package ru.innopolis.stc9.dao.mapper;

import org.apache.log4j.Logger;
import ru.innopolis.stc9.dao.UserDaoImpl;
import ru.innopolis.stc9.pojo.Login;

import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginMapper {
    private Logger logger = Logger.getLogger(UserDaoImpl.class);

    public void setLoginFields(Login login, ResultSet resultSet) {
        if (login==null || resultSet==null) {
            return;
        }
        try {
            if(resultSet.next()) {
                login.setId(resultSet.getInt("id"));
                login.setUserName(resultSet.getString("userName"));
                login.setHashPassword(resultSet.getLong("hash_password"));
                login.setPremissionGroup(resultSet.getInt("permission_group"));
                login.setUserId(resultSet.getInt("user_id"));
            }
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
    }
}
