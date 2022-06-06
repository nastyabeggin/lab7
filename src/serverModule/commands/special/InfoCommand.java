package serverModule.commands.special;

import common.util.User;
import common.util.response.ResponseBody;
import serverModule.commands.*;
import serverModule.collection.CollectionManager;
import serverModule.commands.exceptions.CommandException;
import serverModule.data.MemoryData;
import serverModule.util.ResponseOutputer;

/**
 * Выводит информацию о коллекции
 */
public class InfoCommand extends AbstractCommand{
    public InfoCommand(CollectionManager collectionManager) {
        super("info", "вывести в стандартный поток вывода информацию о коллекции", collectionManager, "");
    }

    @Override
    public ResponseBody execute(String params, Object objectArgument, User user) throws CommandException {
        return new ResponseBody(MemoryData.getInfo() + "\n");
    }
}
