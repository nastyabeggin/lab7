package serverModule.commands.special;

import common.exceptions.DatabaseManagerException;
import common.exceptions.MultiUserException;
import common.util.User;
import common.util.response.AvgOfAvgPointResponseBody;
import serverModule.commands.*;
import serverModule.collection.CollectionManager;
import common.collection.LabWork;
import serverModule.util.DatabaseUserManager;

/**
 * Команда выводит среднее значение среднего балла по всем лабам
 */
public class AverageOfAveragePointCommand extends AbstractCommand {
    private final DatabaseUserManager databaseUserManager;
    public AverageOfAveragePointCommand(CollectionManager collectionManager, DatabaseUserManager databaseUserManager) {
        super("average_of_average_point", "вывести среднее значение поля averagePoint для всех элементов коллекции",
                collectionManager, "");
        this.databaseUserManager = databaseUserManager;
    }

    @Override
    public AvgOfAvgPointResponseBody execute(String commandParameters, Object objectArgument, User user) {
        try {
            if (databaseUserManager.checkUserByUsernameAndPassword(user)) {
                long sum = 0;
                for (LabWork labWork : collectionManager) {
                    sum += (labWork.getAveragePoint());
                }
                return new AvgOfAvgPointResponseBody(sum / collectionManager.size(), null);
            }
        } catch (DatabaseManagerException | MultiUserException e) {
            e.printStackTrace();
        }
        return new AvgOfAvgPointResponseBody(null, "Ошибка выполнения команды! Войдите в аккаунт");
    }
}
