package Instruments;

public class ConsoleApp {

    /**
     * command manager - manages commands
     */
    private final CommandManager commandManager;
    /**
     * user scanner which reads data from the command line
     */
    private final UserScanner userScanner;

    public ConsoleApp(CommandManager commandManager, UserScanner userScanner) {
        this.commandManager = commandManager;
        this.userScanner  = userScanner;

    }

    /**
     * Mode for catching commands from user input.
     */
    public void interactiveMode() {
        System.out.println("The application is running, a list with all commands can be obtained using the 'help' keyword");
        int commandStatus;
            do {
                String[] userCommand = userScanner.enterCommand();
                commandStatus = launchCommand(userCommand);
                commandManager.addToHistory(userCommand[0]);
            } while (commandStatus != 2);
    }

    /**
     * Launch the command.
     * @param userCommand Command to launch.
     * @return exit code.
     */
    private int launchCommand(String[] userCommand) {
        String command = userCommand[0];
        String argument = userCommand[1];
        switch (command) {
            case "":
                break;
            case "save":
                if (commandManager.save(argument)) return 1;
                break;
            case "show":
                if (commandManager.show(argument)) return 1;
                break;
            case "clear":
                if (commandManager.clear(argument)) return 1;
                break;
            case "info":
                if (commandManager.info(argument)) return 1;
                break;
            case "exit":
                if (commandManager.exit(argument)) return 2;
                break;
            case "help":
                if (commandManager.help(argument)) return 1;
                break;
            case "history":
                if (commandManager.history(argument)) return 1;
                break;
            case "min_by_creation_date":
                if (commandManager.min_by_creation_date(argument)) return 1;
                break;
            case "remove_key":
                if (commandManager.remove_key(argument)) return 1;
                break;
            case "remove_any_by_full_name":
                if (commandManager.remove_any_by_full_name(argument)) return 1;
                break;
            case "print_ascending":
                if (commandManager.print_ascending(argument)) return 1;
                break;
            case "insert":
                if (commandManager.insert(argument)) return 1;
                break;
            case "update":
                if (commandManager.update(argument)) return 1;
                break;
            case "replace_if_lowe":
                if (commandManager.replace_if_lowe(argument)) return 1;
                break;
            case "remove_lower":
                if (commandManager.remove_lower(argument)) return 1;
                break;
            case "execute_script":
                if (commandManager.execute_script(argument)) return 1;
                break;
            default:
                if (!commandManager.noSuchCommand(command)) return 1;
        }
        return 0;
    }
}
