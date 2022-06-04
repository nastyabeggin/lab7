package serverModule.commands.special;

import clientModule.exceptions.ParamException;
import common.exceptions.DatabaseManagerException;
import common.exceptions.MultiUserException;
import common.exceptions.UserNotFoundException;
import common.util.User;
import common.util.response.AuthResponseBody;
import common.util.response.ResponseBody;
import serverModule.collection.CollectionManager;
import serverModule.commands.AbstractCommand;
import serverModule.util.DatabaseUserManager;

public class WhoAmICommand extends AbstractCommand {
    private final DatabaseUserManager databaseUserManager;

    public WhoAmICommand(CollectionManager collectionManager, DatabaseUserManager databaseUserManager) {
        super("whoami", "информация о пользователе в текущей сессии", collectionManager, null);
        this.databaseUserManager = databaseUserManager;
    }

    @Override
    public ResponseBody execute(String argument, Object objectArgument, User user) {
        try {
            return new ResponseBody("Пользователь с логином " + user.getLogin() + " с id " + databaseUserManager.getUserIdByUsername(user.getLogin()) + "\n");
        } catch (DatabaseManagerException ex) {
            ex.printStackTrace();
        }
        return new ResponseBody("Что-то пошло не так))\n");
    }
}
