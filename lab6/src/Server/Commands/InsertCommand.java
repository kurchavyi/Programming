package Server.Commands;

import Server.CollectionManager;
import Server.Instruments.Marshal;
import Server.Instruments.MarshalJson;
import Server.Organization.Organization;

/**
 * 'insert' command. Add a new item with the given key.
 */
public class InsertCommand extends Command {
    /**
     * 'collection manager' with which the collection is managed
     */
    private final CollectionManager collectionManager;
    /**
     * user scanner which reads data from the command line
     */

    public InsertCommand(CollectionManager collectionManager) {
        super("insert", "add a new item with the given key. The key can only be a positive integer. " +
                "if an existing key is entered, then the item in the collection is overwritten with a new one");
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
        if (collectionManager.isExistOrganizationThisKey(key)) {
            return "an element with the given key already exists. Use 'update' if you want to change" +
                    " the value for this key";
        }
        collectionManager.addToCollection(key, organization);
        return "organization successfully added";
    }
}
