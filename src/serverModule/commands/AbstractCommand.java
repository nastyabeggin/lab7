package serverModule.commands;

import common.util.User;
import common.util.response.ResponseBody;
import serverModule.collection.CollectionManager;

abstract public class AbstractCommand implements Command{
    protected String name;
    protected String description;
    protected final CollectionManager collectionManager;
    public static CommandHistory history = new CommandHistory();
    protected String paramsInString;

    public AbstractCommand(String name, String description, CollectionManager collectionManager, String paramsInString) {
        this.name = name;
        this.description = description;
        this.collectionManager = collectionManager;
        this.paramsInString = paramsInString;
    }

    public abstract ResponseBody execute(String argument, Object objectArgument, User user);


    @Override
    public void addToHistory() {
        history.addCommand(getName());
    }

    @Override
    public String getParams() {
        return paramsInString;
    }


    @Override
    public String getCommandHistory() {
        return history.getHistory();
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getDescription() {
        return description;
    }

}
