package Server;

import Exceptions.DatabaseException;
import Exceptions.MultiUserException;
import data.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserManager {
    private final String SELECT_USER_BY_ID = "SELECT * FROM " + DatabaseManager.USER_TABLE +
            " WHERE " + DatabaseManager.USER_TABLE_ID_COLUMN + " = ?";
    private final String SELECT_USER_BY_LOGIN = "SELECT * FROM " + DatabaseManager.USER_TABLE +
            " WHERE " + DatabaseManager.USER_TABLE_LOGIN_COLUMN + " = ?";
    private final String SELECT_USER_BY_LOGIN_AND_PASSWORD = SELECT_USER_BY_LOGIN + " AND " +
            DatabaseManager.USER_TABLE_PASSWORD_COLUMN + " = ?";
    private final String UPDATE_ONLINE_BY_LOGIN_AND_PASSWORD = "UPDATE " + DatabaseManager.USER_TABLE + " SET " +
            DatabaseManager.USER_TABLE_ONLINE_COLUMN + " = ?" + " WHERE " +
            DatabaseManager.USER_TABLE_LOGIN_COLUMN + " = ?" + " AND " +
            DatabaseManager.USER_TABLE_PASSWORD_COLUMN + " = ?";
    private final String SWITCH_OFF_ALL_USERS = "UPDATE " + DatabaseManager.USER_TABLE + " SET " +
            DatabaseManager.USER_TABLE_ONLINE_COLUMN + " = ?";

    private DatabaseManager databaseManager;

    public UserManager(DatabaseManager databaseManager) {
        this.databaseManager = databaseManager;
        switchOffAllUsers();
    }

    public void switchOffAllUsers() {
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = databaseManager.doPreparedStatement(SWITCH_OFF_ALL_USERS, false);
            preparedStatement.setBoolean(1, false);
            if (preparedStatement.executeUpdate() == 0) throw new SQLException();
        } catch (SQLException e) {
            System.out.println("An error occurred while executing the SWITCH_OFF_ALL_USERS request!");
        }
    }

    public User getUserById(int userID) throws SQLException {
        User user;
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = databaseManager.doPreparedStatement(SELECT_USER_BY_ID, false);
            preparedStatement.setInt(1, userID);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                user = new User(
                        resultSet.getString(DatabaseManager.USER_TABLE_LOGIN_COLUMN),
                        resultSet.getString(DatabaseManager.USER_TABLE_PASSWORD_COLUMN)
                );
            } else throw new SQLException();
        } catch (SQLException exception) {
            System.out.println("An error occurred while executing the SELECT USER BY ID query!");
            throw new SQLException();
        } finally {
            databaseManager.closePreparedStatement(preparedStatement);
        }
        return user;
    }

    public boolean checkUserByLoginAndPassword(User user) throws DatabaseException, MultiUserException {
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = databaseManager.doPreparedStatement(SELECT_USER_BY_LOGIN_AND_PASSWORD, false);
            preparedStatement.setString(1, user.getLogin());
            preparedStatement.setString(2, user.getPassword());
            ResultSet resultSet = preparedStatement.executeQuery();
            System.out.println(user.getLogin() + user.getPassword());
            if (!resultSet.next()) return false;
            if (resultSet.getBoolean(DatabaseManager.USER_TABLE_ONLINE_COLUMN)) {throw new MultiUserException();}
            return true;
        } catch (SQLException e) {
            System.out.println("An error occurred while executing the SELECT_USER_BY_USERNAME_AND_PASSWORD query!");
            throw new DatabaseException();
        } finally {
            databaseManager.closePreparedStatement(preparedStatement);
        }
    }

    public void updateOnline(User user, boolean newValue) throws DatabaseException {
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = databaseManager.doPreparedStatement(UPDATE_ONLINE_BY_LOGIN_AND_PASSWORD, false);
            preparedStatement.setBoolean(1, newValue);
            preparedStatement.setString(2, user.getLogin());
            preparedStatement.setString(3, user.getPassword());
            if (preparedStatement.executeUpdate() == 0) throw new SQLException();
        } catch (SQLException exception) {
            System.out.println("An error occurred while executing the UPDATE_ONLINE_BY_USERNAME_AND_PASSWORD query!");
            throw new DatabaseException();
        } finally {
            databaseManager.closePreparedStatement(preparedStatement);
        }
    }

    public int getUserIdByUsername(User user) {
        int userID;
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = databaseManager.doPreparedStatement(SELECT_USER_BY_LOGIN, false);
            preparedStatement.setString(1, user.getLogin());
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                userID = resultSet.getInt(DatabaseManager.USER_TABLE_ID_COLUMN);
            } else userID = -1;
            return userID;
        } catch (SQLException e) {
            System.out.println("An error occurred while executing the SELECT_USER_BY_USERNAME query!");
            e.printStackTrace();
            return -1;
        } finally {
            databaseManager.closePreparedStatement(preparedStatement);
        }
    }

    public boolean insertUser(User user) throws DatabaseException{
        PreparedStatement preparedStatement = null;
        try {
            if (getUserIdByUsername(user) != -1) {
                return false;
            }
            String INSERT_USER = "INSERT INTO " +
                    DatabaseManager.USER_TABLE + " (" +
                    DatabaseManager.USER_TABLE_LOGIN_COLUMN + ", " +
                    DatabaseManager.USER_TABLE_PASSWORD_COLUMN + ", " +
                    DatabaseManager.USER_TABLE_ONLINE_COLUMN + ") VALUES (?, ?, ?)";
            preparedStatement = databaseManager.doPreparedStatement(INSERT_USER, false);
            preparedStatement.setString(1, user.getLogin());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setBoolean(3, true);

            if (preparedStatement.executeUpdate() == 0) throw new SQLException();
            return true;
        } catch (SQLException exception) {
            exception.printStackTrace();
            System.out.println("An error occurred while executing the INSERT_USER query!");
            throw new DatabaseException();
        } finally {
            databaseManager.closePreparedStatement(preparedStatement);
        }
    }
}