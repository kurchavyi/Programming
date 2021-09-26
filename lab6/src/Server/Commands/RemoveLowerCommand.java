package Server.Commands;

import Server.CollectionManager;
import Server.Instruments.Marshal;
import Server.Instruments.MarshalJson;
import Server.Organization.Organization;

import java.util.ArrayList;
import java.util.Map;

/**
 *  'remove_lower' command. Removes from the collection all elements less than the specified one
 */
public class RemoveLowerCommand extends Command {
    /**
     * 'collection manager' with which the collection is managed
     */
    private final CollectionManager collectionManager;

    public RemoveLowerCommand(CollectionManager collectionManager) {
        super("remove_lower", "removes from the collection all elements less than the specified one");
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
        ArrayList<Integer> deleteKey = new ArrayList<Integer>();
        for (Map.Entry<Integer, Organization> entry : collectionManager.getCollection().entrySet()) {
            if (entry.getValue().compareTo(organization) < 0) {
                deleteKey.add(entry.getKey());
            }
        }
        if (!deleteKey.isEmpty()) {
            for (Integer key : deleteKey) {
                collectionManager.removeFromCollection(key);
            }
        } else return "nothing has been deleted";
        return deleteKey.size() + "items were removed";
    }
}
