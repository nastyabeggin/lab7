package serverModule.commands.special;

import common.collection.LabWork;
import common.util.User;
import common.util.response.ResponseBody;
import serverModule.commands.*;
import serverModule.collection.CollectionManager;
import serverModule.commands.exceptions.CommandException;
import serverModule.commands.exceptions.ParamException;
import serverModule.util.ResponseOutputer;

/**
 * Команда, которая обновляет экземпляр коллекции
 */

public class UpdateIdCommand extends AbstractCommand {
    public UpdateIdCommand(CollectionManager collectionManager) {
        super("update", "обновить значение элемента коллекции, id которого равен заданному", collectionManager, "{Id(long)}");
    }

    @Override
    public ResponseBody execute(String params, Object objectArgument, User user) throws CommandException {
            long CommandManagerId = Long.parseLong(params);
            collectionManager.update(CommandManagerId, (LabWork) objectArgument);
            return new ResponseBody("Команда выполнена \n");
         //   return new ResponseBody("Команда не выполнена. Возникла ошибка \n");
    }
}

