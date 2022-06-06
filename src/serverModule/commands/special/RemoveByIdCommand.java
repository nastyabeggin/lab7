package serverModule.commands.special;

import common.exceptions.DatabaseManagerException;
import common.util.User;
import common.util.response.ResponseBody;
import serverModule.collection.CollectionManager;
import serverModule.commands.AbstractCommand;
import serverModule.commands.exceptions.CommandException;
import serverModule.commands.exceptions.ParamException;
import serverModule.util.DatabaseLabWorkManager;
import serverModule.util.DatabaseUserManager;

import java.sql.SQLException;
import java.util.Objects;

/**
 * Команда, удаляющая элемент коллекции по id
 */
public class RemoveByIdCommand extends AbstractCommand {
    private final DatabaseUserManager databaseUserManager;
    private final DatabaseLabWorkManager databaseLabWorkManager;

    public RemoveByIdCommand(CollectionManager collectionManager, DatabaseUserManager databaseUserManager, DatabaseLabWorkManager databaseLabWorkManager) {
        super("remove_by_id", "удалить элемент из коллекции по его id", collectionManager, "{id (long)}");
        this.databaseUserManager = databaseUserManager;
        this.databaseLabWorkManager = databaseLabWorkManager;
    }

    @Override
    public ResponseBody execute(String commandParameters, Object objectArgument, User user) throws CommandException {
        if (Objects.equals(commandParameters, "")) throw new ParamException();
        try {
            if (databaseUserManager.getUserIdByUsername(user.getLogin()) != -1) {
                int userId = databaseUserManager.getUserIdByUsername(user.getLogin());
                int labWorkId = Integer.parseInt(commandParameters);
                databaseLabWorkManager.deleteLabWorkByIdAndUserId(labWorkId, userId);
                return new ResponseBody("Удалена лабораторная с id " + labWorkId + ", добавленная вами \n");
            }
    } catch (DatabaseManagerException  e) {
            return new ResponseBody("Лабораторной с этим id или не существует, или ее добавили не вы \n");
        }
        return new ResponseBody("Команда не выполнена. \n");
    }
}

