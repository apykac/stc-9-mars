package ru.innopolis.stc9.dao;

import org.apache.log4j.Logger;
import ru.innopolis.stc9.db.connection.ConnectionManager;
import ru.innopolis.stc9.db.connection.ConnectionManagerImpl;
import ru.innopolis.stc9.pojo.Login;
import ru.innopolis.stc9.pojo.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Семушев on 24.05.2018.
 */
public class UserDaoImpl implements UserDao {
    private static ConnectionManager connectionManager = ConnectionManagerImpl.getInstance();
    private Logger logger = Logger.getLogger(UserDaoImpl.class);

    @Override
    public boolean addUser(User user) {
        if (user == null) return false;
        logger.info("Start add user");
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "INSERT INTO users (login, hash_password, first_name, second_name, middle_name) VALUES (?, ?, ?, ?, ?)")) {
            UserMapper.statementSetter(statement, user, 1, 5);
            statement.execute();
            logger.info("Adding user successfully");
        } catch (SQLException e) {
            logger.error(e.getMessage());
            return false;
        }
        return true;
    }

    @Override
    public Integer maxId() {
        Integer result = null;
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT MAX(id) maxid FROM users")) {
            statement.execute();
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) result = resultSet.getInt("maxid");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public Integer addUserWithoutAutoInc(User user) {
        Integer result = maxId() + 1;
        if (user == null) return result;
        user.setId(result);
        logger.info("Start add user");
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "INSERT INTO users (first_name, second_name, middle_name, group_id, id) VALUES (?, ?, ?, ?, ?)")) {
            UserMapper.statementSetter(statement, user, 4, 5);
            statement.execute();
            logger.info("Adding user successfully");
        } catch (SQLException e) {
            logger.error(e.getMessage());
            return null;
        }
        return result;
    }

    public User getUser(int id) {
        User result = null;
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM users WHERE id=?")) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) result = UserMapper.getByResultSet(resultSet);
            }
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
        return result;
    }

    @Override
    public List<User> getUsersList() {
        logger.info("Users list requested.");
        List<User> result = new ArrayList<>();
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM users")) {
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    User user = UserMapper.getByResultSet(resultSet);
                    result.add(user);
                }
                logger.info("Users list returned successfully");
            }
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
        return result;
    }


    public boolean updateUser(int id, User newUser) {
        if (newUser == null) return false;
        logger.info("Started updating user.");
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "UPDATE users SET first_name = ?, second_name = ?, middle_name = ?, group_id = ?" +
                             "WHERE id = ?")) {
            UserMapper.statementSetter(statement, newUser, 4, 5);
            statement.execute();
            logger.info("User updated successfully");
        } catch (SQLException e) {
            logger.error(e.getMessage());
            return false;
        }
        return true;
    }

    @Override
    public User findLoginByName(String login) {
        if ((login == null) || login.isEmpty()) return null;
        User user = null;
        logger.info("Start find login by name");
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "SELECT * FROM users WHERE login = ?")) {
            statement.setString(1, login);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) user = UserMapper.getByResultSet(resultSet);
            }
        } catch (SQLException e) {
            logger.error(e.getMessage());
            return null;
        }
        return user;
    }
}
