package Server;

import Server.Commands.*;

import java.io.*;
import java.net.InetSocketAddress;
import java.nio.channels.ServerSocketChannel;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Server {

    public static void main(String[] args) {
        DatabaseManager databaseManager = new DatabaseManager();
        UserManager userManager = new UserManager(databaseManager);
        DatabaseCollectionManager databaseCollectionManager = new DatabaseCollectionManager(databaseManager, userManager);
        try {
            ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
            InetSocketAddress inetSocketAddress = new InetSocketAddress("localhost", 1034);
            serverSocketChannel.bind(inetSocketAddress);
            Path path = Paths.get("data.txt");
            CollectionManager collectionManager = new CollectionManager(databaseCollectionManager);
            CommandManager commandManager = new CommandManager(new ShowCommand(collectionManager),
                    new ClearCommand(collectionManager, databaseCollectionManager),
                new InfoCommand(collectionManager), new ExitCommand(userManager), new HelpCommand(),
                new HistoryCommand(), new MinByCreationDateCommand(collectionManager),
                new RemoveKeyCommand(collectionManager, databaseCollectionManager),
                    new RemoveFullName(collectionManager, databaseCollectionManager),
                new PrintAscendingCommand(collectionManager),
                    new InsertCommand(collectionManager, databaseCollectionManager),
                new UpdateCommand(collectionManager, databaseCollectionManager),
                new ReplaceIfLoweCommand(collectionManager, databaseCollectionManager),
                new RemoveLowerCommand(collectionManager, databaseCollectionManager), new ExecuteScriptCommand(), new SignInCommand(userManager),
                    new SignUpCommand(userManager), new LogOutCommand(userManager), collectionManager);
            ServerApp serverApp = new ServerApp(serverSocketChannel, commandManager);
            serverApp.run();
        } catch(IOException e){
            System.out.println("problems with connection");
        }
    }
}