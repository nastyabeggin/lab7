package serverModule.commands.special;

import common.util.User;
import common.util.response.ResponseBody;
import serverModule.commands.*;
import serverModule.collection.CollectionManager;
import serverModule.commands.exceptions.CommandException;
import serverModule.util.*;

/**
 * Выводит доступные команды и справку
 */
public class HelpCommand extends AbstractCommand {
    private final CollectionManager collectionManager;
    private final SQLConstants SQLConstants;
    private final DatabaseUserManager databaseUserManager;
    private final DatabaseLabWorkManager databaseLabWorkManager;
    private final DatabaseCoordinatesManager databaseCoordinatesManager;
    private final DatabaseDisciplineManager databaseDisciplineManager;

    public HelpCommand(CollectionManager collectionManager, SQLConstants SQLConstants, DatabaseUserManager databaseUserManager, DatabaseLabWorkManager databaseLabWorkManager, DatabaseCoordinatesManager databaseCoordinatesManager, DatabaseDisciplineManager databaseDisciplineManager) {
        super("help", "вывести справку по доступным командам", collectionManager, "");
        this.collectionManager = collectionManager;
        this.SQLConstants = SQLConstants;
        this.databaseUserManager = databaseUserManager;
        this.databaseLabWorkManager = databaseLabWorkManager;
        this.databaseCoordinatesManager = databaseCoordinatesManager;
        this.databaseDisciplineManager = databaseDisciplineManager;
    }

    @Override
    public ResponseBody execute(String params, Object objectArgument, User user) throws CommandException {
        CommandManager tempCommandManager = new CommandManager(collectionManager, databaseUserManager, SQLConstants,
                databaseLabWorkManager, databaseCoordinatesManager, databaseDisciplineManager);
        StringBuilder longResponse = new StringBuilder("");
        longResponse.append("\tСписок доступных команд\t\n");
        for (Command command : tempCommandManager.getAllCommands()) {
            longResponse.append(command.getName()).append("\t-\t").append(command.getDescription()).append("\n");
        }
        return new ResponseBody(longResponse.toString());
    }
}
