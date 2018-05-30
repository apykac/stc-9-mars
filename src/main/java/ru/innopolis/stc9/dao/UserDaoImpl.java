package ru.innopolis.stc9.dao;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import ru.innopolis.stc9.db.connection.ConnectionManager;
import ru.innopolis.stc9.db.connection.ConnectionManagerImpl;
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
@Component
public class UserDaoImpl implements UserDao {
    private static ConnectionManager connectionManager = ConnectionManagerImpl.getInstance();
    private Logger logger = Logger.getLogger(UserDaoImpl.class);

    @Override
    public boolean addUser(User user) {
        if (user == null) return false;
        logger.info("Start add user");
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "INSERT INTO users (login, hash_password, permission_group, first_name, second_name, middle_name) " +
                             "VALUES (?, ?, DEFAULT , ?, ?, ?)")) {
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
    public User findUserByUserId(int id) {
        User result = null;
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "SELECT * FROM users where id=?")) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) result = UserMapper.getByResultSet(resultSet);
            }
        } catch (SQLException e) {
            logger.info(e.getMessage());
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

    @Override
    public boolean updateUserByFIOL(User newUser) {
        if (newUser == null) return false;
        logger.info("Started updating user.");
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "UPDATE users SET first_name = ?, second_name = ?, middle_name = ?, group_id = ?, login = ? " +
                             "WHERE id = ?")) {
            UserMapper.statementSetter(statement, newUser, 3, 4);
            statement.setString(5, newUser.getLogin());
            statement.setInt(6, newUser.getId());
            statement.execute();
            logger.info("User updated successfully");
        } catch (SQLException e) {
            logger.error(e.getMessage());
            return false;
        }
        return true;
    }

    @Override
    public boolean updateUserPassword(User newUser) {
        if (newUser == null) return false;
        logger.info("Started updating user.");
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "UPDATE users SET hash_password = ? WHERE id = ?")) {
            statement.setString(1, newUser.getHashPassword());
            statement.setInt(2, newUser.getId());
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
