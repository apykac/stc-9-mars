package ru.innopolis.stc9.dao.mapper;

import org.apache.log4j.Logger;
import ru.innopolis.stc9.dao.UserDaoImpl;
import ru.innopolis.stc9.pojo.User;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserMapper {
    private Logger logger = Logger.getLogger(UserDaoImpl.class);

    public User getUserFromResultSet(ResultSet resultSet) {
        User user=null;
        try {
            if (resultSet.next()) {
                user = new User();
                user.setId(resultSet.getInt("id"));
                user.setFirstName(resultSet.getString("first_name"));
                user.setSecondName(resultSet.getString("second_name"));
                user.setMddleName(resultSet.getString("middle_name"));
                user.setGroupId(resultSet.getInt("group_id"));
            }
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
        return user;
    }
}
