package Commands;

import Instruments.CollectionManager;

/**
 *  'show' command. Prints out all the elements of the collection
 */
public class ShowCommand extends  Command {
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
    public boolean execute(String argument) {
        System.out.println(collectionManager);
        return true;
    }
}
