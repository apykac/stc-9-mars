package ru.innopolis.stc9.pojo.mappers;


import org.springframework.util.MultiValueMap;
import ru.innopolis.stc9.pojo.Message;

public class MessageMapper {
    public static final String ID = "id";
    public static final String TEXT = "text";
    public static final String TOUSERGROUP = "toUserGroup";
    public static final String UNAME = "uname";
    public static final String THEME = "theme";

    public static Message getByParam(MultiValueMap<String, String> incParam) {
        Message message = new Message();
        if ((incParam == null) || incParam.isEmpty()) return message;
        if (incParam.get(ID) != null) message.setId(Integer.parseInt(incParam.get(ID).get(0)));
        if (incParam.get(TEXT) != null) message.setText(incParam.get(TEXT).get(0));
        if (incParam.get(TOUSERGROUP) != null) message.setToUserGroup(incParam.get(TOUSERGROUP).get(0));
        if (incParam.get(UNAME) != null) message.setUname(incParam.get(UNAME).get(0));
        if (incParam.get(THEME) != null) message.setTheme(incParam.get(THEME).get(0));
        return message;
    }
}