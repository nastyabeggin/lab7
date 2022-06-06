package serverModule.commands.special;

import common.exceptions.DatabaseManagerException;
import common.exceptions.MultiUserException;
import common.util.User;
import common.util.response.ResponseBody;
import serverModule.collection.CollectionManager;
import common.collection.LabWork;
import serverModule.commands.*;
import serverModule.util.*;

/**
 * Добавляет элемент в коллекцию
 */
/* add {element} : добавить новый элемент в коллекцию */
public class AddLabWorkCommand extends AbstractCommand {
    private final CollectionManager collectionManager;
    private final DatabaseUserManager databaseUserManager;
    private SQLConstants SQLConstants;
    private final DatabaseLabWorkManager databaseLabWorkManager;
    private final DatabaseCoordinatesManager databaseCoordinatesManager;
    private final DatabaseDisciplineManager databaseDisciplineManager;

    public AddLabWorkCommand(CollectionManager collectionManager,
                             DatabaseUserManager databaseUserManager,
                             SQLConstants SQLConstants,
                             DatabaseLabWorkManager databaseLabWorkManager,
                             DatabaseCoordinatesManager databaseCoordinatesManager,
                             DatabaseDisciplineManager databaseDisciplineManager) {
        super("add", "добавить новый элемент в коллекцию", collectionManager, "{LabWork}");
        this.collectionManager = collectionManager;
        this.databaseUserManager = databaseUserManager;
        this.SQLConstants = SQLConstants;
        this.databaseLabWorkManager = databaseLabWorkManager;
        this.databaseCoordinatesManager = databaseCoordinatesManager;
        this.databaseDisciplineManager = databaseDisciplineManager;
    }

    @Override
    public ResponseBody execute(String commandParameters, Object objectArgument, User user) {
        if (user == null) {
            return new ResponseBody("Ошибка добавления лабораторной, войдите в аккаунт! \n");
        }
        try {
            LabWork labWork = (LabWork) objectArgument;
            databaseCoordinatesManager.insertCoordinates(labWork.getCoordinates());
            databaseDisciplineManager.insertDiscipline(labWork.getDiscipline());
            if
            (databaseLabWorkManager.insertLabWork(labWork, databaseCoordinatesManager.getCoordinatesId(labWork.getCoordinates()),
                    databaseDisciplineManager.getDisciplineId(labWork.getDiscipline()), user))
                return new ResponseBody("Элемент успешно добавлен в коллекцию\n");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseBody("Что-то пошло не так!\n");
    }
}
