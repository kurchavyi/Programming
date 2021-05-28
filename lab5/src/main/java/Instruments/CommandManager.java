package Instruments;

import Commands.Command;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * manages commands in console application.
 */
public class CommandManager {
    /**
     * the number of commands to remember.
     */
    private final int COMMAND_HISTORY_SIZE = 6;
    /**
     * list of used commands.
     */
    private String[] commandHistory = new String[COMMAND_HISTORY_SIZE];
    /**
     * list of all teams.
     */
    private final List<Command> commands = new ArrayList<>();
    /**
     * save command.
     */
    private final Command saveCommand;
    /**
     * show command.
     */
    private final Command showCommand;
    /**
     * clear command.
     */
    private final Command clearCommand;
    /**
     * info command.
     */
    private  final Command infoCommand;
    /**
     * exit command.
     */
    private final Command exitCommand;
    /**
     * help command.
     */
    private final Command helpCommand;
    /**
     * history command.
     */
    private final Command historyCommand;
    /**
     * min_by_creation_date command.
     */
    private final Command minByCreationDateCommand;
    /**
     * remove_key command.
     */
    private final Command removeKeyCommand;
    /**
     * remove_full_name command.
     */
    private final Command removeFullName;
    /**
     * print_ascending command.
     */
    private final Command printAscendingCommand;
    /**
     * insert command.
     */
    private  final Command insertCommand;
    /**
     * update command.
     */
    private final Command updateCommand;
    /**
     * replace_if_lowe command.
     */
    private final Command replaceIfLoweCommand;
    /**
     * remove_lower command.
     */
    private final Command removeLowerCommand;
    /**
     * execute_script command.
     */
    private final Command executeScriptCommand;

    private final ArrayList<String> executeNameFiles = new ArrayList<String>();

    public CommandManager(Command saveCommand, Command showCommand, Command clearCommand, Command infoCommand,
                          Command exitCommand,Command helpCommand, Command historyCommand,
                          Command minByCreationDateCommand, Command removeKeyCommand, Command removeFullName,
                          Command printAscendingCommand, Command insertCommand, Command updateCommand,
                          Command replaceIfLoweCommand, Command removeLowerCommand, Command executeScriptCommand) {
        this.saveCommand = saveCommand;
        this.showCommand = showCommand;
        this.clearCommand = clearCommand;
        this.infoCommand = infoCommand;
        this.exitCommand = exitCommand;
        this.helpCommand = helpCommand;
        this.historyCommand = historyCommand;
        this.minByCreationDateCommand = minByCreationDateCommand;
        this.removeKeyCommand = removeKeyCommand;
        this.removeFullName = removeFullName;
        this.printAscendingCommand = printAscendingCommand;
        this.insertCommand = insertCommand;
        this.updateCommand = updateCommand;
        this.replaceIfLoweCommand = replaceIfLoweCommand;
        this.removeLowerCommand = removeLowerCommand;
        this.executeScriptCommand = executeScriptCommand;
        commands.add(saveCommand);
        commands.add(showCommand);
        commands.add(clearCommand);
        commands.add(infoCommand);
        commands.add(exitCommand);
        commands.add(helpCommand);
        commands.add(historyCommand);
        commands.add(minByCreationDateCommand);
        commands.add(removeKeyCommand);
        commands.add(removeFullName);
        commands.add(printAscendingCommand);
        commands.add(insertCommand);
        commands.add(updateCommand);
        commands.add(removeKeyCommand);
        commands.add(removeLowerCommand);
        commands.add(executeScriptCommand);
    }

    /**
     * @return List of manager's com.commands.
     */
    public List<Command> getCommands() {
        return commands;
    }
    /**
     * Executes needed command.
     * @param argument Its argument.
     * @return Command exit status.
     */
    public boolean save(String argument) {
        return saveCommand.execute(argument);
    }

    /**
     * Executes needed command.
     * @param argument Its argument.
     * @return Command exit status.
     */

    public boolean show(String argument) {
        return showCommand.execute(argument);
    }

    /**
     * Executes needed command.
     * @param argument Its argument.
     * @return Command exit status.
     */
    public boolean clear(String argument) {
        return clearCommand.execute(argument);
    }

    /**
     * Executes needed command.
     * @param argument Its argument.
     * @return Command exit status.
     */
    public boolean info(String argument) {
        return infoCommand.execute(argument);
    }

    /**
     * Prints that command is not found.
     * @param command Command, which is not found.
     * @return Command exit status.
     */
    public boolean noSuchCommand(String command) {
        System.out.println("Command'" + command + "' not found. " +
                "Enter the keyword 'help to get a list of all available commands.");
        return false;
    }

    /**
     * Executes needed command.
     * @param argument Its argument.
     * @return Command exit status.
     */
    public boolean exit(String argument) {
        return exitCommand.execute(argument);
    }

    /**
     * Prints info about the all com.commands.
     * @param argument Its argument.
     * @return Command exit status.
     */
    public boolean help(String argument) {
        if (helpCommand.execute(argument)) {
            for (Command command : commands) {
                System.out.println(command.getName() + ": "+ command.getDescription());
            }
            return true;
        } else return false;
    }

    /**
     * @return The command history.
     */
    public String[] getCommandHistory() {
        return commandHistory;
    }

    /**
     * Adds command to command history.
     * @param commandToStore Command to add.
     */
    public void addToHistory(String commandToStore) {

        for (Command command : commands) {
            if (command.getName().split(" ")[0].equals(commandToStore)) {
                for (int i = COMMAND_HISTORY_SIZE-1; i>0; i--) {
                    commandHistory[i] = commandHistory[i-1];
                }
                commandHistory[0] = commandToStore;
            }
        }
    }

    /**
     * @return false if all elements are null else true.
     */
    public boolean IsNotAllNullInHistory() {
        int count = 0;
        for (String command : commandHistory) {
            if (command == null) {
                count += 1;
            }
        }
        return count != commandHistory.length;
    }
    /**
     * Prints the history of used commands.
     * @param argument Its argument.
     * @return Command exit status.
     */
    public boolean history(String argument) {
        if (historyCommand.execute(argument)) {
            if (!IsNotAllNullInHistory()) {
                System.out.println("Ни одной команды еще не было использовано!");
            }
            else {
                System.out.println("Последние использованные команды:");
                for (String command : commandHistory) {
                    if (command != null) System.out.println(" " + command);
                }
            }
            return true;
        }
        return false;
    }

    /**
     * Prints organization from the collection whose creationDate field value is the minimum.
     * @param argument Its argument.
     * @return Command exit status.
     */
    public boolean min_by_creation_date(String argument) {
        return minByCreationDateCommand.execute(argument);
    }

    /**
     * delete organization from the collection by given key.
     * @param argument Its argument.
     * @return Command exit status.
     */
    public boolean remove_key(String argument) {
        return  removeKeyCommand.execute(argument);
    }

    /**
     * delete organization from the collection by given full name.
     * @param argument Its argument.
     * @return Command exit status.
     */
    public boolean remove_any_by_full_name(String argument) {
        return removeFullName.execute(argument);
    }

    /**
     * print a collection sorted by keys.
     * @return Command exit status.
     */
    public boolean print_ascending(String argument) {
        return printAscendingCommand.execute(argument);
    }

    /**
     * adds an item to the collection by key.
     * @return Command exit status.
     */
    public boolean insert(String argument) {
        return insertCommand.execute(argument);
    }

    /**
     * updates an item in the collection by id.
     * @return Command exit status.
     */
    public boolean update(String argument) {
        return updateCommand.execute(argument);
    }

    /**
     * replaces the value of the collection item by id.
     * @return Command exit status.
     */
    public boolean replace_if_lowe(String argument) {
        return replaceIfLoweCommand.execute(argument);
    }

    /**
     * removes from the collection all elements less than the specified one.
     * @return Command exit status.
     */
    public boolean remove_lower(String argument) {
        return removeLowerCommand.execute(argument);
    }

    /**
     * executes scripts from a file.
     * @return Command exit status.
     */
    public boolean execute_script(String arg) {
        Path path = null;
        try {
            path = Paths.get(arg);
            executeNameFiles.add(arg.toString());
        } catch (InvalidPathException e) {
            System.out.println("Path invalid");
            return false;
        }
        String inputFileName = path.toString();
        if (executeScriptCommand.execute(arg)) {
                try (BufferedReader reader = new BufferedReader(new FileReader(inputFileName))) {
                    String line;
                    while ((line = reader.readLine()) != null){
                    if (line == null) {
                        System.out.println("file is empty");
                        return true;
                    }
                    String[] userCommand = {"", ""};
                    userCommand = (line.trim() + " ").split(" ", 2);
                    userCommand[1] = userCommand[1].trim();
                    String command = userCommand[0];
                    String argument = userCommand[1];
                    switch (command) {
                        case "":
                            break;
                        case "save":
                            save(argument);
                            break;
                        case "show":
                            show(argument);
                            break;
                        case "clear":
                            clear(argument);
                            break;
                        case "info":
                            info(argument);
                            break;
                        case "exit":
                            exit(argument);
                            break;
                        case "help":
                            help(argument);
                            break;
                        case "history":
                            history(argument);
                            break;
                        case "min_by_creation_date":
                            min_by_creation_date(argument);
                            break;
                        case "remove_key":
                            remove_key(argument);
                            break;
                        case "remove_any_by_full_name":
                            remove_any_by_full_name(argument);
                            break;
                        case "print_ascending":
                            print_ascending(argument);
                            break;
                        case "insert":
                            insert(argument);
                            break;
                        case "update":
                            update(argument);
                            break;
                        case "replace_if_lowe":
                            replace_if_lowe(argument);
                            break;
                        case "remove_lower":
                            remove_lower(argument);
                            break;
                        case "execute_script":
                            if (executeNameFiles.contains(argument)) {
                                System.out.println("cannot call a file that has already been called");
                            } else execute_script(argument);
                            break;
                        default:
                            noSuchCommand(command);
                    }
                    } executeNameFiles.clear();
                } catch (IOException e) {
                    System.out.println("There are some problems with the file");
                }
            }
        return true;
    }
}
