package Server.Commands;

import Exceptions.DatabaseException;
import Exceptions.MultiUserException;
import Server.UserManager;
import data.User;

public class SignInCommand<DatabaseUser> extends Command {
    private UserManager databaseUser;

    public SignInCommand(UserManager databaseUserManager) {
        super("sign_in", "sign in");
        this.databaseUser = databaseUserManager;
    }

    @Override
    public String execute(Object objectArgument, User user, Integer intArgument) {
        try {
            if (databaseUser.checkUserByLoginAndPassword(user)) {
                databaseUser.updateOnline(user, true);
                return "Authorization was successful\n";
            }
            else return "User is not found. Check login and password";
        } catch (MultiUserException e) {
            return "This user is already logged in!";
        } catch (DatabaseException e) {
            return "An error occurred while executing the SELECT_USER_BY_USERNAME_AND_PASSWORD query!";
        }
    }
}