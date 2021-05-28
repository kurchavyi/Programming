package Commands;

import Instruments.CollectionManager;

/**
 *  'min_by_creation_date' command. Outputs the collection object that was created first.
 *  If the creation date matches, then displays any of them..
 */
public class MinByCreationDateCommand extends Command {
    /**
     * 'collection manager' with which the collection is managed
     */
    private final CollectionManager collectionManager;

    public MinByCreationDateCommand(CollectionManager collectionManager) {
        super("min_by_creation_date", " displays any object in the collection whose creationDate" +
                " field is the minimum. Needs no arguments");
        this.collectionManager = collectionManager;
    }

    /**
     * Executes the command.
     * @return Command execute status.
     */
    @Override
    public boolean execute(String argument) {
        System.out.println(collectionManager.getOrganizationMinByCreationDate());
        return true;
    }
}
