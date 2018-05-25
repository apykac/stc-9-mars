package ru.innopolis.stc9.dao;

import org.apache.log4j.Logger;
import ru.innopolis.stc9.dao.mapper.UserMapper;
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
public class UserDaoImpl implements UserDao {
    private static ConnectionManager connectionManager = ConnectionManagerImpl.getInstance();
    private UserMapper userMapper = new UserMapper();
    private Logger logger = Logger.getLogger(UserDaoImpl.class);

    @Override
    public boolean addUser(User user) {
        if (user == null) return false;
        logger.info("Start add user");
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "INSERT INTO users (first_name, second_name, middle_name, group_id) " +
                             "VALUES (?, ?, ?, ?)")) {
            statement.setString(1, user.getFirstName());
            statement.setString(2, user.getSecondName());
            statement.setString(3, user.getMddleName());
            statement.setInt(4, user.getGroupId());
            statement.execute();
            logger.info("Adding user successfully");
        } catch (SQLException e) {
            logger.error(e.getMessage());
            return false;
        }
        return true;
    }

    public User getUser(int id) {
        User result=null;
        try(Connection connection = connectionManager.getConnection();
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT * from users WHERE id=?"
            )) {
            statement.setInt(1,id);
            try (ResultSet resultSet = statement.executeQuery()) {
                    result = userMapper.getUserFromResultSet(resultSet);
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
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT * FROM users"
            )) {
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    User user = new User();
                    user.setId(resultSet.getInt("id"));
                    user.setFirstName(resultSet.getString("first_name"));
                    user.setSecondName(resultSet.getString("second_name"));
                    result.add(user);
                }
                logger.info("Users list returned successfully");
            }
        } catch (SQLException e) {
            logger.info(e.getMessage());
        }
        return result;
    }
}
