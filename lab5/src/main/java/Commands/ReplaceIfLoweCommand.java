package Commands;

import Instruments.CollectionManager;
import Instruments.UserScanner;
import Organization.Organization;
import java.util.Map;

/**
 *  'remove_if_lower' command. Replaces the value of the collection item by id
 *  if the new value is less than the old one
 */
public class ReplaceIfLoweCommand extends Command{
    /**
     * 'collection manager' with which the collection is managed
     */
    private final CollectionManager collectionManager;
    /**
     * user scanner which reads data from the command line
     */
    private final UserScanner userScanner;

    public ReplaceIfLoweCommand(CollectionManager collectionManager, UserScanner userScanner) {
        super("replace_if_lowe", "replaces the value of the collection item by id" +
                " if the new value is less than the old one, id is a positive integer");
        this.collectionManager = collectionManager;
        this.userScanner = userScanner;
    }

    /**
     * Executes the command.
     * @return Command execute status.
     */
    @Override
    public boolean execute(String argument) {
        Integer key = null;
        try {
            key = Integer.parseInt(argument);
        }
        catch (NumberFormatException nfe)
        {
            System.out.println("the key must be a positive integer");
            return false;
        }
        for (Map.Entry<Integer, Organization> entry : collectionManager.getCollection().entrySet()){
            if (entry.getValue().getId().equals(key)) {
                Organization organization = userScanner.enterOrganization();
                if (organization.compareTo(entry.getValue()) < 0) {
                    collectionManager.addToCollection(key, organization);
                    System.out.println("item added to collection successfully");
                    return true;
                } else {
                    System.out.println("the new value is greater than the old one");
                    return false;
                }
            }
        }
        System.out.println("the organization with the given id does not exist," +
                " use 'show' to dump the elements of the collection");
        return false;
    }

}
