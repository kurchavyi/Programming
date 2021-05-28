package Commands;

import Instruments.CollectionManager;
import Instruments.UserScanner;
import Organization.Organization;

/**
 * 'insert' command. Add a new item with the given key.
 */
public class InsertCommand extends Command {
    /**
     * 'collection manager' with which the collection is managed
     */
    private final CollectionManager collectionManager;
    /**
     * user scanner which reads data from the command line
     */
    private final UserScanner userScanner;

    public InsertCommand(CollectionManager collectionManager, UserScanner userScanner) {
        super("insert", "add a new item with the given key. The key can only be a positive integer. " +
                "if an existing key is entered, then the item in the collection is overwritten with a new one");
        this.userScanner = userScanner;
        this.collectionManager = collectionManager;
    }

    /**
     * Executes the command.
     * @return Command execute status.
     */
    @Override
    public boolean execute(String argument) {
        Integer key = null;
        try {
            key = Integer.parseInt(argument.split(" ", 2)[0]);;
        }
        catch (NumberFormatException nfe)
        {
            System.out.println("the key must be an integer and not repeated");
            return false;
        }
        Organization organization = userScanner.enterOrganization();
        collectionManager.addToCollection(key, organization);
        return true;
    }
}
