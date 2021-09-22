package Server.Commands;

import Exceptions.DatabaseException;
import Server.CollectionManager;
import Server.DatabaseCollectionManager;
import data.User;


/**
 *  'remove_key' command. Deletes an organization by a given key
 */
public class RemoveKeyCommand extends Command {
    /**
     * 'collection manager' with which the collection is managed
     */
    private final CollectionManager collectionManager;
    private DatabaseCollectionManager databaseCollectionManager;

    public RemoveKeyCommand(CollectionManager collectionManager, DatabaseCollectionManager databaseCollectionManager) {
        super("remove_key", "removes an item from the collection by its key." +
                " The required argument is an integer, the argument must be supplied " +
                "separated by a space after the command");
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
        int key = (int) argument;
        try {
            if (collectionManager.getCollection().containsKey(key)) {
                if (!collectionManager.getCollection().get(key).getOwner().equals(user)) {
                    return "you can only delete your items";
                }
                databaseCollectionManager.deleteOrganizationById(collectionManager.getIdFromKey(key));
                collectionManager.removeFromCollection(key);

                return "organization successfully deleted";
            }
            else {
                return "the organization by the given key does not exist in the collection use" +
                        " 'show' to display information about the collection";
            }
        } catch (NumberFormatException e) {
            return "The key can only be a positive integer";
        } catch (DatabaseException e) {
            return "error";
        }
    }
}
