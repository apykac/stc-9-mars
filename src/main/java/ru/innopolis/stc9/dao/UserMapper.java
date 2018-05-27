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
        user.setLogin(resultSet.getString("login"));
        user.setHashPassword(resultSet.getString("hash_password"));
        user.setPermissionGroup(resultSet.getInt("permission_group"));
        user.setFirstName(resultSet.getString("first_name"));
        user.setSecondName(resultSet.getString("second_name"));
        user.setMiddleName(resultSet.getString("middle_name"));
        user.setGroupId(resultSet.getInt("group_id"));
        return user;
    }

    public static void statementSetter(PreparedStatement statement, User user, int begin, int length) throws SQLException {
        if ((statement == null) || (user == null) || (length < 1)) return;
        switch (begin) {
            case 1:
                statement.setString(1, user.getLogin());
            case 2:
                if ((--length) == 0) return;
                statement.setString(2, user.getHashPassword());
            case 3:
                if ((--length) == 0) return;
                statement.setInt(3, user.getPermissionGroup());
            case 4:
                if ((--length) == 0) return;
                statement.setString(4, user.getFirstName());
            case 5:
                if ((--length) == 0) return;
                statement.setString(5, user.getSecondName());
            case 6:
                if ((--length) == 0) return;
                if (!user.getMiddleName().equals("")) statement.setString(6, user.getMiddleName());
                else statement.setNull(6, Types.VARCHAR);
            case 7:
                if ((--length) == 0) return;
                if (user.getGroupId() != null) statement.setInt(7, user.getGroupId());
                else statement.setNull(7, Types.INTEGER);
            case 8:
                if ((--length) == 0) return;
                statement.setInt(8, user.getId());
        }
    }
}
