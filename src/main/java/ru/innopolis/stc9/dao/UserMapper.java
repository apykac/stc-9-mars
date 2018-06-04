package ru.innopolis.stc9.dao;

import org.springframework.util.MultiValueMap;
import ru.innopolis.stc9.pojo.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

public class UserMapper {
    private UserMapper() {
    }

    /**
     * Возвращает ущность User по ResultSet
     */
    public static User getByResultSet(ResultSet resultSet) throws SQLException {
        if (resultSet == null) return null;
        User user = new User();
        user.setId(resultSet.getInt(UserField.ID));
        user.setLogin(resultSet.getString(UserField.LOGIN));
        user.setHashPassword(resultSet.getString(UserField.HASH));
        user.setPermissionGroup(resultSet.getString(UserField.PERMGROUP));
        user.setFirstName(resultSet.getString(UserField.FNAME));
        user.setSecondName(resultSet.getString(UserField.SNAME));
        user.setMiddleName(resultSet.getString(UserField.MNAME));
        user.setGroupId(resultSet.getInt(UserField.GROUPID));
        user.setEnabled(resultSet.getInt(UserField.ENABLED));
        return user;
    }

    /**
     * Возвращает User по параметрам переданным со страницы
     */
    public static User getByParam(MultiValueMap<String, String> incParam) {
        User user = new User();
        if ((incParam == null) || incParam.isEmpty()) return user;
        if (incParam.get(UserField.ID) != null)
            user.setId(Integer.parseInt(incParam.get(UserField.ID).get(0)));
        if (incParam.get(UserField.LOGIN) != null)
            user.setLogin(incParam.get(UserField.LOGIN).get(0));
        if (incParam.get(UserField.HASH) != null)
            user.setHashPassword(incParam.get(UserField.HASH).get(0));
        if (incParam.get(UserField.PERMGROUP) != null)
            user.setPermissionGroup(incParam.get(UserField.PERMGROUP).get(0));
        if (incParam.get(UserField.FNAME) != null)
            user.setFirstName(incParam.get(UserField.FNAME).get(0));
        if (incParam.get(UserField.SNAME) != null)
            user.setSecondName(incParam.get(UserField.SNAME).get(0));
        if (incParam.get(UserField.MNAME) != null)
            user.setMiddleName(incParam.get(UserField.MNAME).get(0));
        if (incParam.get(UserField.GROUPID) != null)
            user.setGroupId(Integer.parseInt(incParam.get(UserField.GROUPID).get(0)));
        if (incParam.get(UserField.ENABLED) != null)
            user.setEnabled(Integer.parseInt(incParam.get(UserField.ENABLED).get(0)));
        return user;
    }

    /**
     * Сеттер PreparedStatement
     *
     * @param statement сам стейтмента
     * @param user      даные для сеттера
     * @param begin     начало строчки сеттинга
     * @param shift     смещения count statement.set(?, user.get());
     * @param length    для прохода по строчкам
     * @throws SQLException
     */
    public static void statementSetter(PreparedStatement statement, User user, int begin, int shift, int length) throws SQLException {
        if ((statement == null) || (user == null) || (begin < 1) || (length < 1) || (shift < 1)) return;
        int count = shift;
        if (begin <= 1)
            statement.setString(count++, user.getLogin());
        if ((begin <= 2) && count <= length)
            statement.setString(count++, user.getHashPassword());
        if ((begin <= 3) && count <= length)
            statement.setString(count++, user.getFirstName());
        if ((begin <= 4) && count <= length)
            statement.setString(count++, user.getSecondName());
        if ((begin <= 5) && count <= length)
            statement.setString(count++, user.getMiddleName());
        if ((begin <= 6) && count <= length) {
            if (user.getGroupId() != null && user.getGroupId() != 0) statement.setInt(count++, user.getGroupId());
            else statement.setNull(count++, Types.INTEGER);
        }
        if ((begin <= 7) && count <= length)
            statement.setInt(count++, user.getId());
        if ((begin <= 8) && count <= length)
            statement.setString(count++, user.getPermissionGroup());
        if ((begin <= 9) && count <= length)
            statement.setInt(count, user.getEnabled());
    }
}
