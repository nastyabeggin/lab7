package serverModule.commands.special;

import common.util.User;
import common.util.response.ResponseBody;
import serverModule.commands.*;
import serverModule.collection.CollectionManager;

/**
 * Команда, завершающая программу (без сохранения)
 */
public class ExitCommand extends AbstractCommand {
    public ExitCommand(CollectionManager collectionManager) {
        super("exit", "завершить программу (без сохранения в файл)", collectionManager, "");
    }

    @Override
    public ResponseBody execute(String commandParameters, Object objectArgument, User user) {
        System.exit(0);
        return new ResponseBody("Ок. Работа системы завершена");
    }
}
