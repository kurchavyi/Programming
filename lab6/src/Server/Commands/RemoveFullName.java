package Server.Commands;

import Server.CollectionManager;
import Server.Organization.Organization;

import java.util.ArrayList;
import java.util.TreeMap;

/**
 *  'remove_any_by_full_name' command. Deletes an organization by a given full name
 *
 */

public class RemoveFullName extends Command {
    /**
     * 'collection manager' with which the collection is managed
     */
    private final CollectionManager collectionManager;

    public RemoveFullName(CollectionManager collectionManager) {
        super("remove_any_by_full_name", " removes one item from the collection whose fullName" +
                " field value is equivalent to the specified one. Unhidden Argument is " +
                "Full name of the organization,the argument must be supplied separated by a space after the command");
        this.collectionManager = collectionManager;
    }

    /**
     * Executes the command.
     * @return Command execute status.
     */
    @Override
    public String execute(Object argument) {
        String arg = (String) argument;
        arg = arg.replaceAll("\\s+", " ");
        TreeMap<Integer, Organization> collection = collectionManager.getCollection();
        ArrayList<String> fileNames = new ArrayList<String>();
        for (Organization organization : collection.values()) {
            fileNames.add(organization.getFullName());
        }
        int key;
        for (String fullName : fileNames) {
            if (arg.indexOf(fullName) == 0){
                key = collectionManager.getOrganizationByFullName(fullName);
                collectionManager.removeFromCollection(key);
                return "organization successfully deleted";
            }
        }
        return "An organization with such a fully qualified name does not exist," +
                " use 'show' to display all items in the collection";
    }
}