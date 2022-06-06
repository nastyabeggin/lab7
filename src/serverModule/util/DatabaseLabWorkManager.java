package serverModule.util;

import common.collection.Coordinates;
import common.collection.Discipline;
import common.collection.LabWork;
import common.exceptions.DatabaseManagerException;
import common.util.User;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class DatabaseLabWorkManager {
    private DatabaseManager databaseManager;
    private DatabaseUserManager databaseUserManager;

    public DatabaseLabWorkManager(DatabaseManager databaseManager, DatabaseUserManager databaseUserManager) {
        this.databaseManager = databaseManager;
        this.databaseUserManager = databaseUserManager;
    }

    public boolean insertLabWork(LabWork labWork, Integer coordinates_id, Integer discipline_id, User user) throws DatabaseManagerException {
        PreparedStatement preparedStatement = null;
        try {
            String INSERT_LABWORK = SQLConstants.INSERT_LABWORK;
            preparedStatement = databaseManager.doPreparedStatement(INSERT_LABWORK, false);
            preparedStatement.setString(1, labWork.getName());
            preparedStatement.setLong(2, coordinates_id);
            LocalDateTime localDateTime = LocalDateTime.now();
            preparedStatement.setTimestamp(3, Timestamp.valueOf(localDateTime));
            preparedStatement.setDouble(4, labWork.getMinimalPoint());
            preparedStatement.setLong(5, labWork.getAveragePoint());
            preparedStatement.setString(6, String.valueOf(labWork.getDifficulty()));
            preparedStatement.setLong(7, discipline_id);
            preparedStatement.setLong(8, databaseUserManager.getUserIdByUsername(user.getLogin()));
            if (preparedStatement.executeUpdate() == 0) throw new SQLException();
            return true;
        } catch (SQLException exception) {
            exception.printStackTrace();
            System.out.println("Произошла ошибка при выполнении запроса INSERT_USER!");
            throw new DatabaseManagerException();
        } catch (Exception exception) {
            exception.printStackTrace();
            System.out.println("Произошла ошибка fatal при выполнении запроса INSERT_USER!");
            throw new DatabaseManagerException();
        } finally {
            databaseManager.closePreparedStatement(preparedStatement);
        }
    }

    public void deleteLabWorkByIdAndUserId(int labWorkId, int userId) throws DatabaseManagerException {
        PreparedStatement deleteStudyGroup = null;
        PreparedStatement deleteCoordinates = null;
        PreparedStatement deletePerson = null;
        PreparedStatement deleteLocation = null;
        try {
            deleteStudyGroup = databaseManager.doPreparedStatement(SQLConstants.DELETE_LABWORK_BY_ID_AND_USER_ID, false);
            deleteStudyGroup.setLong(1, labWorkId);
            deleteStudyGroup.setLong(2, userId);
            System.out.println(labWorkId + " " + userId);
            if (deleteStudyGroup.executeUpdate() == 0) throw new SQLException();
        } catch (SQLException exception) {
            System.out.println("Произошла ошибка при выполнении запроса DELETE_GROUP_BY_ID!");
            throw new DatabaseManagerException();
        } finally {
            databaseManager.closePreparedStatement(deleteStudyGroup);
            databaseManager.closePreparedStatement(deleteCoordinates);
            databaseManager.closePreparedStatement(deletePerson);
            databaseManager.closePreparedStatement(deleteLocation);
        }
    }
}
