package Server.Commands;

import Exceptions.DatabaseException;
import Server.CollectionManager;
import Server.DatabaseCollectionManager;
import data.Organization.Organization;
import data.User;

import java.util.ArrayList;
import java.util.Map;

/**
 *  'clear' command. Clears the collection.
 */
public class ClearCommand extends Command {
    /**
     * 'collection manager' with which the collection is managed
     */
    private final CollectionManager collectionManager;

    private DatabaseCollectionManager databaseCollectionManager;

    public ClearCommand(CollectionManager collectionManager, DatabaseCollectionManager databaseCollectionManager) {
        super("clear", "clears the collection, does not need any arguments");
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
        Map<Integer, Organization> organizations = collectionManager.getCollection();
        ArrayList<Integer> idDeleteOrganizations = new ArrayList<Integer>();
        for (Map.Entry<Integer, Organization> entry : organizations.entrySet()) {
            if (entry.getValue().getOwner().equals(user)) {
                idDeleteOrganizations.add(entry.getValue().getId());
            }
        }
        if (idDeleteOrganizations.isEmpty()) {
            return "your items are not in the collection";
        }
        else {
            try {
                System.out.println(idDeleteOrganizations);
                databaseCollectionManager.clearCollection(idDeleteOrganizations);
                collectionManager.clearCollection(idDeleteOrganizations);
            } catch (DatabaseException e) {
                return "error database";
            }
        }
        return "collection cleared";
    }
}
