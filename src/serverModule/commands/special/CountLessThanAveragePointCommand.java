package serverModule.commands.special;

import common.exceptions.DatabaseManagerException;
import common.exceptions.MultiUserException;
import common.util.User;
import common.util.response.CountLessThanAvgResponseBody;
import serverModule.commands.*;
import serverModule.collection.CollectionManager;
import common.collection.LabWork;
import serverModule.commands.exceptions.ParamException;
import serverModule.util.DatabaseUserManager;

import java.util.Objects;

/**
 * Команда, которая выводит количество элементов, значение среднего балла которых меньше введенного
 */
public class CountLessThanAveragePointCommand extends AbstractCommand {
    private final DatabaseUserManager databaseUserManager;

    public CountLessThanAveragePointCommand(CollectionManager collectionManager, DatabaseUserManager databaseUserManager) {
        super("count_less_than_average_point", "вывести количество элементов, значение поля averagePoint которых меньше заданного",
                collectionManager, "{averagePoint (long)}");
        this.databaseUserManager = databaseUserManager;
    }

    @Override
    public CountLessThanAvgResponseBody execute(String commandParameters, Object objectArgument, User user) throws ParamException {

        if (Objects.equals(commandParameters, "")) throw new ParamException();
        try {
            if (databaseUserManager.checkUserByUsernameAndPassword(user)) {
                try {
                    int n = 0;
                    long CommandManagerAveragePoint = Long.parseLong(commandParameters);
                    for (LabWork labWork : collectionManager) {
                        if (labWork.getAveragePoint() < CommandManagerAveragePoint) n++;
                    }
                    return new CountLessThanAvgResponseBody(n, null);
                } catch (Exception e) {
                    throw new ParamException();
                }
            }
        } catch (DatabaseManagerException | MultiUserException e) {
            e.printStackTrace();
        }
        return new CountLessThanAvgResponseBody(null, "Команда не выполнена! Войдите в аккаунт");

    }
}
