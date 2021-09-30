package Server.Commands;

import Exceptions.DatabaseException;
import Server.CollectionManager;
import Server.DatabaseCollectionManager;
import data.Organization.FlyOrganization;
import data.User;

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
        super("replace_if_lowe", "replaces the value of the collection item by key" +
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
        if (user == null) return "need authorization";
        FlyOrganization organization = (FlyOrganization) argument;
        Integer updateKey = intArgument;
        try {
            if (collectionManager.getCollection().containsKey(updateKey)) {
                if (!collectionManager.getCollection().get(updateKey).getOwner().equals(user)) {
                    return "you can only change your items";
                }
                if (organization.compareTo(collectionManager.getCollection().get(updateKey)) < 0) {
                    databaseCollectionManager.
                            updateOrganizationByID(collectionManager.getCollection().get(updateKey).getId(), organization);
                    collectionManager.getCollection().get(updateKey).setName(organization.getName());
                    collectionManager.getCollection().get(updateKey).setCoordinates(organization.getCoordinates());
                    collectionManager.getCollection().get(updateKey).setAnnualTurnover(organization.getAnnualTurnover());
                    collectionManager.getCollection().get(updateKey).setFullName(organization.getFullName());
                    collectionManager.getCollection().get(updateKey).setEmployeesCount(organization.getEmployeesCount());
                    collectionManager.getCollection().get(updateKey).setType(organization.getType());
                    collectionManager.getCollection().get(updateKey).setPostalAddress(organization.getPostalAddress());
                    return "command completed successful";
                } else return "the new value is greater than the old one";
            }
        } catch (DatabaseException e) {
            return "error";
        }
        return "the organization with the given key does not exist," +
                " use 'show' to dump the elements of the collection";
    }
}
