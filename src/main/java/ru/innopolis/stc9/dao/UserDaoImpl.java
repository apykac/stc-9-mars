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
import java.util.Collections;
import java.util.List;

/**
 * Created by Семушев on 24.05.2018.
 */
@Component
public class UserDaoImpl implements UserDao {
    private static ConnectionManager connectionManager = ConnectionManagerImpl.getInstance();
    private Logger logger = Logger.getLogger(UserDaoImpl.class);
    private String sqlPrefixMsg = "SQLException: ";
    private String selectPrefix = "SELECT * FROM users ";
    private String updatePrefix = "UPDATE users SET ";
    private String wherePostfix = " WHERE ";

    public boolean executor(User user, String prefix, List<String> mainParam, String postfix, List<String> secondaryParam, boolean isEqually) {
        if (user == null) return false;
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     UserMapper.getSqlRequestByParam(prefix, mainParam, postfix, secondaryParam, isEqually))) {
            UserMapper.statementSetterUniversal(statement, user, mainParam, secondaryParam, false);
            statement.execute();
        } catch (SQLException e) {
            logger.error(sqlPrefixMsg + e.getMessage());
            return false;
        }
        return true;
    }

    public List<User> getter(User user, String prefix, List<String> mainParam, String postfix, List<String> secondaryParam, boolean isEqually) {
        List<User> result = new ArrayList<>();
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     UserMapper.getSqlRequestByParam(prefix, mainParam, postfix, secondaryParam, isEqually))) {
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
    public boolean delUserById(int id) {
        if (id < 0) return false;
        User user = new User();
        user.setId(id);
        List<String> secondaryParam = new ArrayList<>(Collections.singletonList(UserMapper.ID));
        return executor(user, "DELETE FROM users ", null, wherePostfix, secondaryParam, true);
    }

    @Override
    public boolean addUser(User user) {
        if (user == null) return false;
        List<String> mainParam = new ArrayList<>(Arrays.asList(UserMapper.LOGIN, UserMapper.HASH,
                UserMapper.FNAME, UserMapper.SNAME, UserMapper.MNAME));
        return executor(user, "INSERT INTO users (", mainParam, ") VALUES (?, ?, ?, ?, ?)", null, false);
    }

    @Override
    public List<User> getUsersList() {
        return getter(new User(), selectPrefix, null, null, null, true);
    }

    @Override
    public User findUserByUserId(int id) {
        if (id < 0) return null;
        User user = new User();
        user.setId(id);
        List<String> secondaryParam = new ArrayList<>(Collections.singletonList(UserMapper.ID));
        List<User> result = getter(user, selectPrefix, null, wherePostfix, secondaryParam, true);
        return result.isEmpty() ? null : result.get(0);
    }


    @Override
    public User findLoginByName(String login) {
        if (login.equals("anonymousUser") || login.isEmpty()) return null;
        User user = new User();
        user.setLogin(login);
        List<String> secondaryParam = new ArrayList<>(Collections.singletonList(UserMapper.LOGIN));
        List<User> result = getter(user, selectPrefix, null, wherePostfix, secondaryParam, true);
        return result.isEmpty() ? null : result.get(0);
    }

    @Override
    public boolean deactivateUser(int id) {
        if (id < 0) return false;
        User user = new User();
        user.setId(id);
        user.setEnabled(0);
        List<String> mainParam = new ArrayList<>(Collections.singletonList(UserMapper.ENABLED));
        List<String> secondaryParam = new ArrayList<>(Collections.singletonList(UserMapper.ID));
        return executor(user, updatePrefix, mainParam, wherePostfix, secondaryParam, true);
    }

    @Override
    public boolean updateUserByFIOL(User newUser) {
        if (newUser == null) return false;
        List<String> mainParam = new ArrayList<>(Arrays.asList(UserMapper.FNAME, UserMapper.SNAME, UserMapper.MNAME,
                UserMapper.LOGIN, UserMapper.ENABLED, UserMapper.PERMGROUP));
        List<String> secondaryParam = new ArrayList<>(Collections.singletonList(UserMapper.ID));
        return executor(newUser, updatePrefix, mainParam, wherePostfix, secondaryParam, true);
    }

    @Override
    public boolean updateUserPassword(User newUser) {
        if (newUser == null) return false;
        List<String> mainParam = new ArrayList<>(Collections.singletonList(UserMapper.HASH));
        List<String> secondaryParam = new ArrayList<>(Collections.singletonList(UserMapper.ID));
        return executor(newUser, updatePrefix, mainParam, wherePostfix, secondaryParam, true);
    }
}
