package Server.Commands;

import data.User;

/**
 * 'execute_script' command. Executes scripts from a file.
 */

public class ExecuteScriptCommand extends Command {

    public ExecuteScriptCommand() {
        super("execute_script", "executes the script from the specified file, " +
                "the required argument is the path to the file," +
                " must be supplied separated by a space after the command," +
                " the command and argument in the file must be specified the same as on the command line");
    }

    /**
     * Executes the script.
     * @return Command execute status.
     */
    @Override
    public String execute(Object argument, User user, Integer intArgument) {
        return "";
    }
}
