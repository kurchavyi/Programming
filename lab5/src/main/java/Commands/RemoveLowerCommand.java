package Commands;

import Instruments.CollectionManager;
import Instruments.UserScanner;
import Organization.Organization;
import java.util.ArrayList;
import java.util.Map;

/**
 *  'remove_lower' command. Removes from the collection all elements less than the specified one
 */
public class RemoveLowerCommand extends Command{
    /**
     * 'collection manager' with which the collection is managed
     */
    private final CollectionManager collectionManager;
    /**
     * user scanner which reads data from the command line
     */
    private final UserScanner userScanner;

    public RemoveLowerCommand(CollectionManager collectionManager, UserScanner userScanner) {
        super("remove_lower", "removes from the collection all elements less than the specified one");
        this.collectionManager = collectionManager;
        this.userScanner = userScanner;
    }

    /**
     * Executes the command.
     * @return Command execute status.
     */
    @Override
    public boolean execute(String argument) {
        Organization organization = userScanner.enterOrganization();
        ArrayList<Integer> deleteKey = new ArrayList<Integer>();
        for (Map.Entry<Integer, Organization> entry : collectionManager.getCollection().entrySet()) {
            if (entry.getValue().compareTo(organization) < 0) {
                deleteKey.add(entry.getKey());
            }
        }
        if (!deleteKey.isEmpty()) {
            for (Integer key : deleteKey) {
                collectionManager.removeFromCollection(key);
            }
        }
        return false;
    }
}
