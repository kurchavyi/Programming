package Server;

import Server.Commands.Command;
import Server.Organization.Organization;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.TreeMap;

/**
 * manages commands in console application.
 */
public class CommandManager {
    /**
     * the number of commands to remember.
     */
    private final int COMMAND_HISTORY_SIZE = 6;
    private final TreeMap<String, Command> treeCommands = new TreeMap<>();
    /**
     * list of used commands.
     */
    private String[] commandHistory = new String[COMMAND_HISTORY_SIZE];
    /**
     * list of all teams.
     */

    private CollectionManager collectionManager;

    public CommandManager(Command showCommand, Command clearCommand, Command infoCommand,
                          Command exitCommand,Command helpCommand, Command historyCommand,
                          Command minByCreationDateCommand, Command removeKeyCommand, Command removeFullName,
                          Command printAscendingCommand, Command insertCommand, Command updateCommand,
                          Command replaceIfLoweCommand, Command removeLowerCommand,
                          Command executeScriptCommand, CollectionManager collectionManager) {
        treeCommands.put("show", showCommand);
        treeCommands.put("clear", clearCommand);
        treeCommands.put("info", infoCommand);
        treeCommands.put("exit", exitCommand);
        treeCommands.put("help", helpCommand);
        treeCommands.put("history", historyCommand);
        treeCommands.put("min_by_creation_date", minByCreationDateCommand);
        treeCommands.put("remove_key", removeKeyCommand);
        treeCommands.put("remove_any_by_full_name", removeFullName);
        treeCommands.put("print_ascending", printAscendingCommand);
        treeCommands.put("insert", insertCommand);
        treeCommands.put("update", updateCommand);
        treeCommands.put("replace_if_lowe",replaceIfLoweCommand);
        treeCommands.put("remove_lower", removeLowerCommand);
        treeCommands.put("execute_script", executeScriptCommand);
        this.collectionManager = collectionManager;
    }

    /**
     * Executes needed command.
     * @return Command exit status.
     */

    public String show() {
        return treeCommands.get("show").execute(new Object());
    }

    /**
     * Executes needed command.
     * @return Command exit status.
     */
    public String clear() {
        return treeCommands.get("clear").execute("");
    }

    /**
     * Executes needed command.
     * @return Command exit status.
     */
    public String info() {
        return treeCommands.get("info").execute("");
    }

    /**
     * Executes needed command.
     * @return Command exit status.
     */
    public String exit() {
        return treeCommands.get("exit").execute("");
    }

    public boolean save() {
        return this.collectionManager.save();
    }

    /**
     * Prints info about the all com.commands.
     * @return Command exit status.
     */
    public String help() {
        String result = "";
        for (Command command : treeCommands.values()) {
            result += command.getName() + ": "+ command.getDescription() + "\n";
        }
        return result;
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

        for (Command command : treeCommands.values()) {
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
     * @return Command exit status.
     */
    public String history() {
        String result = "";
        if (!IsNotAllNullInHistory()) {
            return "No command has been used yet!";
        }
        else {
            result = "Last Commands Used:\n";
            for (String command : commandHistory) {
                if (command != null) result += " " + command + "\n";
            }
        } return result;
    }

    /**
     * Prints organization from the collection whose creationDate field value is the minimum.
     * @return Command exit status.
     */
    public String min_by_creation_date() {
        return treeCommands.get("min_by_creation_date").execute("");
    }

    /**
     * delete organization from the collection by given key.
     * @param argument Its argument.
     * @return Command exit status.
     */
    public String remove_key(Object argument) {
        return  treeCommands.get("remove_key").execute(argument);
    }

    /**
     * delete organization from the collection by given full name.
     * @param argument Its argument.
     * @return Command exit status.
     */
    public String remove_any_by_full_name(Object argument) {
        return treeCommands.get("remove_any_by_full_name").execute(argument);
    }
    /**
     * print a collection sorted by keys.
     * @return Command exit status.
     */
    public String print_ascending() {
        return treeCommands.get("print_ascending").execute("");
    }

    /**
     * adds an item to the collection by key.
     * @return Command exit status.
     */
    public String insert(Object argument) {
        return treeCommands.get("insert").execute(argument);
    }

    /**
     * updates an item in the collection by id.
     * @return Command exit status.
     */
    public String update(Object argument) {
        return treeCommands.get("update").execute(argument);
    }

    /**
     * replaces the value of the collection item by id.
     * @return Command exit status.
     */
    public String replace_if_lowe(Object argument) {
        return treeCommands.get("replace_if_lowe").execute(argument);
    }

    /**
     * removes from the collection all elements less than the specified one.
     * @return Command exit status.
     */
    public String remove_lower(Object argument) {
        return treeCommands.get("remove_lower").execute(argument);
    }

    public String executeCommand(TransferWrapper transferWrapper) {
        if (transferWrapper == null) {
            return "";
        }
        String command = transferWrapper.getNameCommand();
        Object argument = transferWrapper.getArgument();
        switch (command) {
            case "":
                return "";
            case "show":
                return show();
            case "clear":
                return clear();
            case "info":
                return info();
            case "exit":
                return exit();
            case "help":
                return help();
            case "history":
                return history();
            case "min_by_creation_date":
                return min_by_creation_date();
            case "remove_key":
                return remove_key(argument);
            case "remove_any_by_full_name":
                return remove_any_by_full_name(argument);
            case "print_ascending":
                return print_ascending();
            case "insert":
                return insert(argument);
            case "update":
                return update(argument);
            case "replace_if_lowe":
                return replace_if_lowe(argument);
            case "remove_lower":
                return remove_lower(argument);
            default:
                return "Command '" + command + "' not exist";
        }
    }

    public CollectionManager getCollectionManager() {
        return collectionManager;
    }
}
