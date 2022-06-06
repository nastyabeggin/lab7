package serverModule.util;

import common.collection.Coordinates;
import common.exceptions.DatabaseManagerException;
import common.util.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static serverModule.util.SQLConstants.SELECT_COORDINATES_BY_X_AND_Y;
import static serverModule.util.SQLConstants.SELECT_USER_BY_USERNAME;

public class DatabaseCoordinatesManager {
    private DatabaseManager databaseManager;

    public DatabaseCoordinatesManager(DatabaseManager databaseManager) {
        this.databaseManager = databaseManager;
    }

    public boolean insertCoordinates(Coordinates coordinates) throws DatabaseManagerException {
        PreparedStatement preparedStatement = null;
        try {
            String INSERT_COORDINATES = SQLConstants.INSERT_COORDINATES;
            preparedStatement = databaseManager.doPreparedStatement(INSERT_COORDINATES, false);
            preparedStatement.setLong(1, coordinates.getX());
            preparedStatement.setInt(2, coordinates.getY());
            if (preparedStatement.executeUpdate() == 0) throw new SQLException();
            return true;
        } catch (SQLException exception) {
            exception.printStackTrace();
            System.out.println("Произошла ошибка при выполнении запроса INSERT_COORDINATES!");
            throw new DatabaseManagerException();
        } finally {
            databaseManager.closePreparedStatement(preparedStatement);
        }
    }

    public Integer getLastId() throws DatabaseManagerException{
        PreparedStatement preparedStatement = null;
        Integer lastId = null;
        try {
            String SELECT_LAST_COORDINATES_ID = SQLConstants.SELECT_LAST_COORDINATES_ID;
            preparedStatement = databaseManager.doPreparedStatement(SELECT_LAST_COORDINATES_ID, false);
            if (preparedStatement.executeUpdate() == 0) throw new SQLException();
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                lastId = resultSet.getInt(SQLConstants.COORDINATES_TABLE_ID_COLUMN);
            } else lastId = -1;
            return lastId;
        } catch (SQLException exception) {
            exception.printStackTrace();
            System.out.println("Произошла ошибка при выполнении запроса SELECT_LAST_COORDINATES_ID!");
            throw new DatabaseManagerException();
        } finally {
            databaseManager.closePreparedStatement(preparedStatement);
        }
    }

    public int getCoordinatesId(Coordinates coordinates) throws DatabaseManagerException {
        int coordsId;
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = databaseManager.doPreparedStatement(SELECT_COORDINATES_BY_X_AND_Y, false);
            preparedStatement.setLong(1, coordinates.getX());
            preparedStatement.setLong(2, coordinates.getY());
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                coordsId = resultSet.getInt(SQLConstants.COORDINATES_TABLE_ID_COLUMN);
            } else coordsId = -1;
            return coordsId;
        } catch (SQLException e) {
            System.out.println("Произошла ошибка при выполнении запроса SELECT_COORDINATES_BY_X_AND_Y!");
            throw new DatabaseManagerException();
        } finally {
            databaseManager.closePreparedStatement(preparedStatement);
        }
    }
}
