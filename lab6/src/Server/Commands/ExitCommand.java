package Server.Commands;

/**
 * 'exit' command. Closes the program.
 */

public class ExitCommand extends Command {
    public ExitCommand() {
        super("exit", "ends the program (without saving to file). Needs no arguments");
    }

    /**
     * Executes the command.
     * @return Command execute status.
     */
    @Override
    public String execute(Object argument) {
        return "application stopped";
    }
}
