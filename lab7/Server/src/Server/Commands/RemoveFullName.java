package Server.Commands;

import Exceptions.DatabaseException;
import Server.CollectionManager;
import Server.DatabaseCollectionManager;
import data.Organization.Organization;
import data.User;

import java.util.ArrayList;
import java.util.Map;

/**
 *  'remove_any_by_full_name' command. Deletes an organization by a given full name
 *
 */

public class RemoveFullName extends Command {
    /**
     * 'collection manager' with which the collection is managed
     */
    private final CollectionManager collectionManager;
    private DatabaseCollectionManager databaseCollectionManager;

    public RemoveFullName(CollectionManager collectionManager, DatabaseCollectionManager databaseCollectionManager) {
        super("remove_any_by_full_name", " removes one item from the collection whose fullName" +
                " field value is equivalent to the specified one. Unhidden Argument is " +
                "Full name of the organization,the argument must be supplied separated by a space after the command");
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
        String arg = (String) argument;
        arg = arg.replaceAll("\\s+", " ");
        Map<Integer, Organization> collection = collectionManager.getCollection();
        ArrayList<String> fileNames = new ArrayList<String>();
        for (Organization organization : collection.values()) {
            fileNames.add(organization.getFullName());
        }
        int key;
        try {
            for (String fullName : fileNames) {
                System.out.println("check name");
                System.out.println(fullName);
                System.out.println(arg);
                if (arg.indexOf(fullName) == 0) {
                    System.out.println("all right");
                    key = collectionManager.getOrganizationByFullName(fullName);
                    if (!collectionManager.getCollection().get(key).getOwner().equals(user)) {
                        return "you can only delete your items";
                    }
                    databaseCollectionManager.deleteOrganizationById(collectionManager.getIdFromKey(key));
                    collectionManager.removeFromCollection(key);
                    return "organization successfully deleted";
                }
            }
        } catch (DatabaseException e) {
            return "error";
        }
        return "An organization with such a fully qualified name does not exist," +
                " use 'show' to display all items in the collection";
    }
}