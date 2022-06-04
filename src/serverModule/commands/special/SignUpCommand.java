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

public class SignUpCommand extends AbstractCommand {
    private final DatabaseUserManager databaseUserManager;

    public SignUpCommand(CollectionManager collectionManager, DatabaseUserManager databaseUserManager) {
        super("sign_up", "регистрация", collectionManager, "{username} {password}");
        this.databaseUserManager = databaseUserManager;
    }

    @Override
    public AuthResponseBody execute(String argument, Object objectArgument, User user) {
        try {
            if (!argument.isEmpty() || objectArgument != null) throw new ParamException();
            if (databaseUserManager.insertUser(user)) {
                return new AuthResponseBody(true, "Вы успешно зареганы \n");
            } else {
                return new AuthResponseBody(false, "пользователь с таким именем уже существует\n");
            }
        } catch (ParamException e) {
            return new AuthResponseBody(false, "У этой команды должен быть только один параметр: 'user'\n");
        } catch (DatabaseManagerException e) {
            e.printStackTrace();
        }
        return new AuthResponseBody(false, "Что-то пошло не так))\n");
    }
}
