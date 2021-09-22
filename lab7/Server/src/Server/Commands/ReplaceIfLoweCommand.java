package Server.Commands;

import Exceptions.DatabaseException;
import Server.CollectionManager;
import Server.DatabaseCollectionManager;
import data.Organization.FlyOrganization;
import data.Organization.Organization;
import data.User;

import java.util.Map;

/**
 *  'remove_if_lower' command. Replaces the value of the collection item by id
 *  if the new value is less than the old one
 */
public class ReplaceIfLoweCommand extends Command {
    /**
     * 'collection manager' with which the collection is managed
     */
    private final CollectionManager collectionManager;

    private DatabaseCollectionManager databaseCollectionManager;

    public ReplaceIfLoweCommand(CollectionManager collectionManager, DatabaseCollectionManager databaseCollectionManager) {
        super("replace_if_lowe", "replaces the value of the collection item by id" +
                " if the new value is less than the old one, id is a positive integer");
        this.collectionManager = collectionManager;
        this.databaseCollectionManager = databaseCollectionManager;
    }

    /**
     * Executes the command.
     * @return Command execute status.
     */
    @Override
    public String execute(Object argument, User user, Integer intArgument) {
        FlyOrganization organization = (FlyOrganization) argument;
        Integer id = intArgument;
        Integer key;
        try {
            for (Map.Entry<Integer, Organization> entry : collectionManager.getCollection().entrySet()) {
                if (entry.getValue().getId().equals(id)) {
                    if (organization.compareTo(entry.getValue()) < 0) {
                        collectionManager.removeById(id);
                        key = entry.getKey();
                        databaseCollectionManager.deleteOrganizationById(id);
                        collectionManager.addToCollection(key,
                                databaseCollectionManager.insertOrganization(organization, user, key));
                        return "item added to collection successfully";
                    } else {
                        return "the new value is greater than the old one";
                    }
                }
            }
        } catch (DatabaseException e) {
            return "error";
        }
        return "the organization with the given id does not exist," +
                " use 'show' to dump the elements of the collection";
    }
}
