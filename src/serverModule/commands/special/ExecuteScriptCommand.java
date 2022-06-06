
package serverModule.commands.special;

import common.exceptions.DatabaseManagerException;
import common.exceptions.MultiUserException;
import common.util.User;
import common.util.response.ResponseBody;
import serverModule.commands.*;
import serverModule.collection.CollectionManager;
import serverModule.commands.exceptions.ParamException;
import common.util.Request;
import serverModule.util.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;


/*execute_script file_name : считать и исполнить скрипт из
  указанного файла. В скрипте содержатся команды в таком же виде,
  в котором их вводит пользователь в интерактивном режиме.
 */

public class ExecuteScriptCommand extends AbstractCommand {
    private final DatabaseUserManager databaseUserManager;
    private final SQLConstants SQLConstants;
    private final DatabaseCoordinatesManager databaseCoordinatesManager;
    private final DatabaseDisciplineManager databaseDisciplineManager;
    private final DatabaseLabWorkManager databaseLabWorkManager;

    public ExecuteScriptCommand(CollectionManager collectionManager,
                                SQLConstants SQLConstants,
                                DatabaseUserManager databaseUserManager,
                                DatabaseCoordinatesManager databaseCoordinatesManager,
                                DatabaseDisciplineManager databaseDisciplineManager,
                                DatabaseLabWorkManager databaseLabWorkManager) {
        super("execute_script", "считать и исполнить скрипт из указанного файла", collectionManager, "{file_name (String)}");
        this.SQLConstants = SQLConstants;
        this.databaseUserManager = databaseUserManager;
        this.databaseCoordinatesManager = databaseCoordinatesManager;
        this.databaseDisciplineManager = databaseDisciplineManager;
        this.databaseLabWorkManager = databaseLabWorkManager;
    }

    @Override
    public ResponseBody execute(String params, Object objectArgument, User user) throws ParamException {
        try {
            if (databaseUserManager.checkUserByUsernameAndPassword(user)) {
                if (params.equals(""))
                throw new ParamException();
            else {
                File file = new File(params);
                if (!file.exists()) {
                    return new ResponseBody("Файла не существует \n");
                } else if (file.exists() && !file.canRead()) {
                    return new ResponseBody("Файл существует, нет прав на чтение.");
                }
                //else if (file.exists() && !file.canExecute()) {System.out.println("Проверьте файл на выполнение"); throw new ParamException();}
                else {
                    Scanner scanner = null;
                    try {
                        scanner = new Scanner(file);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    while (true) {
                        assert scanner != null;
                        if (!scanner.hasNextLine()) break;
                        String line = scanner.nextLine().trim();
                        List<String> collection = Arrays.asList(line.split(" "));
                        CommandManager CommandManager = new CommandManager(collectionManager, databaseUserManager,
                                SQLConstants, databaseLabWorkManager,
                                databaseCoordinatesManager, databaseDisciplineManager);
                        if (collection.get(0).equals("execute_script")) {
                            return new ResponseBody("В файле команда execute_script не выполняется");
                        } else {
                            try {
                                CommandManager.manage(new Request(collection.get(0), collection.get(1)));
                            } catch (IndexOutOfBoundsException e) {
                                Request request = new Request(collection.get(0), "");
                                CommandManager.manage(request);
                            }
                        }

                    }
                }
            }}
        } catch (DatabaseManagerException | MultiUserException e) {
            e.printStackTrace();
        }
        return new ResponseBody("Команда не выполнена");
    }
}

