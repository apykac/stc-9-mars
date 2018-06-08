package ru.innopolis.stc9.dao.mappers;

import org.springframework.util.MultiValueMap;
import ru.innopolis.stc9.pojo.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class UserMapper extends Mapper {
    public static final String ID = "id";
    public static final String LOGIN = "login";
    public static final String HASH = "hash_password";
    public static final String PERMGORUP = "permission_group";
    public static final String FNAME = "first_name";
    public static final String SNAME = "second_name";
    public static final String MNAME = "middle_name";
    public static final String GROUPID = "group_id";
    public static final String ENABLED = "enabled";
    private static final Map<String, Object[]> sourceMap = new HashMap<>();

    public UserMapper() {
        sourceMap.put(ID, new Object[]{"getId", TypeOfMethod.INT});
        sourceMap.put(LOGIN, new Object[]{"getLogin", TypeOfMethod.STRING});
        sourceMap.put(HASH, new Object[]{"getHashPassword", TypeOfMethod.STRING});
        sourceMap.put(PERMGORUP, new Object[]{"getPermissionGroup", TypeOfMethod.STRING});
        sourceMap.put(FNAME, new Object[]{"getFirstName", TypeOfMethod.STRING});
        sourceMap.put(SNAME, new Object[]{"getSecondName", TypeOfMethod.STRING});
        sourceMap.put(MNAME, new Object[]{"getMiddleName", TypeOfMethod.STRING});
        sourceMap.put(GROUPID, new Object[]{"getGroupId", TypeOfMethod.INT});
        sourceMap.put(ENABLED, new Object[]{"getEnabled", TypeOfMethod.INT});
    }

    @Override
    public Map<String, Object[]> getSourceMap() {
        return sourceMap;
    }

    /**
     * Возвращает сущность User по ResultSet
     */
    public User getByResultSet(ResultSet resultSet) throws SQLException {
        if (resultSet == null) return null;
        User user = new User();
        user.setId(resultSet.getInt(ID));
        user.setLogin(resultSet.getString(LOGIN));
        user.setHashPassword(resultSet.getString(HASH));
        user.setPermissionGroup(resultSet.getString(PERMGORUP));
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
    public User getByParam(MultiValueMap<String, String> incParam) {
        User user = new User();
        if ((incParam == null) && incParam.isEmpty()) return user;
        if (incParam.get(ID) != null) user.setId(Integer.parseInt(incParam.get(ID).get(0)));
        if (incParam.get(LOGIN) != null) user.setLogin(incParam.get(LOGIN).get(0));
        if (incParam.get(HASH) != null) user.setHashPassword(incParam.get(HASH).get(0));
        if (incParam.get(PERMGORUP) != null) user.setPermissionGroup(incParam.get(PERMGORUP).get(0));
        if (incParam.get(FNAME) != null) user.setFirstName(incParam.get(FNAME).get(0));
        if (incParam.get(SNAME) != null) user.setSecondName(incParam.get(SNAME).get(0));
        if (incParam.get(MNAME) != null) user.setMiddleName(incParam.get(MNAME).get(0));
        if (incParam.get(GROUPID) != null) user.setGroupId(Integer.parseInt(incParam.get(GROUPID).get(0)));
        if (incParam.get(ENABLED) != null) user.setEnabled(Integer.parseInt(incParam.get(ENABLED).get(0)));
        return user;
    }
}
