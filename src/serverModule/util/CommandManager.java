package serverModule.util;

import common.util.Request;
import common.util.response.Response;
import common.util.response.ResponseBody;
import common.util.response.ResponseCode;
import common.util.User;
import serverModule.collection.CollectionManager;
import serverModule.commands.Command;
import serverModule.commands.special.*;
import serverModule.commands.exceptions.ParamException;

import java.util.Collection;
import java.util.HashMap;

/**
 * Класс пользователя, который вызывает выполнение команд
 */
public class CommandManager {

    HashMap<String, Command> commandMap = new HashMap<String, Command>();
    CollectionManager collectionManager = new CollectionManager();

    public void addCommand(Command command) {
        commandMap.put(command.getName(), command);
    }

    public boolean hasCommand(String commandName) {
        return commandMap.containsKey(commandName);
    }

    public Collection<Command> getAllCommands() {
        return commandMap.values();
    }

    public Response manage(Request request) {
        try {
            User hashUser;
            if (request.getUser() == null) {
                hashUser = null;
            } else {
                hashUser = new User(
                        request.getUser().getLogin(), DataHasher.hash(request.getUser().getPassword() + "!Hq78p@T"));
            }
            return execCommand(request.getCommandName(), request.getArgument(), request.getObjectArgument(), request.getUser());
        } catch (NullPointerException e) {
            return null;
        }
    }

    public Response execCommand(String commandName, String commandParameters, Object objectArgument, User user) {

        while (true) {
            try {
                ResponseBody responseBody = commandMap.get(commandName).execute(commandParameters, objectArgument, user);
                this.collectionManager.addToCommandHistory(commandName);
                return new Response(
                        ResponseCode.OK,
                        responseBody
                        );
            } catch (ParamException e) {
                return new Response(
                        ResponseCode.ERROR,
                        null
                );
            }
        }
    }

    public void saveCollection(){
        this.collectionManager.saveToFile();
    }

    public CommandManager(CollectionManager collectionManager, DatabaseUserManager databaseUserManager, SQLConstants SQLConstants) {
        this.collectionManager = collectionManager;
        addCommand(new AddLabWorkCommand(collectionManager, databaseUserManager, SQLConstants));
        addCommand(new SignInCommand(collectionManager, databaseUserManager));
        addCommand(new SignUpCommand(collectionManager, databaseUserManager));
        addCommand(new HelpCommand(collectionManager, SQLConstants, databaseUserManager));
        addCommand(new WhoAmICommand(collectionManager, databaseUserManager));


//        addCommand(new ShowCommand(collectionManager, SQLConstants));
//        addCommand(new HelpCommand(collectionManager, SQLConstants));
//        addCommand(new AddLabWorkIfMinCommand(collectionManager, SQLConstants));
//        addCommand(new AverageOfAveragePointCommand(collectionManager, SQLConstants));
//        addCommand(new ClearCommand(collectionManager, SQLConstants));
//        addCommand(new CountLessThanAveragePointCommand(collectionManager, SQLConstants));
//        addCommand(new HistoryCommand(collectionManager, SQLConstants));
//        addCommand(new InfoCommand(collectionManager, SQLConstants));
//        addCommand(new RemoveLowerCommand(collectionManager, SQLConstants));
//        addCommand(new RemoveByIdCommand(collectionManager, SQLConstants));
//        addCommand(new RemoveAllByMinimalPointCommand(collectionManager, SQLConstants));
//        addCommand(new UpdateIdCommand(collectionManager, SQLConstants));
//        addCommand(new ExecuteScriptCommand(collectionManager, SQLConstants));
    }

}