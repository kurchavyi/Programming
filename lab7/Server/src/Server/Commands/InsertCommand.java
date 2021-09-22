package Server.Commands;

import Exceptions.DatabaseException;
import Server.CollectionManager;
import Server.DatabaseCollectionManager;
import data.Organization.FlyOrganization;
import data.User;

/**
 * 'insert' command. Add a new item with the given key.
 */
public class InsertCommand extends Command {
    /**
     * 'collection manager' with which the collection is managed
     */
    private final CollectionManager collectionManager;

    private DatabaseCollectionManager databaseCollectionManager;

    public InsertCommand(CollectionManager collectionManager, DatabaseCollectionManager databaseCollectionManager) {
        super("insert", "add a new item with the given key. The key can only be a positive integer. " +
                "if an existing key is entered, then the item in the collection is overwritten with a new one");
        this.collectionManager = collectionManager;
        this.databaseCollectionManager = databaseCollectionManager;
    }

    /**
     * Executes the command.
     * @return Command execute status.
     */
    @Override
    public String execute(Object argument, User user, Integer key) {
        if (user == null) return "need authorization";
        FlyOrganization organization = (FlyOrganization) argument;
        if (collectionManager.isExistOrganizationThisKey(key)) {
            return "an element with the given key already exists. Use 'update' if you want to change" +
                    " the value for this key";
        }
        try {
            collectionManager.addToCollection(key, databaseCollectionManager.insertOrganization(organization, user, key));
        } catch (DatabaseException e) {
            return "error";
        }
        return "organization successfully added";
    }
}
