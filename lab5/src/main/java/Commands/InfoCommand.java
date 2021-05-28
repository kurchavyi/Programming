package Commands;

import Instruments.CollectionManager;
import java.time.LocalDateTime;

/**
 * 'info' command. Prints information about the collection.
 */

public class InfoCommand extends Command {
    /**
     * 'collection manager' with which the collection is managed
     */
    private final CollectionManager collectionManager;

    public InfoCommand(CollectionManager collectionManager) {
        super("info", "displays information about the collection (type, date of initialization, number of elements)." +
                " Does not need any arguments");
        this.collectionManager = collectionManager;
    }

    /**
     * Executes the command.
     * @return Command execute status.
     */
    @Override
    public boolean execute(String argument) {
        LocalDateTime lastInitTime = collectionManager.getLastInitTime();
        String lastInitTimeString = null;
        if (lastInitTime == null) {
            lastInitTimeString = "no initialization has occurred in this session yet";
            System.out.println(lastInitTimeString);
        }
        else {
            lastInitTimeString = lastInitTime.toString();
        }

        LocalDateTime lastSaveTime = collectionManager.getLastSaveTime();
        String lastSaveTimeString = null;
        if (lastSaveTime == null) {
            lastSaveTimeString = "no save has occurred in this session yet";
        }
        else {
            lastSaveTimeString = lastSaveTime.toString();
        }

        System.out.println("collection details:");
        System.out.println("class: "  + collectionManager.collectionType());
        System.out.println("amount of elements: " + collectionManager.collectionSize());
        System.out.println("date of last save: " + lastSaveTimeString);
        System.out.println("date of last initialization: " + lastInitTimeString);
        return true;
    }
}
