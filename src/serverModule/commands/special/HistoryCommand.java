package serverModule.commands.special;

import common.util.User;
import common.util.response.ResponseBody;
import serverModule.commands.*;
import serverModule.collection.CollectionManager;
import serverModule.commands.exceptions.CommandException;
import serverModule.util.ResponseOutputer;

/**
 * Команда, которая выводит последние 5 команд
 */
public class HistoryCommand extends AbstractCommand{
    public HistoryCommand(CollectionManager collectionManager) {
        super("history", "вывести последние 5 команд (без их аргументов)", collectionManager, "");
    }

    @Override
    public ResponseBody execute(String params, Object objectArgument, User user) throws CommandException {
        return new ResponseBody(collectionManager.getCommandHistory());
    }
}
