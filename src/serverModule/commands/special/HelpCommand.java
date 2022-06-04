package serverModule.commands.special;

import common.exceptions.DatabaseManagerException;
import common.exceptions.MultiUserException;
import common.util.User;
import common.util.response.ResponseBody;
import serverModule.commands.*;
import serverModule.collection.CollectionManager;
import serverModule.commands.exceptions.CommandException;
import serverModule.util.SQLConstants;
import serverModule.util.DatabaseUserManager;
import serverModule.util.ResponseOutputer;
import serverModule.util.CommandManager;

/**
 * Выводит доступные команды и справку
 */
public class HelpCommand extends AbstractCommand {
    private final CollectionManager collectionManager;
    private final SQLConstants SQLConstants;
    private final DatabaseUserManager databaseUserManager;

    public HelpCommand(CollectionManager collectionManager, SQLConstants SQLConstants, DatabaseUserManager databaseUserManager) {
        super("help", "вывести справку по доступным командам", collectionManager, "");
        this.collectionManager = collectionManager;
        this.SQLConstants = SQLConstants;
        this.databaseUserManager = databaseUserManager;
    }

    @Override
    public ResponseBody execute(String params, Object objectArgument, User user) throws CommandException {
        CommandManager tempCommandManager = new CommandManager(collectionManager, databaseUserManager, SQLConstants);
        StringBuilder longResponse = new StringBuilder("");
        longResponse.append("\tСписок доступных команд\t\n");
        for (Command command : tempCommandManager.getAllCommands()) {
            longResponse.append(command.getName()).append("\t-\t").append(command.getDescription()).append("\n");
        }
        return new ResponseBody(longResponse.toString());
    }
}
