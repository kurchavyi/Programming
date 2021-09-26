package Server.Commands;

import Server.CollectionManager;
import Server.Organization.Organization;

import java.util.ArrayList;

/**
 *  'print_ascending' command. Displays the elements of the collection in ascending order.
 */
public class PrintAscendingCommand extends Command {
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
    public String execute(Object argument) {
        ArrayList<Organization> SortOrganizations = collectionManager.getArrayListSortOrganization();
        String result = "";
        if (SortOrganizations.isEmpty()) {
            return "collection is empty";
        }
        for (Organization organization : SortOrganizations) {
            result += organization.toString();
        }
        return result;
    }
}
