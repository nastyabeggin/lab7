package serverModule.commands.special;

import common.exceptions.DatabaseManagerException;
import common.exceptions.MultiUserException;
import common.util.User;
import common.util.response.ResponseBody;
import serverModule.commands.*;
import serverModule.collection.CollectionManager;
import serverModule.commands.exceptions.CommandException;
import serverModule.commands.exceptions.ParamException;
import serverModule.util.DatabaseUserManager;

import java.util.Objects;

/**
 * Команда, удаляющая элемент коллекции по id
 */
public class RemoveByIdCommand extends AbstractCommand {
    private final DatabaseUserManager databaseUserManager;

    public RemoveByIdCommand(CollectionManager collectionManager, DatabaseUserManager databaseUserManager) {
        super("remove_by_id", "удалить элемент из коллекции по его id", collectionManager, "{id (long)}");
        this.databaseUserManager = databaseUserManager;
    }

    @Override
    public ResponseBody execute(String commandParameters, Object objectArgument, User user) throws CommandException {
        if (Objects.equals(commandParameters, "")) throw new ParamException();
        try {
            if (databaseUserManager.checkUserByUsernameAndPassword(user)) {
                long CommandManagerId = Long.parseLong(commandParameters);
                collectionManager.removeIf(labWork -> labWork.getId() == CommandManagerId);
                return new ResponseBody("Команда выполнена \n");
            }
        } catch (DatabaseManagerException | MultiUserException e) {
            e.printStackTrace();
        }
        return new ResponseBody("Команда не выполнена. \n");
    }
}

