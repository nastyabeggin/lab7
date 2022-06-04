package serverModule.commands.special;

import common.exceptions.DatabaseManagerException;
import common.exceptions.MultiUserException;
import common.util.User;
import common.util.response.ResponseBody;
import common.util.response.ResponseBodyTypes;
import serverModule.commands.*;
import serverModule.collection.CollectionManager;
import serverModule.util.DatabaseUserManager;
import serverModule.util.ResponseOutputer;

/**
 * Команда, очищающая коллекцию
 */
public class ClearCommand extends AbstractCommand {
    private final DatabaseUserManager databaseUserManager;
    public ClearCommand(CollectionManager collectionManager, DatabaseUserManager databaseUserManager) {
        super("clear", "очистить коллекцию", collectionManager, "");
        this.databaseUserManager = databaseUserManager;
    }

    @Override
    public ResponseBody execute(String commandParameters, Object objectArgument, User user) {
        try {
            if (databaseUserManager.checkUserByUsernameAndPassword(user)) {
                collectionManager.clear();
                return new ResponseBody("Коллекция очищена \n");
            }
        } catch (DatabaseManagerException | MultiUserException e) {
            e.printStackTrace();
        }
        return new ResponseBody("Команда не выполнена! Войдите в аккаунт \n");
    }
}
