package data;

import java.io.Serializable;

public class TransferWrapper implements Serializable {
    private String nameCommand;
    private Object argument;
    private  User user;
    private Integer integerArgument;

    public TransferWrapper(String nameCommand, Object argument, User user, Integer integerArgument) {
        this.nameCommand = nameCommand;
        this.argument = argument;
        this.user = user;
        this.integerArgument = integerArgument;
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

    public void setUser(User user) {
        this.user = user;
    }

    public User getUser(){
        return this.user;
    }

    public Integer getIntegerArgument() {
        return integerArgument;
    }

    public void setIntegerArgument(Integer integerArgument) {
        this.integerArgument = integerArgument;
    }

    @Override
    public String toString() {
        if (user!= null) {
            return String.format("command: %s -- user: %s -- argument: %s", nameCommand, user.getLogin(), argument);
        } else {
            if (!nameCommand.equals(""))
                return String.format("command: %s -- user: -", nameCommand);
            else return "";
        }
    }
}