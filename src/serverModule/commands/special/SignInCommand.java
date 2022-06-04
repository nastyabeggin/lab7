package serverModule.commands.special;

import clientModule.exceptions.ParamException;
import common.exceptions.DatabaseManagerException;
import common.exceptions.MultiUserException;
import common.exceptions.UserNotFoundException;
import common.util.User;
import common.util.response.AuthResponseBody;
import serverModule.collection.CollectionManager;
import serverModule.commands.AbstractCommand;
import serverModule.util.DatabaseUserManager;

public class SignInCommand extends AbstractCommand {
    private final DatabaseUserManager databaseUserManager;

    public SignInCommand(CollectionManager collectionManager, DatabaseUserManager databaseUserManager) {
        super("sign_in", "войти в аккаунт", collectionManager, "{username} {password}");
        this.databaseUserManager = databaseUserManager;
    }

    @Override
    public AuthResponseBody execute(String argument, Object objectArgument, User user) {
        try {
            if (!argument.isEmpty() || objectArgument != null) throw new ParamException();
            if (databaseUserManager.checkUserByUsernameAndPassword(user)) {
                return new AuthResponseBody(true, "Авторизация прошла успешно!\n");
            } else throw new UserNotFoundException();
        } catch (ParamException e) {
           return new AuthResponseBody(false, "У этой команды должен быть только один параметр: 'user'\n");
        } catch (DatabaseManagerException e) {
            return new AuthResponseBody(false, "Произошла ошибка при обращении к базе данных!\n");
        } catch (UserNotFoundException e) {
            return new AuthResponseBody(false, "Неправильные имя пользователя или пароль!\n");
        } catch (ClassCastException e) {
            return new AuthResponseBody(false, "Переданный клиентом объект неверен!\n");
        } catch (MultiUserException e) {
            return new AuthResponseBody(false, "Этот пользователь уже авторизован!\n");
        }
    }
}