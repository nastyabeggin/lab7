package serverModule.util;

import common.exceptions.DatabaseManagerException;
import common.exceptions.MultiUserException;
import common.util.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static serverModule.util.SQLConstants.*;

public class DatabaseUserManager {

    private DatabaseManager databaseManager;

    public DatabaseUserManager(DatabaseManager databaseManager) {
        this.databaseManager = databaseManager;
    }


    public User getUserById(long userID) throws SQLException {
        User user;
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = databaseManager.doPreparedStatement(SELECT_USER_BY_ID, false);
            preparedStatement.setLong(1, userID);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                user = new User(
                        resultSet.getString(SQLConstants.USER_TABLE_USERNAME_COLUMN),
                        resultSet.getString(SQLConstants.USER_TABLE_PASSWORD_COLUMN)
                );
            } else throw new SQLException();
        } catch (SQLException exception) {
            System.out.println("Произошла ошибка при выполнении запроса SELECT_USER_BY_ID!");
            throw new SQLException();
        } finally {
            databaseManager.closePreparedStatement(preparedStatement);
        }
        return user;
    }

    public boolean checkUserByUsernameAndPassword(User user) throws DatabaseManagerException, MultiUserException {
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = databaseManager.doPreparedStatement(SELECT_USER_BY_USERNAME_AND_PASSWORD, false);
            preparedStatement.setString(1, user.getLogin());
            preparedStatement.setString(2, user.getPassword());
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next();
        } catch (SQLException e) {
            System.out.println("Произошла ошибка при выполнении запроса SELECT_USER_BY_USERNAME_AND_PASSWORD!");
            throw new DatabaseManagerException();
        } finally {
            databaseManager.closePreparedStatement(preparedStatement);
        }
    }


    public int getUserIdByUsername(String login) throws DatabaseManagerException {
        int userID;
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = databaseManager.doPreparedStatement(SELECT_USER_BY_USERNAME, false);
            preparedStatement.setString(1, login);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                userID = resultSet.getInt(SQLConstants.USER_TABLE_ID_COLUMN);
            } else userID = -1;
            return userID;
        } catch (SQLException e) {
            System.out.println("Произошла ошибка при выполнении запроса SELECT_USER_BY_USERNAME!");
            throw new DatabaseManagerException();
        } finally {
            databaseManager.closePreparedStatement(preparedStatement);
        }
    }

    public boolean insertUser(User user) throws DatabaseManagerException {
        PreparedStatement preparedStatement = null;
        try {
            if (getUserIdByUsername(user.getLogin()) != -1) return false;
            String INSERT_USER = "INSERT INTO " +
                    SQLConstants.USER_TABLE + " (" +
                    SQLConstants.USER_TABLE_USERNAME_COLUMN + ", " +
                    SQLConstants.USER_TABLE_PASSWORD_COLUMN + ") VALUES (?, ?)";
            preparedStatement = databaseManager.doPreparedStatement(INSERT_USER, false);
            preparedStatement.setString(1, user.getLogin());
            preparedStatement.setString(2, user.getPassword());

            if (preparedStatement.executeUpdate() == 0) throw new SQLException();
            return true;
        } catch (SQLException exception) {
            exception.printStackTrace();
            System.out.println("Произошла ошибка при выполнении запроса INSERT_USER!");
            throw new DatabaseManagerException();
        } finally {
            databaseManager.closePreparedStatement(preparedStatement);
        }
    }

}
