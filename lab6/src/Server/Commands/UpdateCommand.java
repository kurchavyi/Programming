package Server.Commands;

import Server.CollectionManager;
import Server.Instruments.Marshal;
import Server.Instruments.MarshalJson;
import Server.Organization.Address;
import Server.Organization.Coordinates;
import Server.Organization.Organization;
import Server.Organization.OrganizationType;

import java.util.Map;

/**
 *  'update' command. Updates the value of the element id
 */
public class UpdateCommand extends Command {
    /**
     * 'collection manager' with which the collection is managed
     */
    private final CollectionManager collectionManager;

    public UpdateCommand(CollectionManager collectionManager) {
        super("update", "updates the value of the element id, which is equal to the given," +
                " id should be entered after a space after the command, id is a positive integer");
        this.collectionManager = collectionManager;
    }

    /**
     * Executes the command.
     * @return Command execute status.
     */
    @Override
    public String execute(Object argument) {
        try {
            MarshalJson marshalJson = new MarshalJson();
            Marshal marshal = new Marshal();
            Organization organization = marshal.JsonOrganization(marshalJson.inJson(argument));
            int id = organization.getId();
            for (Map.Entry<Integer, Organization> entry : collectionManager.getCollection().entrySet()){
                if (entry.getValue().getId().equals(id)) {
                    entry.getValue().setName(organization.getName());
                    entry.getValue().setCoordinates(organization.getCoordinates());
                    entry.getValue().setAnnualTurnover(organization.getAnnualTurnover());
                    entry.getValue().setFullName(organization.getFullName());
                    entry.getValue().setEmployeesCount(organization.getEmployeesCount());
                    entry.getValue().setType(organization.getType());
                    entry.getValue().setPostalAddress(organization.getPostalAddress());
                    return "command completed successful";
                }
            }
            return "the organization with the given id does not exist," +
                    " use 'show' to dump the elements of the collection";
        } catch (NumberFormatException e) {
            return "id can only be a positive integer";
        }
    }
}
