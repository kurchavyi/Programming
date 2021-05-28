package Commands;

/**
 * 'help' command. Just for logical structure. Does nothing.
 */
public class HelpCommand extends Command{
    public HelpCommand(){
        super("help", "displays command descriptions. Needs no arguments");
    }

    /**
     * Executes the command.
     * @return Command execute status.
     */
    @Override
    public boolean execute(String argument) {
        return true;
    }
}
