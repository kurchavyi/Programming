package Commands;

import Instruments.CollectionManager;

/**
 *  'save' command. Saves the collection to a file
 */
public class SaveCommand extends Command {
    /**
     * 'collection manager' with which the collection is managed
     */
    private final CollectionManager collectionManager;

    public SaveCommand(CollectionManager collectionManager) {
        super("save", "saves the collection to a file. Needs no arguments");
        this.collectionManager = collectionManager;
    }

    /**
     * Executes the command.
     * @return Command execute status.
     */
    @Override
    public boolean execute(String argument) {
        collectionManager.save();
        return true;
    }

}
