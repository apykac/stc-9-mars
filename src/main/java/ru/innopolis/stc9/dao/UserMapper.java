package ru.innopolis.stc9.dao;

import org.apache.log4j.Logger;
import org.springframework.util.MultiValueMap;
import ru.innopolis.stc9.pojo.User;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserMapper {
    public static final String ID = "id";
    public static final String LOGIN = "login";
    public static final String HASH = "hash_password";
    public static final String PERMGROUP = "permission_group";
    public static final String FNAME = "first_name";
    public static final String SNAME = "second_name";
    public static final String MNAME = "middle_name";
    public static final String GROUPID = "group_id";
    public static final String ENABLED = "enabled";
    protected static final Map<String, Object[]> sourceMap = new HashMap<>();
    private static Logger logger = Logger.getLogger(UserMapper.class);

    static {
        sourceMap.put(ID, new Object[]{"getId", TypeOfMethod.INT});
        sourceMap.put(LOGIN, new Object[]{"getLogin", TypeOfMethod.STRING});
        sourceMap.put(HASH, new Object[]{"getHashPassword", TypeOfMethod.STRING});
        sourceMap.put(PERMGROUP, new Object[]{"getPermissionGroup", TypeOfMethod.STRING});
        sourceMap.put(FNAME, new Object[]{"getFirstName", TypeOfMethod.STRING});
        sourceMap.put(SNAME, new Object[]{"getSecondName", TypeOfMethod.STRING});
        sourceMap.put(MNAME, new Object[]{"getMiddleName", TypeOfMethod.STRING});
        sourceMap.put(GROUPID, new Object[]{"getGroupId", TypeOfMethod.INT});
        sourceMap.put(ENABLED, new Object[]{"getEnabled", TypeOfMethod.INT});
    }

    private UserMapper() {
    }

    public static String getSqlRequestByParam(String prefix, List<String> mainParam, String postfix, List<String> secondaryParam, boolean isEqually) {
        if (prefix == null) return null;
        StringBuilder result = new StringBuilder(prefix);
        if ((mainParam != null) && !mainParam.isEmpty()) result.append(listToString(mainParam, isEqually));
        if ((postfix != null) && !postfix.isEmpty()) result.append(postfix);
        if ((secondaryParam != null) && !secondaryParam.isEmpty())
            result.append(listToString(secondaryParam, isEqually));
        return result.toString();
    }

    private static String listToString(List<String> list, boolean isEqually) {
        StringBuilder result = new StringBuilder();
        String eq = isEqually ? " = ?" : "";
        for (int i = 0; i < list.size(); i++)
            if (i == list.size() - 1) result.append(list.get(i) + eq);
            else result.append(list.get(i) + eq + ", ");
        return result.toString();
    }

    public static void statementSetterUniversal(PreparedStatement statement, Object object, List<String> mainParam, List<String> secondaryParam, boolean isPattern) {
        if ((statement == null) || (object == null) || (mainParam == null)) return;
        if (secondaryParam != null) mainParam.addAll(secondaryParam);
        int count = 1;
        try {
            for (String param : mainParam) {
                Method method = object.getClass().getMethod((String) sourceMap.get(param)[0]);
                setValue(statement, count++, (TypeOfMethod) sourceMap.get(param)[1], method.invoke(object), isPattern);
            }
        } catch (NoSuchMethodException e) {
            logger.error("NoSuchMethodException: " + e.getMessage());
        } catch (IllegalAccessException e) {
            logger.error("IllegalAccessException: " + e.getMessage());
        } catch (InvocationTargetException e) {
            logger.error("InvocationTargetException: " + e.getMessage());
        }
    }

    public static void setValue(PreparedStatement statement, int count, TypeOfMethod type, Object o, boolean isPattern) {
        if ((statement == null) || (count < 0) || (o == null)) return;
        try {
            switch (type) {
                case DATE:
                    statement.setDate(count, (Date) o);
                    return;
                case STRING:
                    statement.setString(count, isPattern ? "%" + o + "%" : (String) o);
                    return;
                case INT:
                    statement.setInt(count, (Integer) o);
            }
        } catch (SQLException e) {
            logger.error("SQLException: " + e.getMessage());
        }
    }

    /**
     * Возвращает сущность User по ResultSet
     */
    public static User getByResultSet(ResultSet resultSet) throws SQLException {
        if (resultSet == null) return null;
        User user = new User();
        user.setId(resultSet.getInt(ID));
        user.setLogin(resultSet.getString(LOGIN));
        user.setHashPassword(resultSet.getString(HASH));
        user.setPermissionGroup(resultSet.getString(PERMGROUP));
        user.setFirstName(resultSet.getString(FNAME));
        user.setSecondName(resultSet.getString(SNAME));
        user.setMiddleName(resultSet.getString(MNAME));
        user.setGroupId(resultSet.getInt(GROUPID));
        user.setEnabled(resultSet.getInt(ENABLED));
        return user;
    }

    /**
     * Возвращает User по параметрам переданным со страницы
     */
    public static User getByParam(MultiValueMap<String, String> incParam) {
        User user = new User();
        if ((incParam == null) || incParam.isEmpty()) return user;
        if (incParam.get(ID) != null) user.setId(Integer.parseInt(incParam.get(ID).get(0)));
        if (incParam.get(LOGIN) != null) user.setLogin(incParam.get(LOGIN).get(0));
        if (incParam.get(HASH) != null) user.setHashPassword(incParam.get(HASH).get(0));
        if (incParam.get(PERMGROUP) != null) user.setPermissionGroup(incParam.get(PERMGROUP).get(0));
        if (incParam.get(FNAME) != null) user.setFirstName(incParam.get(FNAME).get(0));
        if (incParam.get(SNAME) != null) user.setSecondName(incParam.get(SNAME).get(0));
        if (incParam.get(MNAME) != null) user.setMiddleName(incParam.get(MNAME).get(0));
        if (incParam.get(GROUPID) != null) user.setGroupId(Integer.parseInt(incParam.get(GROUPID).get(0)));
        if (incParam.get(ENABLED) != null) user.setEnabled(Integer.parseInt(incParam.get(ENABLED).get(0)));
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
