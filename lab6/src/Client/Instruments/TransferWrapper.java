package Client.Instruments;

public class TransferWrapper {
    private String nameCommand;
    private Object argument;

    public TransferWrapper(String nameCommand){
        this.nameCommand = nameCommand;
        this.argument = null;
    }

    public TransferWrapper(String nameCommand, Object argument) {
        this.nameCommand = nameCommand;
        this.argument = argument;
    }
    public TransferWrapper() {};

    public String getNameCommand() {
        return nameCommand;
    }

    public Object getArgument() {
        return argument;
    }

    public void setNameCommand(String nameCommand) {
        this.nameCommand = nameCommand;
    }

    public void setArgument(Object argument) {
        this.argument = argument;
    }

    @Override
    public String toString() {
        return "{ \nname command: " + this.nameCommand + "\n" +
                "argument " + this.argument + "\n}";
    }
}
