package serverModule.commands.special;

import common.util.User;
import common.util.response.ResponseBody;
import serverModule.commands.*;

import serverModule.collection.CollectionManager;
import serverModule.commands.exceptions.CommandException;
import serverModule.data.MemoryData;
import serverModule.util.DatabaseLabWorkManager;

/**
 * Команда выводит информацию об элементах коллекции
 */
public class ShowCommand extends AbstractCommand{
    private  final DatabaseLabWorkManager databaseLabWorkManager;
    public ShowCommand(CollectionManager collectionManager, DatabaseLabWorkManager databaseLabWorkManager) {
        super("show", "вывести в стандартный поток вывода все элементы коллекции в строковом представлении", collectionManager, "");
        this.databaseLabWorkManager = databaseLabWorkManager;
    }

    @Override
    public ResponseBody execute(String params, Object objectArgument, User user) throws CommandException {
        StringBuilder longString = new StringBuilder();
        longString = MemoryData.getLabsInString();
        return new ResponseBody("Информация об элементах коллекции\n\n" + longString);
    }
}
