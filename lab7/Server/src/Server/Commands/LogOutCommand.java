package Server.Commands;

import Exceptions.DatabaseException;
import Server.UserManager;
import data.User;

public class LogOutCommand extends Command{

    private UserManager databaseUserManager;

    public LogOutCommand(UserManager databaseUserManager) {
        super("log_out", "log out");
        this.databaseUserManager = databaseUserManager;
    }

    @Override
    public String execute(Object objectArgument, User user, Integer intArgument) {
        if (user == null) {
            return "you are not signed in yet";
        }
        //try {
            //databaseUserManager.updateOnline(user, false);
        return "The account has been logged out!";
//        } catch (DatabaseException e) {
//            return "An error occurred while accessing the database!";
//        }
    }
}
