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
 * Команда удаляет элементы Average Point которых меньше заданного
 */
public class RemoveLowerCommand extends AbstractCommand {
    private final DatabaseUserManager databaseUserManager;

    public RemoveLowerCommand(CollectionManager collectionManager, DatabaseUserManager databaseUserManager) {
        super("remove_lower", "удалить из коллекции все элементы, меньшие, чем заданный", collectionManager, "{averagePont(long)}");
        this.databaseUserManager = databaseUserManager;
    }

    @Override
    public ResponseBody execute(String commandParameters, Object objectArgument, User user) throws CommandException {

        if (Objects.equals(commandParameters, "")) throw new ParamException();
        try {
            if (databaseUserManager.checkUserByUsernameAndPassword(user)) {
                long CommandManagerAveragePoint = Long.parseLong(commandParameters);
                collectionManager.removeIf(labWork -> labWork.getAveragePoint() == CommandManagerAveragePoint);
                return new ResponseBody("Команда выполнена \n");
            }
        } catch (DatabaseManagerException | MultiUserException e) {
            e.printStackTrace();
        }
        return new ResponseBody("Команда не выполнена. Проблема с аргументами \n");
    }
}

