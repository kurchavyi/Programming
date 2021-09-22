package Server.Commands;

import Exceptions.DatabaseException;
import Server.CollectionManager;
import Server.DatabaseCollectionManager;
import data.Organization.FlyOrganization;
import data.Organization.Organization;
import data.User;

import java.util.Map;

/**
 *  'update' command. Updates the value of the element id
 */
public class UpdateCommand extends Command {
    /**
     * 'collection manager' with which the collection is managed
     */
    private final CollectionManager collectionManager;

    private DatabaseCollectionManager databaseCollectionManager;

    public UpdateCommand(CollectionManager collectionManager, DatabaseCollectionManager databaseCollectionManager) {
        super("update", "updates the value of the element id, which is equal to the given," +
                " id should be entered after a space after the command, id is a positive integer");
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
        try {
            Integer updateKey = null;
            FlyOrganization flyOrganization = (FlyOrganization) argument;
            int id = intArgument;
            for (Map.Entry<Integer, Organization> entry : collectionManager.getCollection().entrySet()){
                if (entry.getValue().getId().equals(id)) {
                    if (!user.equals(entry.getValue().getOwner())) return "you can only update items that are owned by you";
                    databaseCollectionManager.updateOrganizationByID(id,flyOrganization );
                    updateKey = entry.getKey();
                }
            }
            if (updateKey != null) {
                collectionManager.getCollection().get(updateKey).setName(flyOrganization.getName());
                collectionManager.getCollection().get(updateKey).setCoordinates(flyOrganization.getCoordinates());
                collectionManager.getCollection().get(updateKey).setAnnualTurnover(flyOrganization.getAnnualTurnover());
                collectionManager.getCollection().get(updateKey).setFullName(flyOrganization.getFullName());
                collectionManager.getCollection().get(updateKey).setEmployeesCount(flyOrganization.getEmployeesCount());
                collectionManager.getCollection().get(updateKey).setType(flyOrganization.getType());
                collectionManager.getCollection().get(updateKey).setPostalAddress(flyOrganization.getPostalAddress());
                return "command completed successful";
            }
            return "the organization with the given id does not exist," +
                    " use 'show' to dump the elements of the collection";
        } catch (NumberFormatException e) {
            return "id can only be a positive integer";
        } catch (DatabaseException e) {
            return "error";
        }
    }
}
