package Server.Commands;

import Server.CollectionManager;
import Server.Organization.Organization;

/**
 *  'remove_key' command. Deletes an organization by a given key
 */
public class RemoveKeyCommand extends Command {
    /**
     * 'collection manager' with which the collection is managed
     */
    private final CollectionManager collectionManager;

    public RemoveKeyCommand(CollectionManager collectionManager) {
        super("remove_key", "removes an item from the collection by its key." +
                " The required argument is an integer, the argument must be supplied " +
                "separated by a space after the command");
        this.collectionManager = collectionManager;
    }

    /**
     * Executes the command.
     * @return Command execute status.
     */
    @Override
    public String execute(Object argument) {
        double argument1 = (double) argument;
        Integer arg = (int) argument1;
        try {
            if (collectionManager.getCollection().containsKey(arg)) {
                collectionManager.removeFromCollection(arg);
                return "organization successfully deleted";
            }
            else {
                return "the organization by the given key does not exist in the collection use" +
                        " 'show' to display information about the collection";
            }
        } catch (NumberFormatException e) {
            return "The key can only be a positive integer";
        }
    }
}
