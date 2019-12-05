package com.bsuir.lagunovskaya.clinic.communication;

import java.util.ArrayList;
import java.util.List;

public class CommonServerResponse extends ServerResponse {

    private static final long serialVersionUID = -5788592416728953982L;

    private String commandName;
    private List<String> responseParams;

    public CommonServerResponse(String commandName, List<String> commandParams) {
        this.commandName = commandName;
        this.responseParams = commandParams;
    }

    public CommonServerResponse(String commandName) {
        this.commandName = commandName;
        this.responseParams = new ArrayList<>();
    }

    public String getCommandName() {
        return commandName;
    }

    public List<String> getResponseParams() {
        return responseParams;
    }

    @Override
    public String toString() {
        return "CommonServerResponse{" +
                "commandName='" + commandName + '\'' +
                ", responseParams=" + responseParams +
                '}';
    }
}
