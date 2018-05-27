package ru.innopolis.stc9.dao;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import ru.innopolis.stc9.db.connection.ConnectionManager;
import ru.innopolis.stc9.db.connection.ConnectionManagerImpl;
import ru.innopolis.stc9.pojo.Login;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Семушев on 24.05.2018.
 */
@Component
public class LoginDaoImpl implements LoginDao {
    private static ConnectionManager connectionManager = ConnectionManagerImpl.getInstance();
    private Logger logger = Logger.getLogger(LoginDaoImpl.class);

    @Override
    public boolean addLogin(Login login) {
        if (login == null) return false;
        logger.info("Start add login");
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "INSERT INTO login (userName, hash_password, permission_group, user_id) " +
                             "VALUES (?, ?, ?, ?)")) {
            LoginMapper.statementSetter(statement, login, 4);
            statement.execute();
            logger.info("Adding login successfully");
        } catch (SQLException e) {
            logger.error(e.getMessage());
            return false;
        }
        return true;
    }

    @Override
    public boolean deleteLoginByName(String name) {
        if ((name == null) || name.isEmpty()) return false;
        logger.info("Start delete login by name");
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "DELETE FROM login WHERE userName = ?")) {
            statement.setString(1, name);
            statement.execute();
            logger.info("Deleting login successfully");
        } catch (SQLException e) {
            logger.info(e.getMessage());
            return false;
        }
        return true;
    }

    @Override
    public Login findLoginByName(String name) {
        if ((name == null) || name.isEmpty()) return null;
        Login login = null;
        logger.info("Start find login by name");
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "SELECT * FROM login WHERE userName = ?")) {
            statement.setString(1, name);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) login = LoginMapper.getByResultSet(resultSet);
            }
        } catch (SQLException e) {
            logger.error(e.getMessage());
            return null;
        }
        return login;
    }

    public Login findLoginByUserId(int id) {
        Login result = null;
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "SELECT * FROM login where user_id=?")) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) result = LoginMapper.getByResultSet(resultSet);
            }
        } catch (SQLException e) {
            logger.info(e.getMessage());
        }
        return result;
    }

    @Override
    public boolean updateLogin(Login login) {
        logger.info("Start update login");
        if (login == null) return false;
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "UPDATE login SET userName = ?, hash_password = ?, permission_group = ?, user_id = ? WHERE id = ?")) {
            LoginMapper.statementSetter(statement, login, 5);
            statement.execute();
            logger.info("Updating login successfully");
        } catch (SQLException e) {
            logger.error(e.getMessage());
            return false;
        }
        return true;
    }
}
