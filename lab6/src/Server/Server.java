package Server;

import Server.Commands.*;
import Server.Instruments.Marshal;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.Set;

public class Server {

    public static void main(String[] args) {
        try {
            Selector selector = Selector.open();
            ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
            InetSocketAddress inetSocketAddress = new InetSocketAddress("localhost", 1034);
            serverSocketChannel.bind(inetSocketAddress);
            serverSocketChannel.configureBlocking(false); // блокинг - не блокинг
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
//           Path path = Paths.get("C:\\Users\\vital\\itmo\\programming\\2sem\\data.txt");
            String pathToFile = System.getenv("PATH_TO_FILE");
            if (pathToFile == null) {
                System.out.println("path not exist");
                System.exit(1);
            }
            Path path = Paths.get(pathToFile);
            FileManager fileManager = new FileManager(path);
            CollectionManager collectionManager = new CollectionManager(fileManager);
            CommandManager commandsManager = new CommandManager(new ShowCommand(collectionManager), new ClearCommand(collectionManager),
                new InfoCommand(collectionManager), new ExitCommand(), new HelpCommand(),
                new HistoryCommand(), new MinByCreationDateCommand(collectionManager),
                new RemoveKeyCommand(collectionManager), new RemoveFullName(collectionManager),
                new PrintAscendingCommand(collectionManager), new InsertCommand(collectionManager),
                new UpdateCommand(collectionManager),
                new ReplaceIfLoweCommand(collectionManager),
                new RemoveLowerCommand(collectionManager), new ExecuteScriptCommand(), collectionManager);
            ServerApp serverApp = new ServerApp(selector, serverSocketChannel, commandsManager);
            serverApp.run();
        } catch(IOException e){
            System.out.println("problems with connection");
        }
    }
}