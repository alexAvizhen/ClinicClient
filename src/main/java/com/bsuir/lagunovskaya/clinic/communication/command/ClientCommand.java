package com.bsuir.lagunovskaya.clinic.communication.command;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ClientCommand implements Serializable {

    private static final long serialVersionUID = -2758257796920266991L;

    private String commandName;
    private List<String> commandParams;

    public ClientCommand(String commandName) {
        this.commandName = commandName;
        this.commandParams = new ArrayList<String>();
    }

    public ClientCommand(String commandName, List<String> commandParams) {
        this.commandName = commandName;
        this.commandParams = commandParams;
    }

    public String getCommandName() {
        return commandName;
    }

    public List<String> getCommandParams() {
        return commandParams;
    }

    @Override
    public String toString() {
        return "ClientCommand{" +
                "commandName='" + commandName + '\'' +
                ", commandParams=" + commandParams +
                '}';
    }
}
