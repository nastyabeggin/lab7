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

public class LogOutCommand extends AbstractCommand {
    private final DatabaseUserManager databaseUserManager;

    public LogOutCommand(CollectionManager collectionManager, DatabaseUserManager databaseUserManager) {
        super("log_out", "выйти из аккаунта", collectionManager,  null);
        this.databaseUserManager = databaseUserManager;
    }

    @Override
    public ResponseBody execute(String argument, Object objectArgument, User user) {
        if (user == null){
            return new ResponseBody("Вы успешно вышли из аккаунта!\n");
        }
        return new ResponseBody("Что-то пошло не так! \n");
    }
}
