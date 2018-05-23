package ru.innopolis.stc9.dao;

import org.apache.log4j.Logger;
import ru.innopolis.stc9.db.connection.ConnectionManager;
import ru.innopolis.stc9.db.connection.ConnectionManagerImpl;
import ru.innopolis.stc9.pogo.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

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
}
