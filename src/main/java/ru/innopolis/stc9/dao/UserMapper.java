package ru.innopolis.stc9.dao;

import ru.innopolis.stc9.pojo.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

public class UserMapper {
    private UserMapper() {
    }

    public static User getByResultSet(ResultSet resultSet) throws SQLException {
        if (resultSet == null) return null;
        User user = new User();
        user.setId(resultSet.getInt("id"));
        user.setFirstName(resultSet.getString("first_name"));
        user.setSecondName(resultSet.getString("second_name"));
        user.setMiddleName(resultSet.getString("middle_name"));
        user.setGroupId(resultSet.getInt("group_id"));
        return user;
    }

    public static void statementSetter(PreparedStatement statement, User user, int length) throws SQLException {
        if ((statement == null) || (user == null) || (length < 1)) return;
        statement.setString(1, user.getFirstName());
        if ((--length) == 0) return;
        statement.setString(2, user.getSecondName());
        if ((--length) == 0) return;
        if (!user.getMiddleName().equals("")) statement.setString(3, user.getMiddleName());
        else statement.setNull(3, Types.VARCHAR);
        if ((--length) == 0) return;
        if (user.getGroupId() != null) statement.setInt(4, user.getGroupId());
        else statement.setNull(4, Types.INTEGER);
        if ((--length) == 0) return;
        statement.setInt(5, user.getId());
    }
}
