package serverModule.commands.special;

import common.exceptions.DatabaseManagerException;
import common.exceptions.MultiUserException;
import common.exceptions.NonAuthorizedUserException;
import common.util.User;
import common.util.response.ResponseBody;
import common.util.response.ResponseBodyTypes;
import serverModule.collection.CollectionManager;
import common.collection.LabWork;
import serverModule.commands.*;
import serverModule.util.DatabaseUserManager;
import serverModule.util.SQLConstants;

/**
 * Добавляет элемент в коллекцию
 */
/* add {element} : добавить новый элемент в коллекцию */
public class AddLabWorkCommand extends AbstractCommand {
    private final CollectionManager collectionManager;
    private final DatabaseUserManager databaseUserManager;
    private SQLConstants SQLConstants;

    public AddLabWorkCommand(CollectionManager collectionManager, DatabaseUserManager databaseUserManager, SQLConstants SQLConstants) {
        super("add", "добавить новый элемент в коллекцию", collectionManager, "{LabWork}");
        this.collectionManager = collectionManager;
        this.databaseUserManager = databaseUserManager;
        this.SQLConstants = SQLConstants;
    }

    @Override
    public ResponseBody execute(String commandParameters, Object objectArgument, User user) {
        try {
            if (databaseUserManager.checkUserByUsernameAndPassword(user)) {
                LabWork labWork = (LabWork) objectArgument;
                labWork.generateId(collectionManager);
                //TODO collectionManager.add(SQLConstants.insertLabWork(labWork, user));
                return new ResponseBody("Элемент успешно добавлен в коллекцию\n");
            }
        } catch (DatabaseManagerException | MultiUserException e) {
            e.printStackTrace();
        }
        return new ResponseBody("Ошибка добавления лабораторной, войдите в аккаунт!");
    }
}
