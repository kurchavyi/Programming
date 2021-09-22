package Server.Commands;

import Exceptions.DatabaseException;
import Server.DatabaseCollectionManager;
import Server.UserManager;
import data.User;

/**
 * 'exit' command. Closes the program.
 */

public class ExitCommand extends Command {

    private UserManager userManager;

    public  ExitCommand(UserManager userManager) {
        super("exit", "ends the program (without saving to file). Needs no arguments");
        this.userManager = userManager;
    }

    @Override
    public String execute(Object argument, User user, Integer intArgument) {
        try {
            if (user != null) {
                userManager.updateOnline(user, false);
                return "The account has been logged out!\napplication stopped";
            }
        } catch (DatabaseException e) {
            return "error";
        }
        return "application stopped";
    }
}
