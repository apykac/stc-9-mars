package ru.innopolis.stc9.pojo.mappers;

import org.springframework.util.MultiValueMap;
import ru.innopolis.stc9.pojo.User;

public class UserMapper {
    public static final String ID = "id";
    public static final String LOGIN = "login";
    public static final String HASH = "hashPassword";
    public static final String PERMGORUP = "permissionGroup";
    public static final String FNAME = "firstName";
    public static final String SNAME = "secondName";
    public static final String MNAME = "middleName";
    public static final String ENABLED = "enabled";

    public static User getByParam(MultiValueMap<String, String> incParam) {
        User user = new User();
        if ((incParam == null) || incParam.isEmpty()) return user;
        if (incParam.get(ID) != null) user.setId(Integer.parseInt(incParam.get(ID).get(0)));
        if (incParam.get(LOGIN) != null) user.setLogin(incParam.get(LOGIN).get(0));
        if (incParam.get(HASH) != null) user.setHashPassword(incParam.get(HASH).get(0));
        if (incParam.get(PERMGORUP) != null) user.setPermissionGroup(incParam.get(PERMGORUP).get(0));
        if (incParam.get(FNAME) != null) user.setFirstName(incParam.get(FNAME).get(0));
        if (incParam.get(SNAME) != null) user.setSecondName(incParam.get(SNAME).get(0));
        if (incParam.get(MNAME) != null) user.setMiddleName(incParam.get(MNAME).get(0));
        if (incParam.get(ENABLED) != null) user.setEnabled(Integer.parseInt(incParam.get(ENABLED).get(0)));
        return user;
    }
}
