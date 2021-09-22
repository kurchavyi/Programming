package Instruments;

import data.Organization.FlyOrganization;
import data.TransferWrapper;
import data.User;

import java.io.*;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.file.InvalidPathException;
import java.util.ArrayList;
import java.util.HashSet;

public class ConsoleApp {
    private ArrayList<String> executeNameFiles = new ArrayList<String>();

    /**
     * user scanner which reads data from the command line
     */
    private final UserScanner userScanner;
    private HashSet<String> commandsWithoutArgument = new HashSet<String>();
    private User user;
    private AuthManager authManager;

    public ConsoleApp(UserScanner userScanner, AuthManager authManager) {
        this.userScanner = userScanner;
        this.authManager = authManager;
        commandsWithoutArgument .add("show");
        commandsWithoutArgument.add("clear");
        commandsWithoutArgument.add("info");
        commandsWithoutArgument.add("exit");
        commandsWithoutArgument.add("help");
        commandsWithoutArgument.add("history");
        commandsWithoutArgument.add("min_by_creation_date");
        commandsWithoutArgument.add("print_ascending");
        commandsWithoutArgument.add("log_out");
    }

    /**
     * Mode for catching commands from user input.
     */
    public void interactiveMode() {
        System.out.println("The application is running, a list with all commands can be obtained using the 'help' keyword");
        int commandStatus = 1;
        while (commandStatus != 2) {
            try {
                SocketChannel socketChannelClient = SocketChannel.open();
                do {
                    String[] userCommand = userScanner.enterCommand();
                    commandStatus = launchCommand(userCommand, socketChannelClient);
                } while (commandStatus != 2);
            } catch (IOException e) {
            System.out.println("the server is down, wait for it to work");
            }
        }
    }

    /**
     * Launch the command.
     *
     * @param userCommand Command to launch.
     * @return exit code.
     */
    private int launchCommand(String[] userCommand, SocketChannel socketChannelClient) throws IOException {
        InetSocketAddress address = new InetSocketAddress("localhost", 1034);
        socketChannelClient = SocketChannel.open();
        socketChannelClient.connect(address);
        String command = userCommand[0];
        String argument = userCommand[1];
        FlyOrganization organization = null;
        if  (commandsWithoutArgument.contains(command)) {
            get_answer_for_command(new TransferWrapper(command, "", user, 0), socketChannelClient);
            if (command.equals("exit")) {return 2;}
            return 1;
        }
        switch (command) {
            case "":
                break;
            case "remove_key":
                try {
                    Integer key = getInt(argument);
                    if (key <= 0) {
                        System.out.println("the key can only be a positive integer");
                        socketChannelClient.close();
                        break;
                    }
                    get_answer_for_command(new TransferWrapper(command, key, user, key), socketChannelClient);
                } catch (NumberFormatException e) {
                    System.out.println("Key value can only be a positive integer");
                    socketChannelClient.close();
                    break;
                }
                socketChannelClient.close();
                return 1;
            case "remove_any_by_full_name":
                get_answer_for_command(new TransferWrapper(command, argument, user, 0), socketChannelClient);
                socketChannelClient.close();
                return 1;
            case "insert":
                Integer key = null;
                Integer id_update = null;
                try {
                    key = getInt(argument);
                } catch (NumberFormatException e) {
                    System.out.println("the key can only be a positive integer");
                    socketChannelClient.close();
                    break;
                }
                if (key <= 0) {
                    System.out.println("the key can only be a positive integer");
                    socketChannelClient.close();
                    break;
                }
                organization = userScanner.enterOrganization();
                get_answer_for_command(new TransferWrapper(command, organization, user, key), socketChannelClient);
                socketChannelClient.close();
                return 1;
            case "update":
                try {
                    id_update = getInt(argument);
                } catch (NumberFormatException e) {
                    System.out.println("the id can only be a positive integer");
                    socketChannelClient.close();
                    break;
                }
                if (id_update <= 0) {
                    System.out.println("the id can only be a positive integer");
                    socketChannelClient.close();
                    break;
                }
                organization = userScanner.enterOrganization();
                get_answer_for_command(new TransferWrapper(command, organization, user, id_update), socketChannelClient);
                socketChannelClient.close();
                return 1;
            case "replace_if_lowe":
                try {
                    key = getInt(argument);
                } catch (NumberFormatException e) {
                    System.out.println("the id can only be a positive integer");
                    socketChannelClient.close();
                    break;
                }
                if (key <= 0) {
                    System.out.println("the key can only be a positive integer");
                    socketChannelClient.close();
                    break;
                }
                organization = userScanner.enterOrganization();
                get_answer_for_command(new TransferWrapper(command, organization, user, key), socketChannelClient);
                socketChannelClient.close();
                return 1;
            case "remove_lower":
                organization = userScanner.enterOrganization();
                get_answer_for_command(new TransferWrapper(command, organization, user, 0), socketChannelClient);
                socketChannelClient.close();
                return 1;
            case "execute_script":
                socketChannelClient.close();
                String argPath = argument.trim().split(" ", 2)[0].trim();
                try {
                    executeNameFiles.add(argPath);
                } catch (InvalidPathException e) {
                    System.out.println("Path invalid");
                    socketChannelClient.close();
                    return 1;
                }
                if (isCanRead(new File(argPath))) {
                    try (BufferedReader reader = new BufferedReader(new FileReader(argPath))) {
                        String line;
                        while ((line = reader.readLine()) != null) {
                            if (line == null) {
                                System.out.println("file is empty");
                                socketChannelClient.close();
                                return 1;
                            }
                            String[] userCommandFile = {"", ""};
                            userCommandFile = (line.trim() + " ").split(" ", 2);
                            userCommandFile[1] = userCommandFile[1].trim();
                            String commandFile = userCommandFile[0];
                            String argumentFile = userCommandFile[1];
                            if (commandFile.equals("execute_script")) {
                                if (executeNameFiles.contains(argumentFile)) {
                                    System.out.println("cannot call a file that has already been called");
                                    continue;
                                }
                            }
                            executeNameFiles.add(argumentFile);
                            launchCommand(userCommandFile, socketChannelClient);
                        }
                        System.out.println("end script");
                        executeNameFiles.clear();
                        socketChannelClient.close();
                        return 1;
                    } catch (IOException e) {
                        System.out.println("The file at the specified path could not be read.");
                        socketChannelClient.close();
                        return 1;
                    }
                } else {
                    socketChannelClient.close();
                    return 1;
                }
            case "log_in":
                if (user != null) {
                    System.out.println("you must first log out");
                    socketChannelClient.close();
                    return 1;
                }
                TransferWrapper transferWrapper = authManager.handle();
                get_answer_for_command(transferWrapper, socketChannelClient);
                socketChannelClient.close();
                return 1;
            default:
                noSuchCommand(command);
                socketChannelClient.close();
                return 1;
    }   socketChannelClient.close();
        return 1;
}
    public void noSuchCommand(String command) {
        System.out.println("Command'" + command + "' not found. " +
                "Enter the keyword 'help to get a list of all available commands.");
    }

    public void transferToServer(byte[] bytes, SocketChannel socketChannelClient) throws IOException {
        ByteBuffer buffer = ByteBuffer.wrap(bytes);
        socketChannelClient.write(buffer);
        buffer.clear();
    }

    public String transferOutServer(SocketChannel socketChannelClient) throws IOException {
        ByteBuffer byteBuffer = ByteBuffer.allocate(8192);
        socketChannelClient.read(byteBuffer);
        String result = new String(byteBuffer.array()).trim();
        byteBuffer.flip();
        return result;
    }

    public boolean isCanRead(File file) {
        if (!file.exists()) {
            System.out.println("The file at the specified path does not exist.");
            return false;
        }
        if (!file.canRead()) {
            System.out.println("The file at the specified path could not be read.");
            return false;
        }
        return true;
    }

    private byte[] serialize(TransferWrapper transferWrapper) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
        objectOutputStream.writeObject(transferWrapper);
        byte[] bytes = byteArrayOutputStream.toByteArray();
        objectOutputStream.flush();
        byteArrayOutputStream.flush();
        byteArrayOutputStream.close();
        objectOutputStream.close();
        return bytes;
    }
    private TransferWrapper deserialize(ByteBuffer byteBuffer) throws IOException, ClassNotFoundException {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(byteBuffer.array());
        ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
        TransferWrapper transferWrapper = (TransferWrapper) objectInputStream.readObject();
        byteArrayInputStream.close();
        objectInputStream.close();
        byteBuffer.clear();
        return transferWrapper;
    }

    private void get_answer_for_command(TransferWrapper transferWrapper, SocketChannel socketChannel) throws IOException{
        byte[] bytes = serialize(transferWrapper);
        transferToServer(bytes, socketChannel);
        String answer = transferOutServer(socketChannel);
        if (transferWrapper.getNameCommand().equals("sign_up") && answer.equals("registration completed successfully")){
            user = transferWrapper.getUser();
        }
        if (transferWrapper.getNameCommand().equals("sign_in") && answer.equals("Authorization was successful")){
            user = transferWrapper.getUser();
        }
        if (transferWrapper.getNameCommand().equals("log_out") && answer.equals("The account has been logged out!")){
            user = null;
        }
        System.out.println(answer);
    }
    public int getInt(String argument) throws NumberFormatException{
        argument = argument.trim().split(" ")[0];
        return Integer.parseInt(argument);

    }
}
