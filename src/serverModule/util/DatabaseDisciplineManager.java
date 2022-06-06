package serverModule.util;

import common.collection.Coordinates;
import common.collection.Discipline;
import common.exceptions.DatabaseManagerException;
import common.util.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static serverModule.util.SQLConstants.SELECT_COORDINATES_BY_X_AND_Y;
import static serverModule.util.SQLConstants.SELECT_DISCIPLINE_BY_NAME;

public class DatabaseDisciplineManager {
    private DatabaseManager databaseManager;

    public DatabaseDisciplineManager(DatabaseManager databaseManager) {
        this.databaseManager = databaseManager;
    }

    public boolean insertDiscipline(Discipline discipline) throws DatabaseManagerException {
        PreparedStatement preparedStatement = null;
        try {
            String INSERT_DISCIPLINE = SQLConstants.INSERT_DISCIPLINE;
            preparedStatement = databaseManager.doPreparedStatement(INSERT_DISCIPLINE, false);
            preparedStatement.setString(1, discipline.getName());
            preparedStatement.setLong(2, discipline.getLectureHours());
            preparedStatement.setInt(3, discipline.getPracticeHours());
            if (discipline.getLabsCount() != null) {
                preparedStatement.setLong(4, discipline.getLabsCount());
            } else {
                preparedStatement.setNull(4, java.sql.Types.NULL);
            }
            if (preparedStatement.executeUpdate() == 0) throw new SQLException();
            return true;
        } catch (SQLException exception) {
            exception.printStackTrace();
            System.out.println("Произошла ошибка при выполнении запроса INSERT_DISCIPLINE!");
            throw new DatabaseManagerException();
        } finally {
            databaseManager.closePreparedStatement(preparedStatement);
        }
    }

    public Integer getLastId() throws DatabaseManagerException {
        PreparedStatement preparedStatement = null;
        Integer lastId = null;
        try {
            String SELECT_LAST_DISCIPLINE_ID = SQLConstants.SELECT_LAST_DISCIPLINE_ID;
            preparedStatement = databaseManager.doPreparedStatement(SELECT_LAST_DISCIPLINE_ID, false);
            if (preparedStatement.executeUpdate() == 0) throw new SQLException();
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                lastId = resultSet.getInt(SQLConstants.DISCIPLINE_ID_COLUMN);
            } else lastId = -1;
            return lastId;
        } catch (SQLException exception) {
            exception.printStackTrace();
            System.out.println("Произошла ошибка при выполнении запроса SELECT_LAST_DISCIPLINE_ID!");
            throw new DatabaseManagerException();
        } finally {
            databaseManager.closePreparedStatement(preparedStatement);
        }
    }

    public int getDisciplineId(Discipline discipline) throws DatabaseManagerException {
        int disciplineId;
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = databaseManager.doPreparedStatement(SELECT_DISCIPLINE_BY_NAME, false);
            preparedStatement.setString(1, discipline.getName());
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                disciplineId = resultSet.getInt(SQLConstants.DISCIPLINE_ID_COLUMN);
            } else disciplineId = -1;
            return disciplineId;
        } catch (SQLException e) {
            System.out.println("Произошла ошибка при выполнении запроса SELECT_DISCIPLINE_BY_NAME!");
            throw new DatabaseManagerException();
        } finally {
            databaseManager.closePreparedStatement(preparedStatement);
        }
    }
}
