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
        int count = 1;
        switch (begin) {
            case 1:
                statement.setString(count++, user.getLogin());
            case 2:
                if (count > length) return;
                statement.setString(count++, user.getHashPassword());
            case 3:
                if (count > length) return;
                statement.setString(count++, user.getFirstName());
            case 4:
                if (count > length) return;
                statement.setString(count++, user.getSecondName());
            case 5:
                if (count > length) return;
                statement.setString(count++, user.getMiddleName());
            case 6:
                if (count > length) return;
                if (user.getGroupId() != null) statement.setInt(count++, user.getGroupId());
                else statement.setNull(count++, Types.INTEGER);
            case 7:
                if (count > length) return;
                statement.setInt(count++, user.getId());
            case 8:
                if (count > length) return;
                statement.setInt(count++, user.getPermissionGroup());

        }
    }
}
