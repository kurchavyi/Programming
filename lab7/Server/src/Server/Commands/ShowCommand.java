package Server.Commands;

import Server.CollectionManager;
import data.Organization.Organization;
import data.User;

import java.util.Locale;
import java.util.Map;

/**
 *  'show' command. Prints out all the elements of the collection
 */
public class ShowCommand extends Command {
    /**
     * 'collection manager' with which the collection is managed
     */
    private final CollectionManager collectionManager;

    public ShowCommand(CollectionManager collectionManager) {
        super("show", "prints out all the elements of the collection. Needs no arguments");
        this.collectionManager = collectionManager;
    }

    /**
     * Executes the command.
     * @return Command execute status.
     */
    @Override
    public String execute(Object argument, User user, Integer intArgument) {
        if (user == null) return "need authorization";
        if (collectionManager.getCollection().isEmpty()) {
            return "collection is empty";
        }
        String result = "";
        for (Map.Entry<Integer, Organization> entry : collectionManager.getCollection().entrySet()) {
            if (entry.getValue().getOwner().equals(user)) {
                result += entry.getKey().toString().toUpperCase(Locale.ROOT) + " (your element):\n" + entry.getValue().toString() + "\n";
            }
            else {
                result += entry.getKey() + ":\n" + entry.getValue().toString() + "\n";
            }
        }
        return result;
    }
}
