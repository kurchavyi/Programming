package Server;

import Server.Instruments.Marshal;
import Server.Instruments.MarshalJson;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.logging.Logger;

import Server.CommandManager;
import static java.util.logging.Level.*;

import javax.xml.crypto.KeySelector;

public class ServerApp {
    private Selector selector;
    private ServerSocketChannel serverSocketChannel;
    public CommandManager commandManager;
    public String answer;
    private static final Logger logger = Logger.getLogger("");
    public Iterator<SelectionKey> keysIterator;

    public ServerApp(Selector selector, ServerSocketChannel serverSocketChannel, CommandManager commandManager) {
        this.selector = selector;
        this.serverSocketChannel = serverSocketChannel;
        this.commandManager = commandManager;
        input();
    }

    public void run() throws IOException {
        logger.info("Server running.");
        while (true) {
                try {
                    selector.select();
                } catch (IOException e) {
                    System.out.println("error connections");
                }
                Set<SelectionKey> keys = selector.selectedKeys();
                this.keysIterator = keys.iterator();
                while (keysIterator.hasNext()) {
                    SelectionKey myKey = keysIterator.next();
                    if (myKey.isAcceptable()) {
                        try {
                            SocketChannel socketChannelClient = serverSocketChannel.accept();
                            socketChannelClient.configureBlocking(false);
                            socketChannelClient.register(selector, SelectionKey.OP_READ);
                        } catch (IOException e) {
                            System.out.println("error connections");
                        }
                    } else if (myKey.isReadable()) {
                        SocketChannel crunchifyClient = (SocketChannel) myKey.channel();
                        ByteBuffer crunchifyBuffer = ByteBuffer.allocate(2048);
                        try {
                            crunchifyClient.read(crunchifyBuffer);
                        } catch (IOException e) {
                            myKey.interestOps(0);
                            crunchifyClient.close();
                            break;
                        }
                        String result = new String(crunchifyBuffer.array()).trim();
                        Marshal marshal = new Marshal();
                        if (marshal.JsonTransferWrapper(result) == null) {
                            myKey.interestOps(0);
                            crunchifyClient.close();
                            break;
                        }
                        this.answer = executeCommand(marshal.JsonTransferWrapper(result));
                        this.commandManager.addToHistory(marshal.JsonTransferWrapper(result).getNameCommand());
                        this.commandManager.save();
                        try {
                            crunchifyClient.register(selector, SelectionKey.OP_WRITE);
                        } catch (IOException e) {
                            System.out.println("error connections");
                        }
                    } else if (myKey.isWritable()) {
                        SocketChannel socketChannel = (SocketChannel) myKey.channel();
                        ByteBuffer byteBuffer = ByteBuffer.wrap(answer.getBytes());
                        try {
                            socketChannel.write(byteBuffer);
                        } catch (IOException e) {
                            System.out.println("error connections");
                        }
                        byteBuffer.clear();
                        try {
                            socketChannel.register(selector, SelectionKey.OP_READ);
                        } catch (IOException e) {
                            System.out.println("error connections");
                        }
                    }
                    keysIterator.remove();
                }
        }
    }

    public String executeCommand(TransferWrapper transferWrapper){
        return commandManager.executeCommand(transferWrapper);
    }

    private void input() {
        Scanner scanner = new Scanner(System.in);
        Runnable userInput = () -> {
            try {
                while (true) {
                    String[] userCommand = {"", ""};
                    try {
                        userCommand = (scanner.nextLine().trim() + " ").split(" ", 2);
                    } catch (NoSuchElementException e) {
                        logger.severe("The input stream is over. The program is stopped");
                        System.exit(0);
                    }
                    userCommand[1] = userCommand[1].trim();
                    if (userCommand[0].equals("exit")) {
                        logger.info("server is stopped");
                        this.commandManager.save();
                        System.exit(0);
                    }
                    else if (userCommand[0].equals("save")) {
                        if (!this.commandManager.save()) {
                            System.out.println("use command 'new_file', after the problem a new file should be listed");
                        }
                    }
                    else if (userCommand[0].equals("new_file")) {
                        Path newFile = Paths.get(userCommand[1].trim().split(" ", 2)[0]);
                        this.commandManager.getCollectionManager().getFileManager().setPath(newFile);
                    }
                    else System.out.println("Command not found");
                }
            } catch (Exception ignored){}
        };
        Thread thread = new Thread(userInput);
        thread.start();
    }

}
