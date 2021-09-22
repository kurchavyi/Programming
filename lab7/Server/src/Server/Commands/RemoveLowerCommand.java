package Server.Commands;

import Exceptions.DatabaseException;
import Server.CollectionManager;
import Server.DatabaseCollectionManager;
import data.Organization.FlyOrganization;
import data.Organization.Organization;
import data.User;

import java.util.ArrayList;
import java.util.Map;

/**
 *  'remove_lower' command. Removes from the collection all elements less than the specified one
 */
public class RemoveLowerCommand extends Command {
    /**
     * 'collection manager' with which the collection is managed
     */
    private final CollectionManager collectionManager;

    private DatabaseCollectionManager databaseCollectionManager;

    public RemoveLowerCommand(CollectionManager collectionManager, DatabaseCollectionManager databaseCollectionManager) {
        super("remove_lower", "removes from the collection all elements less than the specified one");
        this.collectionManager = collectionManager;
        this.databaseCollectionManager = databaseCollectionManager;
    }

    /**
     * Executes the command.
     * @return Command execute status.
     */
    @Override
    public String execute(Object argument, User user, Integer intArgument) {
        if (user == null) return "need authorization";
        FlyOrganization organization = (FlyOrganization) argument;
        ArrayList<Integer> deleteKey = new ArrayList<Integer>();
        for (Map.Entry<Integer, Organization> entry : collectionManager.getCollection().entrySet()) {
            if (entry.getValue().compareToFly(organization) < 0 && entry.getValue().getOwner().equals(user)) {
                deleteKey.add(entry.getKey());
            }
        }
        try {
            if (!deleteKey.isEmpty()) {
                for (Integer key : deleteKey) {
                    int id = collectionManager.getIdFromKey(key);
                    databaseCollectionManager.deleteOrganizationById(id);
                    collectionManager.removeFromCollection(key);
                }
            } else return "nothing has been deleted";
        } catch (DatabaseException e) {
            return "error";
        }
        return deleteKey.size() + " items were removed";
    }
}
