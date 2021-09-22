package Server.Commands;

import data.User;

/**
 * Command 'history'. Checks for wrong arguments then do nothing.
 */
public class HistoryCommand extends Command {

    public HistoryCommand() {
        super("history", "displays the history of the commands used (the last 6 commands)." +
                " Does not need any arguments");
    }

    /**
     * Executes the command.
     * @return Command exit status.
     */
    @Override
    public String execute(Object argument, User user, Integer intArgument) {
        return "";
    }
}
