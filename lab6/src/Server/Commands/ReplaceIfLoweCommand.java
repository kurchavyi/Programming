package Server.Commands;

import Server.CollectionManager;
import Server.Instruments.Marshal;
import Server.Instruments.MarshalJson;
import Server.Organization.Organization;

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

    public ReplaceIfLoweCommand(CollectionManager collectionManager) {
        super("replace_if_lowe", "replaces the value of the collection item by id" +
                " if the new value is less than the old one, id is a positive integer");
        this.collectionManager = collectionManager;
    }

    /**
     * Executes the command.
     * @return Command execute status.
     */
    @Override
    public String execute(Object argument) {
        MarshalJson marshalJson = new MarshalJson();
        Marshal marshal = new Marshal();
        Organization organization = marshal.JsonOrganization(marshalJson.inJson(argument));
        Integer key = organization.getId();
        organization.setId();
        for (Map.Entry<Integer, Organization> entry : collectionManager.getCollection().entrySet()){
            if (entry.getKey().equals(key)) {
                if (organization.compareTo(entry.getValue()) < 0) {
                    collectionManager.addToCollection(key, organization);
                    return "item added to collection successfully";
                } else {
                    return "the new value is greater than the old one";
                }
            }
        }
        return "the organization with the given id does not exist," +
                " use 'show' to dump the elements of the collection";
    }
}
