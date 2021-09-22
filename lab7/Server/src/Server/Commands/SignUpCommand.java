package Server.Commands;


import Exceptions.DatabaseException;
import Server.UserManager;
import data.User;

public class SignUpCommand extends Command {
    private UserManager databaseUserManager;

    public SignUpCommand(UserManager databaseUserManager) {
        super("sign_up", "new user registration");
        this.databaseUserManager = databaseUserManager;
    }

    @Override
    public String execute(Object argument, User user, Integer intArgument) {
        try {
            if (databaseUserManager.insertUser(user))
                return "registration completed successfully";
            else {
                System.out.println("not true");
                return "This user already exists!";
            }
        } catch (DatabaseException exception) {
            return "An error occurred while accessing the database!";
        }
    }
}
