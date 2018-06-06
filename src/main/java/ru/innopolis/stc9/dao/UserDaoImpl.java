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
import java.util.Arrays;
import java.util.List;

/**
 * Created by Семушев on 24.05.2018.
 */
@Component
public class UserDaoImpl implements UserDao {
    private static ConnectionManager connectionManager = ConnectionManagerImpl.getInstance();
    private Logger logger = Logger.getLogger(UserDaoImpl.class);
    private String sqlPrefixMsg = "SQLException: ";

    public boolean update(User user, String prefix, List<String> mainParam, String postfix, List<String> secondaryParam) {
        if (user == null) return false;
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     UserMapper.getSqlRequestByParam(prefix, mainParam, postfix, secondaryParam, true))) {
            UserMapper.statementSetterUniversal(statement, user, mainParam, secondaryParam, false);
            statement.execute();
        } catch (SQLException e) {
            logger.error(sqlPrefixMsg + e.getMessage());
            return false;
        }
        return true;
    }

    @Override
    public List<User> getUsersList() {
        return get(new User(), "SELECT * FROM users ", null, null, null);
    }

    @Override
    public User findUserByUserId(int id) {
        if (id < 0) return null;
        User user = new User();
        user.setId(id);
        List<String> secondaryParam = new ArrayList<>(Arrays.asList(UserMapper.ID));
        return get(user, "SELECT * FROM users ", null, " WHERE ", secondaryParam).get(0);
    }

    public List<User> get(User user, String prefix, List<String> mainParam, String postfix, List<String> secondaryParam) {
        List<User> result = new ArrayList<>();
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     UserMapper.getSqlRequestByParam(prefix, mainParam, postfix, secondaryParam, true))) {
            UserMapper.statementSetterUniversal(statement, user, mainParam, secondaryParam, false);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) result.add(UserMapper.getByResultSet(resultSet));
            }
        } catch (SQLException e) {
            logger.error(sqlPrefixMsg + e.getMessage());
        }
        return result;
    }

    @Override
    public boolean addUser(User user) {
        if (user == null) return false;
        logger.info("Start add user");
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "INSERT INTO users (login, hash_password, permission_group, first_name, second_name, middle_name) " +
                             "VALUES (?, ?, DEFAULT , ?, ?, ?)")) {
            UserMapper.statementSetter(statement, user, 1, 1, 5);
            statement.execute();
            logger.info("Adding user successfully");
        } catch (SQLException e) {
            logger.error(sqlPrefixMsg + e.getMessage());
            return false;
        }
        return true;
    }

    @Override
    public boolean delUserById(int id) {
        if (id < 0) return false;
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "DELETE FROM users WHERE id = ?")) {
            statement.setInt(1, id);
            statement.execute();
        } catch (SQLException e) {
            logger.error(sqlPrefixMsg + e.getMessage());
            return false;
        }
        return true;
    }

    @Override
    public boolean deactivateUser(int id) {
        if (id < 0) return false;
        User user = new User();
        user.setId(id);
        user.setEnabled(0);
        List<String> mainParam = new ArrayList<>(Arrays.asList(UserMapper.ENABLED));
        List<String> secondaryParam = new ArrayList<>(Arrays.asList(UserMapper.ID));
        return update(user, "UPDATE users SET ", mainParam, " WHERE ", secondaryParam);
    }

    @Override
    public boolean updateUserByFIOL(User newUser) {
        if (newUser == null) return false;
        List<String> mainParam = new ArrayList<>(Arrays.asList(UserMapper.FNAME, UserMapper.SNAME, UserMapper.MNAME,
                UserMapper.LOGIN, UserMapper.ENABLED, UserMapper.PERMGROUP));
        List<String> secondaryParam = new ArrayList<>(Arrays.asList(UserMapper.ID));
        return update(newUser, "UPDATE users SET ", mainParam, " WHERE ", secondaryParam);
    }

    @Override
    public boolean updateUserPassword(User newUser) {
        if (newUser == null) return false;
        List<String> mainParam = new ArrayList<>(Arrays.asList(UserMapper.HASH));
        List<String> secondaryParam = new ArrayList<>(Arrays.asList(UserMapper.ID));
        return update(newUser, "UPDATE users SET ", mainParam, " WHERE ", secondaryParam);
    }

    @Override
    public User findLoginByName(String login) {
        if (login.equals("anonymousUser") || login.isEmpty()) return null;
        User user = null;
        logger.info("Start find login by name (" + login + ")");
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "SELECT * FROM users WHERE login = ?")) {
            statement.setString(1, login);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) user = UserMapper.getByResultSet(resultSet);
            }
        } catch (SQLException e) {
            logger.error(sqlPrefixMsg + e.getMessage());
            return null;
        }
        return user;
    }
}
