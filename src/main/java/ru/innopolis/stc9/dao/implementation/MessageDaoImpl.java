package ru.innopolis.stc9.dao.implementation;

import org.springframework.stereotype.Component;
import ru.innopolis.stc9.dao.interfaces.MessageDao;
import ru.innopolis.stc9.dao.mappers.Mapper;
import ru.innopolis.stc9.dao.mappers.MessageMapper;
import ru.innopolis.stc9.pojo.DBObject;
import ru.innopolis.stc9.pojo.Message;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class MessageDaoImpl extends DBObjectDao implements MessageDao {
    @Override
    public Mapper getMapper() {
        return new MessageMapper();
    }

    @Override
    public boolean addMessage(Message message) {
        if (message == null) return false;
        List<String> mainParam = new ArrayList<>(Arrays.asList(MessageMapper.USERID, MessageMapper.TEXT,
                MessageMapper.TOUSERGROUP, MessageMapper.UNAME, MessageMapper.THEME));
        return executor(message, "INSERT INTO messages (", mainParam, ") VALUES (?, ?, ?, ?, ?)", null, false);
    }

    @Override
    public List<Message> getAllMessagesByRole(String role) {
        if ((role == null) || role.isEmpty()) return new ArrayList<>();
        List<Message> result = new ArrayList<>();
        Message message = new Message();
        message.setToUserGroup(role);
        List<String> seondaryParam = new ArrayList<>(Arrays.asList(MessageMapper.TOUSERGROUP));
        List<DBObject> list = getter(message, "SELECT * FROM messages ", null, " WHERE ", seondaryParam, true);
        for (DBObject object : list) result.add((Message) object);
        return result;
    }
}
