package serverModule;

import serverModule.collection.CollectionManager;
import serverModule.util.*;

import java.io.IOException;

public class ServerMain {
    public static final int PORT = 8000;

    public static void main(String[] args) throws IOException {
        DatabaseManager databaseManager = new DatabaseManager();
        DatabaseUserManager databaseUserManager = new DatabaseUserManager(databaseManager);
        DatabaseDisciplineManager databaseDisciplineManager = new DatabaseDisciplineManager(databaseManager);
        DatabaseLabWorkManager databaseLabWorkManager = new DatabaseLabWorkManager(databaseManager, databaseUserManager);
        DatabaseCoordinatesManager databaseCoordinatesManager = new DatabaseCoordinatesManager(databaseManager);
        SQLConstants SQLConstants = new SQLConstants();

        CollectionManager collectionManager = new CollectionManager();
        CommandManager CommandManager = new CommandManager(collectionManager, databaseUserManager, SQLConstants,
                databaseLabWorkManager, databaseCoordinatesManager, databaseDisciplineManager);
        Server server = new Server(PORT, CommandManager);
        server.run();
    }
}
