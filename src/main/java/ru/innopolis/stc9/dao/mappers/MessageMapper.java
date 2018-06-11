package ru.innopolis.stc9.dao.mappers;

import org.springframework.util.MultiValueMap;
import ru.innopolis.stc9.pojo.Message;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class MessageMapper extends Mapper {
    public static final String ID = "id";
    public static final String USERID = "user_id";
    public static final String TEXT = "text";
    public static final String TOUSERGROUP = "to_user_group";
    public static final String UNAME = "uname";
    public static final String THEME = "theme";
    public static final String TOUSERID = "to_user_id";
    private static final Map<String, Object[]> sourceMap = new HashMap<>();

    public MessageMapper() {
        sourceMap.put(ID, new Object[]{"getId", TypeOfMethod.INT});
        sourceMap.put(USERID, new Object[]{"getUserId", TypeOfMethod.INT});
        sourceMap.put(TEXT, new Object[]{"getText", TypeOfMethod.STRING});
        sourceMap.put(TOUSERGROUP, new Object[]{"getToUserGroup", TypeOfMethod.STRING});
        sourceMap.put(UNAME, new Object[]{"getUname", TypeOfMethod.STRING});
        sourceMap.put(THEME, new Object[]{"getTheme", TypeOfMethod.STRING});
        sourceMap.put(TOUSERID, new Object[]{"getToUserId", TypeOfMethod.INT});
    }

    @Override
    public Map<String, Object[]> getSourceMap() {
        return sourceMap;
    }

    @Override
    public Message getByResultSet(ResultSet resultSet) throws SQLException {
        if (resultSet == null) return null;
        Message message = new Message();
        message.setId(resultSet.getInt(ID));
        message.setUserId(resultSet.getInt(USERID));
        message.setText(resultSet.getString(TEXT));
        message.setToUserGroup(resultSet.getString(TOUSERGROUP));
        message.setUname(resultSet.getString(UNAME));
        message.setTheme(resultSet.getString(THEME));
        message.setToUserId(resultSet.getInt(TOUSERID) == 0 ? null : resultSet.getInt(TOUSERID));
        return message;
    }

    @Override
    public Message getByParam(MultiValueMap<String, String> incParam) {
        Message message = new Message();
        if ((incParam == null) || incParam.isEmpty()) return message;
        if (incParam.get(ID) != null) message.setId(Integer.parseInt(incParam.get(ID).get(0)));
        if (incParam.get(USERID) != null) message.setUserId(Integer.parseInt(incParam.get(USERID).get(0)));
        if (incParam.get(TEXT) != null) message.setText(incParam.get(TEXT).get(0));
        if (incParam.get(TOUSERGROUP) != null) message.setToUserGroup(incParam.get(TOUSERGROUP).get(0));
        if (incParam.get(UNAME) != null) message.setUname(incParam.get(UNAME).get(0));
        if (incParam.get(THEME) != null) message.setTheme(incParam.get(THEME).get(0));
        if (incParam.get(TOUSERID) != null) message.setToUserId(Integer.parseInt(incParam.get(TOUSERID).get(0)));
        return message;
    }
}
