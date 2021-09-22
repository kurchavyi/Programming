package Server;

import data.TransferWrapper;

import java.io.IOException;
import java.nio.channels.*;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Logger;

public class ServerApp {
    private final ServerSocketChannel serverSocketChannel;
    public CommandManager commandManager;
    public String answer;
    private static final Logger logger = Logger.getLogger("");
    private ExecutorService fixedThreadPool = Executors.newFixedThreadPool(1);

    public ServerApp(ServerSocketChannel serverSocketChannel, CommandManager commandManager) {
        this.serverSocketChannel = serverSocketChannel;
        this.commandManager = commandManager;
        input();
    }
    public void run() {
        logger.info("Server running.");
        while (true) {
            try {
                SocketChannel socketChannel = serverSocketChannel.accept();
                ReadingCommandThread readingCommandThread = new ReadingCommandThread(socketChannel);
                readingCommandThread.start();
                try {
                    readingCommandThread.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                };
                TransferWrapper command = readingCommandThread.getCommand();
                if (command == null) continue;
                System.out.println(command);
                if (!fixedThreadPool.submit(() -> {
                    answer = commandManager.executeCommand(command);
                    return true;
                }).get()) break;
                SendingAnswerThread sendingAnswerThread = new SendingAnswerThread(socketChannel, answer);
                sendingAnswerThread.start();
                sendingAnswerThread.join();
            } catch (IOException | InterruptedException | ExecutionException e) {
                e.printStackTrace();
                System.out.println("multithreading error");
            }
        }
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
                        //this.commandManager.save();
                        System.exit(0);
                    }
                }
            } catch (Exception ignored){}
        };
        Thread thread = new Thread(userInput);
        thread.start();
    }
}
