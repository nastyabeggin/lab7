package serverModule.commands.special;

import common.exceptions.DatabaseManagerException;
import common.exceptions.MultiUserException;
import common.util.User;
import common.util.response.ResponseBody;
import common.util.response.ResponseBodyTypes;
import serverModule.commands.*;
import serverModule.collection.CollectionManager;
import common.collection.LabWork;
import serverModule.commands.exceptions.ParamException;
import serverModule.util.DatabaseUserManager;

import java.util.Objects;

/**
 * Добавляет лабу в коллекцию, если его минимальный балл меньше минимального
 */
public class AddLabWorkIfMinCommand extends AbstractCommand {
    private final DatabaseUserManager databaseUserManager;

    public AddLabWorkIfMinCommand(CollectionManager collectionManager, DatabaseUserManager databaseUserManager) {
        super("add_if_min", "добавить новый элемент в коллекцию, если его мин. балл меньше," +
                "   чем у наименьшего элемента этой коллекции", collectionManager, "{minimalPoint (float)}");
        this.databaseUserManager = databaseUserManager;
    }

    @Override
    public ResponseBody execute(String commandParameters, Object objectArgument, User user) throws ParamException {
        try {
            if (databaseUserManager.checkUserByUsernameAndPassword(user)) {
                float minimalMinimalPoint = collectionManager.getMinimalMinimalPoint();
                if (Objects.equals(commandParameters, "")) throw new ParamException();
                try {
                    float CommandManagerMinimalPoint = Float.parseFloat(commandParameters);
                    if (minimalMinimalPoint > CommandManagerMinimalPoint) {
                        LabWork labWork = (LabWork) objectArgument;
                        labWork.generateId(collectionManager);
                        collectionManager.add(labWork);
                        return new ResponseBody("Ваша лаба добавлена в коллекцию\n");
                    } else {
                        return new ResponseBody("Ваше значение больше минимального\n");
                    }
                } catch (Exception e) {
                    throw new ParamException();
                }
            }
        } catch (DatabaseManagerException | MultiUserException e) {
            e.printStackTrace();
        }
        return new ResponseBody("Ошибка! Ваша лабораторная не добавлена.\n");
    }

}
