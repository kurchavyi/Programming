package Commands;

import Instruments.CollectionManager;

/**
 *  'remove_key' command. Deletes an organization by a given key
 */
public class RemoveKeyCommand extends Command{
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
    public boolean execute(String argument) {
        try {
            int arg = Integer.parseInt(argument.split(" ", 2)[0]);
            if (collectionManager.getCollection().containsKey(arg)) {
                collectionManager.removeFromCollection(arg);
                System.out.println("organization successfully deleted");
            }
            else {
                System.out.println("the organization by the given key does not exist in the collection use" +
                        " 'show' to display information about the collection");
                return false;
            }
        } catch (NumberFormatException e) {
            System.out.println("The key can only be a positive integer");
            return false;
        }
        return true;
    }
}
