package serverModule.commands.special;

import common.exceptions.DatabaseManagerException;
import common.util.User;
import common.util.response.ResponseBody;
import serverModule.commands.*;
import serverModule.collection.CollectionManager;
import serverModule.util.DatabaseLabWorkManager;
import serverModule.util.DatabaseUserManager;

/**
 * Команда, очищающая коллекцию
 */
public class ClearCommand extends AbstractCommand {
    private final DatabaseUserManager databaseUserManager;
    private final DatabaseLabWorkManager databaseLabWorkManager;

    public ClearCommand(CollectionManager collectionManager, DatabaseUserManager databaseUserManager, DatabaseLabWorkManager databaseLabWorkManager) {
        super("clear", "очистить коллекцию", collectionManager, "");
        this.databaseUserManager = databaseUserManager;
        this.databaseLabWorkManager = databaseLabWorkManager;
    }

    @Override
    public ResponseBody execute(String commandParameters, Object objectArgument, User user) {
        try {
            if (user != null) {
                int userId = databaseUserManager.getUserIdByUsername(user.getLogin());
                databaseLabWorkManager.deleteAllLabWorksByUserId(userId);
                return new ResponseBody("Ваши лабораторные удалены \n");
            }
        } catch (DatabaseManagerException e) {
            return new ResponseBody("У вас нет лабораторных \n");
        }
        return new ResponseBody("Команда не выполнена, войдите в аккаунт. \n");
    }
}

