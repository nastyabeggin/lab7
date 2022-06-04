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
 * Команда, удаляющая из коллекции все элементы, значение поля minimalPoint которого = данному
 */
public class RemoveAllByMinimalPointCommand extends AbstractCommand {
    private final DatabaseUserManager databaseUserManager;

    public RemoveAllByMinimalPointCommand(CollectionManager collectionManager, DatabaseUserManager databaseUserManager) {
        super("remove_all_by_minimal_point", "удалить из коллекции все элементы, значение поля minimalPoint которого эквивалентно заданному", collectionManager, "{minimalPoint (float)}");
        this.databaseUserManager = databaseUserManager;
    }

    @Override
    public ResponseBody execute(String commandParameters, Object objectArgument, User user) throws CommandException {
        if (Objects.equals(commandParameters, "")) throw new ParamException();
        try {
            if (databaseUserManager.checkUserByUsernameAndPassword(user)) {
                float CommandManagerMinimalPoint = Float.parseFloat(commandParameters);
                collectionManager.removeIf(labWork -> labWork.getMinimalPoint() == CommandManagerMinimalPoint);
                return new ResponseBody("Команда выполнена \n");

            }
        } catch (DatabaseManagerException | MultiUserException e) {
            e.printStackTrace();
        }
        return new ResponseBody("Команда не выполнена. Проблема с данными \n");
    }
}

