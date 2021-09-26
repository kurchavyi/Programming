package Server.Commands;

import Server.CollectionManager;

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
    public String execute(Object argument) {
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
        String result ="";
        result += "collection details:\n";
        result += "class: "  + collectionManager.collectionType() + "\n";
        result += "amount of elements: " + collectionManager.collectionSize() + "\n";
        result += "date of last save: " + lastSaveTimeString + "\n";
        result += "date of last initialization: " + lastInitTimeString + "\n";
        return result;
    }
}
