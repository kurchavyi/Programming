package Commands;

import Instruments.CollectionManager;
import Instruments.UserScanner;
import Organization.Organization;
import Organization.Address;
import Organization.OrganizationType;
import Organization.Coordinates;
import java.util.Map;

/**
 *  'update' command. Updates the value of the element id
 */
public class UpdateCommand extends Command {
    /**
     * 'collection manager' with which the collection is managed
     */
    private final CollectionManager collectionManager;
    /**
     * user scanner which reads data from the command line
     */
    private final UserScanner userScanner;

    public UpdateCommand(CollectionManager collectionManager, UserScanner userScanner) {
        super("update", "updates the value of the element id, which is equal to the given," +
                " id should be entered after a space after the command, id is a positive integer");
        this.collectionManager = collectionManager;
        this.userScanner = userScanner;
    }

    /**
     * Executes the command.
     * @return Command execute status.
     */
    @Override
    public boolean execute(String argument) {
        try {
            int arg = Integer.parseInt(argument.split(" ", 2)[0]);
            for (Map.Entry<Integer, Organization> entry : collectionManager.getCollection().entrySet()){
                if (entry.getValue().getId().equals(arg)) {
                    String name = userScanner.enterName();
                    Coordinates coordinates = userScanner.enterCoordinates();
                    Float annualTurnover = userScanner.enterAnnualTurnover();
                    String fullName = userScanner.enterFullName();
                    Integer employeesCount = userScanner.enterEmployeesCount();
                    OrganizationType type = userScanner.enterOrganizationType();
                    Address postalAddress = userScanner.enterPostalAddress();
                    entry.getValue().setName(name);
                    entry.getValue().setCoordinates(coordinates);
                    entry.getValue().setAnnualTurnover(annualTurnover);
                    entry.getValue().setFullName(fullName);
                    entry.getValue().setEmployeesCount(employeesCount);
                    entry.getValue().setType(type);
                    entry.getValue().setPostalAddress(postalAddress);
                    return true;
                }
            }
            System.out.println("the organization with the given id does not exist," +
                    " use 'show' to dump the elements of the collection");
            return false;
        } catch (NumberFormatException e) {
            System.out.println("id can only be a positive integer");
            return false;
        }
    }
}
