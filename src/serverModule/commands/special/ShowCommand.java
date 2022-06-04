package serverModule.commands.special;

import common.util.User;
import common.util.response.ResponseBody;
import serverModule.commands.*;

import serverModule.collection.CollectionManager;
import serverModule.commands.exceptions.CommandException;
import serverModule.util.ResponseOutputer;

/**
 * Команда выводит информацию об элементах коллекции
 */
public class ShowCommand extends AbstractCommand{
    public ShowCommand(CollectionManager collectionManager) {
        super("show", "вывести в стандартный поток вывода все элементы коллекции в строковом представлении", collectionManager, "");
    }

    @Override
    public ResponseBody execute(String params, Object objectArgument, User user) throws CommandException {
        collectionManager.objectsInfo();
        return new ResponseBody("вывести элементы");
    }
}
