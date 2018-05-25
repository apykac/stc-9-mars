package ru.innopolis.stc9.dao;

import org.apache.log4j.Logger;
import ru.innopolis.stc9.dao.mapper.LoginMapper;
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
public class LoginDaoImpl implements LoginDao {
    private static ConnectionManager connectionManager = ConnectionManagerImpl.getInstance();
    private Logger logger = Logger.getLogger(LoginDaoImpl.class);
    private LoginMapper loginMapper = new LoginMapper();

    @Override
    public boolean addLogin(Login login) {
        if (login == null) return false;
        logger.info("Start add login");
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "INSERT INTO login (userName, hash_password, permission_group, user_id) " +
                             "VALUES (?, ?, ?, ?)")) {
            statement.setString(1, login.getUserName());
            statement.setLong(2, login.getHashPassword());
            statement.setInt(3, login.getPremissionGroup());
            statement.setInt(4, login.getUserId());
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
                if (resultSet.next()) {
                    login = new Login();
                    login.setId(resultSet.getInt("id"));
                    login.setUserName(resultSet.getString("userName"));
                    login.setHashPassword(resultSet.getInt("hash_password"));
                    login.setPremissionGroup(resultSet.getInt("permission_group"));
                    login.setUserId(resultSet.getInt("user_id"));
                }
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
            statement.setInt(1,id);
            try (ResultSet resultSet = statement.executeQuery()) {
                result=new Login();
                loginMapper.setLoginFields(result, resultSet);
            }

        } catch (SQLException e) {
            logger.info(e.getMessage());
        }
        return result;
    }

    @Override
    public boolean updateLogin(Login login) {
        logger.info("Start update login");
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "UPDATE login SET hash_password = ?, permission_group = ?, user_id = ? WHERE id = ?")) {
            statement.setLong(1, login.getHashPassword());
            statement.setInt(2, login.getPremissionGroup());
            statement.setInt(3, login.getUserId());
            statement.setInt(4, login.getId());
            statement.execute();
            logger.info("Updating login successfully");
        } catch (SQLException e) {
            logger.error(e.getMessage());
            return false;
        }
        return true;
    }
}
