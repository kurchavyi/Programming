package Commands;

import Instruments.CollectionManager;
import Organization.Organization;
import java.util.ArrayList;

/**
 *  'print_ascending' command. Displays the elements of the collection in ascending order.
 */
public class PrintAscendingCommand extends Command{
    /**
     * 'collection manager' with which the collection is managed
     */
    private final CollectionManager collectionManager;

    public PrintAscendingCommand(CollectionManager collectionManager){
        super("print_ascending", "displays the elements of the collection in ascending order." +
                " Needs no arguments");
        this.collectionManager = collectionManager;
    }

    /**
     * Executes the command.
     * @return Command execute status.
     */
    @Override
    public boolean execute(String argument) {
        ArrayList<Organization> SortOrganizations = collectionManager.getArrayListSortOrganization();
        for (Organization organization : SortOrganizations) {
            System.out.println(organization);
        }
        return true;
    }
}
