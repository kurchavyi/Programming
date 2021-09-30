package Server;

import Server.Commands.Command;
import data.TransferWrapper;
import data.User;

import java.util.Arrays;
import java.util.HashMap;
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
    private HashMap<User, String[]> commandHistory = new HashMap<>();
    /**
     * list of all teams.
     */

    private CollectionManager collectionManager;

    public CommandManager(Command showCommand, Command clearCommand, Command infoCommand,
                          Command exitCommand,Command helpCommand, Command historyCommand,
                          Command minByCreationDateCommand, Command removeKeyCommand, Command removeFullName,
                          Command printAscendingCommand, Command insertCommand, Command updateCommand,
                          Command replaceIfLoweCommand, Command removeLowerCommand,
                          Command executeScriptCommand, Command signInCommand, Command signUpCommand,
                          Command logOutCommand, CollectionManager collectionManager) {
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
        treeCommands.put("sign_in", signInCommand);
        treeCommands.put("sign_up", signUpCommand);
        treeCommands.put("log_out", logOutCommand);
        this.collectionManager = collectionManager;
    }

    /**
     * Executes needed command.
     * @return Command exit status.
     */

    public String show(Object objectArgument, User user, Integer intArgument) {
        return treeCommands.get("show").execute(objectArgument, user, intArgument);
    }

    /**
     * Executes needed command.
     * @return Command exit status.
     */
    public String clear(Object objectArgument, User user, Integer intArgument) {
        return treeCommands.get("clear").execute(objectArgument, user, intArgument);
    }

    /**
     * Executes needed command.
     * @return Command exit status.
     */
    public String info(Object objectArgument, User user, Integer intArgument) {
        return treeCommands.get("info").execute(objectArgument, user, intArgument);
    }

    /**
     * Executes needed command.
     * @return Command exit status.
     */
    public String exit(Object objectArgument, User user, Integer intArgument) {
        return treeCommands.get("exit").execute("", user, intArgument);
    }

    /**
     * Prints info about the all com.commands.
     * @return Command exit status.
     */
    public String help(Object objectArgument, User user, Integer intArgument) {
        String result = "";
        for (Command command : treeCommands.values()) {
            if (!command.getName().equals("sign_in") && (!command.getName().equals("sign_up")))
            result += command.getName() + ": "+ command.getDescription() + "\n";
        }
        result += "log_in : allows you to log in or register";
        return result;
    }

    /**
     * Adds command to command history.
     * @param commandToStore Command to add.
     */
    public void addToHistory(String commandToStore, User user) {
        if (user != null) {
            if (commandHistory.containsKey(user)) {
                String[] user_history = commandHistory.get(user);
                for (Command command : treeCommands.values()) {
                    if (command.getName().split(" ")[0].equals(commandToStore) && !commandToStore.equals("sign_in")
                            && !commandToStore.equals("sign_up") && !commandToStore.equals("log_out")) {
                        for (int i = COMMAND_HISTORY_SIZE - 1; i > 0; i--) {
                            user_history[i] = user_history[i - 1];
                        }
                        user_history[0] = commandToStore;
                    }
                }
            } else {
                String[] user_history = new String[COMMAND_HISTORY_SIZE];
                for (Command command : treeCommands.values()) {
                    if (command.getName().split(" ")[0].equals(commandToStore) && !commandToStore.equals("sign_in")
                            && !commandToStore.equals("sign_up") && !commandToStore.equals("log_out")) {
                        user_history[0] = commandToStore;
                    }
                }
                commandHistory.put(user, user_history);
            }
        }
    }

    /**
     * @return false if all elements are null else true.
     */
    public boolean IsNotAllNullInHistory(User user) {
        int count = 0;
        String[] userHistory = commandHistory.get(user);
        System.out.println(Arrays.toString(userHistory));
        for (String command : userHistory) {
            if (command == null) {
                count += 1;
            }
        }
        return count != COMMAND_HISTORY_SIZE;
    }

    /**
     * Prints the history of used commands.
     * @return Command exit status.
     */
    public String history(Object argument, User user, Integer intArgument) {
        if (user == null) return "need authorization";
        String result = "";
        if (!IsNotAllNullInHistory(user)) {
            return "No command has been used yet!";
        }
        else {
            result = "Last Server.Commands Used:\n";
            for (String command : commandHistory.get(user)) {
                if (command != null) result += " " + command + "\n";
            }
        } return result;
    }

    /**
     * Prints organization from the collection whose creationDate field value is the minimum.
     * @return Command exit status.
     */
    public String min_by_creation_date(Object objectArgument, User user, Integer intArgument) {
        return treeCommands.get("min_by_creation_date").execute(objectArgument, user, intArgument);
    }

    /**
     * delete organization from the collection by given key.
     * @return Command exit status.
     */
    public String remove_key(Object objectArgument, User user, Integer intArgument) {
        return  treeCommands.get("remove_key").execute(objectArgument, user, intArgument);
    }

    /**
     * delete organization from the collection by given full name.
     * @return Command exit status.
     */
    public String remove_any_by_full_name(Object objectArgument, User user, Integer intArgument) {
        return treeCommands.get("remove_any_by_full_name").execute(objectArgument, user, intArgument);
    }
    /**
     * print a collection sorted by keys.
     * @return Command exit status.
     */
    public String print_ascending(Object objectArgument, User user, Integer intArgument) {
        return treeCommands.get("print_ascending").execute(objectArgument, user, intArgument);
    }

    /**
     * adds an item to the collection by key.
     * @return Command exit status.
     */
    public String insert(Object objectArgument, User user, Integer intArgument) {
        return treeCommands.get("insert").execute(objectArgument, user, intArgument);
    }

    /**
     * updates an item in the collection by id.
     * @return Command exit status.
     */
    public String update(Object objectArgument, User user, Integer intArgument) {
        return treeCommands.get("update").execute(objectArgument, user, intArgument);
    }

    /**
     * replaces the value of the collection item by id.
     * @return Command exit status.
     */
    public String replace_if_lowe(Object objectArgument, User user, Integer intArgument) {
        return treeCommands.get("replace_if_lowe").execute(objectArgument, user, intArgument);
    }

    /**
     * removes from the collection all elements less than the specified one.
     * @return Command exit status.
     */
    public String remove_lower(Object objectArgument, User user, Integer intArgument) {
        return treeCommands.get("remove_lower").execute(objectArgument, user, intArgument);
    }

    public String sign_in(Object objectArgument, User user, Integer intArgument) {
        return treeCommands.get("sign_in").execute(objectArgument, user, intArgument);
    }

    public String sign_up(Object objectArgument, User user, Integer intArgument) {
        return treeCommands.get("sign_up").execute(objectArgument, user, intArgument);
    }

    public String log_out(Object objectArgument, User user, Integer intArgument) {
        return treeCommands.get("log_out").execute(objectArgument, user, intArgument);
    }

    public String executeCommand(TransferWrapper transferWrapper) {
        if (transferWrapper == null) {
            return "";
        }
        String command = transferWrapper.getNameCommand();
        Object argument = transferWrapper.getArgument();
        Integer intArgument = transferWrapper.getIntegerArgument();
        User user;
        User hashUser = null;
        if (!(transferWrapper.getUser() == null)) {
            user = transferWrapper.getUser();
            hashUser = new User(user.getLogin(), PasswordHasher.hash(user.getPassword() + "!El@k@"));
            addToHistory(transferWrapper.getNameCommand(), hashUser);
        }
        user = hashUser;
        switch (command) {
            case "":
                return "";
            case "show":
                return show(argument, user, intArgument);
            case "clear":
                return clear(argument, user, intArgument);
            case "info":
                return info(argument, user, intArgument);
            case "exit":
                return exit(argument, user, intArgument);
            case "help":
                return help(argument, user, intArgument);
            case "history":
                return history(argument, user, intArgument);
            case "min_by_creation_date":
                return min_by_creation_date(argument, user, intArgument);
            case "remove_key":
                return remove_key(argument, user, intArgument);
            case "remove_any_by_full_name":
                return remove_any_by_full_name(argument, user, intArgument);
            case "print_ascending":
                return print_ascending(argument, user, intArgument);
            case "insert":
                return insert(argument, user, intArgument);
            case "update":
                return update(argument, user, intArgument);
            case "replace_if_lowe":
                return replace_if_lowe(argument, user, intArgument);
            case "remove_lower":
                return remove_lower(argument, user, intArgument);
            case "sign_in":
                return sign_in(argument, user, intArgument);
            case "sign_up":
                return sign_up(argument, user, intArgument);
            case "log_out":
                return log_out(argument, user, intArgument);
            default:
                return "Command '" + command + "' not exist";
        }
    }

    public CollectionManager getCollectionManager() {
        return collectionManager;
    }
}
