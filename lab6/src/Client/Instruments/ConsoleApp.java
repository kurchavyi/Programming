package Client.Instruments;

import Client.Organization.Organization;

import java.io.*;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class ConsoleApp {

    private InputStream in;
    private OutputStream out;
    private TransferWrapper transferWrapper;
    private Organization organization;
    private MarshalJson marshalJson;
    private byte[] bytes;
    private ArrayList<String> executeNameFiles = new ArrayList<String>();

    /**
     * user scanner which reads data from the command line
     */
    private final UserScanner userScanner;

    public ConsoleApp(UserScanner userScanner) {
        this.userScanner = userScanner;
        marshalJson = new MarshalJson();
    }

    /**
     * Mode for catching commands from user input.
     */
    public void interactiveMode() {
        System.out.println("The application is running, a list with all commands can be obtained using the 'help' keyword");
        int commandStatus = 1;
        boolean printWaitConnected = true;
        while (commandStatus != 2) {
            InetSocketAddress crunchifyAddr = new InetSocketAddress("localhost", 1034);
            SocketChannel socketChannelClient = null;
            try {
                socketChannelClient = SocketChannel.open(crunchifyAddr);
                System.out.println("server connected");
            } catch (IOException e) {
                if (printWaitConnected) {
                    System.out.println("the server is down, wait for it to work");
                }
                printWaitConnected = false;
                continue;
            }
            printWaitConnected = true;
            try {
                do {
                    String[] userCommand = userScanner.enterCommand();
                    commandStatus = launchCommand(userCommand, socketChannelClient);
                } while (commandStatus != 2);
            } catch (IOException ignored) {
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
        String command = userCommand[0];
        String argument = userCommand[1];
        switch (command) {
            case "":
                break;
            case "show":
                transferWrapper = new TransferWrapper("show");
                bytes = marshalJson.transferWrapper(transferWrapper).getBytes();
                transferToServer(bytes, socketChannelClient);
                System.out.println(transferOutServer(socketChannelClient));
                return 1;
            case "clear":
                transferWrapper = new TransferWrapper("clear");
                bytes = marshalJson.transferWrapper(transferWrapper).getBytes();
                transferToServer(bytes, socketChannelClient);
                System.out.println(transferOutServer(socketChannelClient));
                return 1;
            case "info":
                transferWrapper = new TransferWrapper("info");
                bytes = marshalJson.transferWrapper(transferWrapper).getBytes();
                transferToServer(bytes, socketChannelClient);
                System.out.println(transferOutServer(socketChannelClient));
                return 1;
            case "exit":
                transferWrapper = new TransferWrapper("exit");
                bytes = marshalJson.transferWrapper(transferWrapper).getBytes();
                transferToServer(bytes, socketChannelClient);
                return 2;
            case "help":
                transferWrapper = new TransferWrapper("help");
                bytes = marshalJson.transferWrapper(transferWrapper).getBytes();
                transferToServer(bytes, socketChannelClient);
                System.out.println(transferOutServer(socketChannelClient));
                return 1;
            case "history":
                transferWrapper = new TransferWrapper("history");
                bytes = marshalJson.transferWrapper(transferWrapper).getBytes();
                transferToServer(bytes, socketChannelClient);
                System.out.println(transferOutServer(socketChannelClient));
                break;
            case "min_by_creation_date":
                transferWrapper = new TransferWrapper("min_by_creation_date");
                bytes = marshalJson.transferWrapper(transferWrapper).getBytes();
                transferToServer(bytes, socketChannelClient);
                System.out.println(transferOutServer(socketChannelClient));
                return 1;
            case "remove_key":
                argument = argument.trim().split(" ")[0];
                try {
                    Integer key = Integer.parseInt(argument);
                    transferWrapper = new TransferWrapper("remove_key", key);
                    bytes = marshalJson.transferWrapper(transferWrapper).getBytes();
                } catch (NumberFormatException e) {
                    System.out.println("Key value can only be a positive integer");
                    break;
                }
                transferToServer(bytes, socketChannelClient);
                System.out.println(transferOutServer(socketChannelClient));
                return 1;
            case "remove_any_by_full_name":
                transferWrapper = new TransferWrapper("remove_any_by_full_name", argument);
                bytes = marshalJson.transferWrapper(transferWrapper).getBytes();
                transferToServer(bytes, socketChannelClient);
                System.out.println(transferOutServer(socketChannelClient));
                return 1;
            case "print_ascending":
                transferWrapper = new TransferWrapper("print_ascending");
                bytes = marshalJson.transferWrapper(transferWrapper).getBytes();
                transferToServer(bytes, socketChannelClient);
                System.out.println(transferOutServer(socketChannelClient));
                return 1;
            case "insert":
                argument = argument.split(" ", 2)[0].trim();
                Integer arg = null;
                try {
                    arg = Integer.parseInt(argument);
                } catch (NumberFormatException e) {
                    System.out.println("the key can only be a positive integer");
                    break;
                }
                organization = userScanner.enterOrganization();
                organization.keySetId(arg);
                transferWrapper = new TransferWrapper("insert", organization);
                bytes = marshalJson.transferWrapper(transferWrapper).getBytes();
                transferToServer(bytes, socketChannelClient);
                System.out.println(transferOutServer(socketChannelClient));
                return 1;
            case "update":
                argument = argument.split(" ", 2)[0].trim();
                Integer arg1 = null;
                try {
                    arg1 = Integer.parseInt(argument);
                } catch (NumberFormatException e) {
                    System.out.println("the id can only be a positive integer");
                    break;
                }
                organization = userScanner.enterOrganization();
                organization.keySetId(arg1);
                transferWrapper = new TransferWrapper("update", organization);
                bytes = marshalJson.transferWrapper(transferWrapper).getBytes();
                transferToServer(bytes, socketChannelClient);
                System.out.println(transferOutServer(socketChannelClient));
                return 1;
            case "replace_if_lowe":
                argument = argument.split(" ", 2)[0].trim();
                Integer arg2 = null;
                try {
                    arg2 = Integer.parseInt(argument);
                } catch (NumberFormatException e) {
                    System.out.println("the id can only be a positive integer");
                    break;
                }
                organization = userScanner.enterOrganization();
                organization.keySetId(arg2);
                transferWrapper = new TransferWrapper("replace_if_lowe", organization);
                bytes = marshalJson.transferWrapper(transferWrapper).getBytes();
                transferToServer(bytes, socketChannelClient);
                System.out.println(transferOutServer(socketChannelClient));
                return 1;
            case "remove_lower":
                organization = userScanner.enterOrganization();
                transferWrapper = new TransferWrapper("remove_lower", organization);
                bytes = marshalJson.transferWrapper(transferWrapper).getBytes();
                transferToServer(bytes, socketChannelClient);
                System.out.println(transferOutServer(socketChannelClient));
                return 1;
            case "execute_script":
                transferWrapper = new TransferWrapper("execute_script");
                bytes = marshalJson.transferWrapper(transferWrapper).getBytes();
                String argPath = argument.trim().split(" ", 2)[0].trim();
                Path path = null;
                try {
                    executeNameFiles.add(argPath);
                } catch (InvalidPathException e) {
                    System.out.println("Path invalid");
                    return 1;
                }
                if (isCanRead(new File(argPath))) {
                    try (BufferedReader reader = new BufferedReader(new FileReader(argPath))) {
                        String line;
                        while ((line = reader.readLine()) != null) {
                            if (line == null) {
                                System.out.println("file is empty");
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
                        executeNameFiles.clear();
                        return 1;
                    } catch (IOException e) {
                        System.out.println("The file at the specified path could not be read.");
                        return 1;
                    }
                } else return 1;
            default:
                noSuchCommand(command);
                return 1;
    } return 1;
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
        ByteBuffer crunchifyBuffer = ByteBuffer.allocate(2048);
        socketChannelClient.read(crunchifyBuffer);
        String result = new String(crunchifyBuffer.array()).trim();
        crunchifyBuffer.clear();
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
}
