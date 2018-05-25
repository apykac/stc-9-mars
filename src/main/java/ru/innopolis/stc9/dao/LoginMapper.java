package ru.innopolis.stc9.dao;

import ru.innopolis.stc9.pojo.Login;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

public class LoginMapper {
    private LoginMapper() {
    }

    public static Login getByResultSet(ResultSet resultSet) throws SQLException {
        if (resultSet == null) return null;
        Login login = new Login();
        login.setId(resultSet.getInt("id"));
        login.setUserName(resultSet.getString("userName"));
        login.setHashPassword(resultSet.getInt("hash_password"));
        login.setPermissionGroup(resultSet.getInt("permission_group"));
        login.setUserId(resultSet.getInt("user_id"));
        return login;
    }

    public static void statementSetter(PreparedStatement statement, Login login, int length) throws SQLException {
        if ((statement == null) || (login == null) || (length < 1)) return;
        statement.setString(1, login.getUserName());
        if ((--length) == 0) return;
        statement.setLong(2, login.getHashPassword());
        if ((--length) == 0) return;
        statement.setInt(3, login.getPermissionGroup());
        if ((--length) == 0) return;
        if (login.getUserId() != null) statement.setInt(4, login.getUserId());
        else statement.setNull(4, Types.INTEGER);
        if ((--length) == 0) return;
        statement.setInt(5, login.getId());
    }

}
