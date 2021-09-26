package Server.Commands;

import java.util.Objects;

/**
 * abstract class from which all commands inherit
 */
public abstract class Command {
    /**
     * name command
     */
    private final String name;
    /**
     * description command
     */
    private final String description;

    public Command(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String execute(Object argument){
        return "";
    };

    /**
     * @return Name command.
     */
    public String getName() {
        return name;
    }

    /**
     * @return Description of the command.
     */
    public String getDescription() {
        return description;
    }

    /**
     * scrapes objects of class organization
     * @param o the object to be compared
     * @return true if objects are equal, else false
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Command command = (Command) o;
        return Objects.equals(name, command.name) &&
                Objects.equals(description, command.description);
    }

    /**
     *Method for printing Client.Organization-class object into string representation
     * @return a string that contains information about the organization
     */
    @Override
    public String toString() {
        return name + ": " + description;
    }
}
