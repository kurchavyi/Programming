package Main;

import Commands.*;
import Instruments.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        UserScanner userScanner = new UserScanner(new Scanner(System.in));
        //Path pathToFile = Paths.get("C:\\Users\\vital\\itmo\\programming\\2sem\\data.txt");
        String pathToFile = System.getenv("PATH_TO_FILE");
        if (pathToFile == null) {
            System.out.println("path not exist");
            System.exit(1);
        }
        Path path = Paths.get(pathToFile);
        FileManager fileManager = new FileManager(path);
        CollectionManager collectionManager = new CollectionManager(fileManager);
        CommandManager commandsManager = new CommandManager(new SaveCommand(collectionManager),
                new ShowCommand(collectionManager), new ClearCommand(collectionManager),
                new InfoCommand(collectionManager), new ExitCommand(), new HelpCommand(),
                new HistoryCommand(), new MinByCreationDateCommand(collectionManager),
                new RemoveKeyCommand(collectionManager), new RemoveFullName(collectionManager),
                new PrintAscendingCommand(collectionManager), new InsertCommand(collectionManager, userScanner),
                new UpdateCommand(collectionManager, userScanner),
                new ReplaceIfLoweCommand(collectionManager, userScanner),
                new RemoveLowerCommand(collectionManager, userScanner), new ExecuteScriptCommand());
        ConsoleApp console = new ConsoleApp(commandsManager, userScanner);
        console.interactiveMode();
    }
}