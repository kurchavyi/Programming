package Server.Commands;

import Server.CollectionManager;

/**
 *  'clear' command. Clears the collection.
 */
public class ClearCommand extends Command {
    /**
     * 'collection manager' with which the collection is managed
     */
    private final CollectionManager collectionManager;

    public ClearCommand(CollectionManager collectionManager) {
        super("clear", "clears the collection, does not need any arguments");
        this.collectionManager = collectionManager;
    }

    /**
     * Executes the command.
     * @return Command execute status.
     */
    @Override
    public String execute(Object argument) {
        collectionManager.clearCollection();
        return "collection cleared";
    }
}
