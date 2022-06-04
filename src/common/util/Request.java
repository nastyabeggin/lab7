package common.util;

import common.collection.LabWork;

import java.io.Serializable;

public class Request implements Serializable {
    private String commandName;
    private String argument;
    private Serializable objectArgument;
    private User user;


    public Request(String commandName, String argument, Serializable objectArgument, User user) {
        this.commandName = commandName;
        this.argument = argument;
        this.objectArgument = objectArgument;
        this.user = user;
    }

    public Request(String commandName, String argument, User user) {
        this.commandName = commandName;
        this.argument = argument;
        this.objectArgument = null;
        this.user = user;
    }

    public Request(String commandName, String argument) {
        this.commandName = commandName;
        this.argument = argument;
        this.objectArgument = null;
        this.user = null;
    }

    public Request(User user) {
        this.commandName = "";
        this.argument = "";
        this.objectArgument = null;
        this.user = user;
    }

    public String getCommandName() {
        return commandName;
    }

    public String getArgument() {
        return argument;
    }

    public Object getObjectArgument() {
        return objectArgument;
    }

    public User getUser() {
        return user;
    }

    public boolean isEmpty() {
        return commandName.isEmpty() && argument.isEmpty() && objectArgument == null;
    }

}
