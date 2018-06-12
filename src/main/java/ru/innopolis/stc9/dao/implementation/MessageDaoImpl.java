package ru.innopolis.stc9.dao.implementation;

import org.springframework.stereotype.Component;
import ru.innopolis.stc9.dao.interfaces.MessageDao;
import ru.innopolis.stc9.dao.mappers.Mapper;
import ru.innopolis.stc9.dao.mappers.MessageMapper;
import ru.innopolis.stc9.pojo.DBObject;
import ru.innopolis.stc9.pojo.Message;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Component
public class MessageDaoImpl extends DBObjectDao implements MessageDao {
    private String selectPrefix = "SELECT * FROM messages ";
    private String wherePostfix = " WHERE ";

    @Override
    public Mapper getMapper() {
        return new MessageMapper();
    }

    @Override
    public boolean addMessage(Message message) {
        if (message == null) return false;
        List<String> mainParam = new ArrayList<>(Arrays.asList(MessageMapper.USERID, MessageMapper.TEXT,
                MessageMapper.TOUSERGROUP, MessageMapper.UNAME, MessageMapper.THEME, MessageMapper.TOUSERID));
        return executor(message, "INSERT INTO messages (", mainParam,
                ") VALUES (?, ?, ?, ?, ?, ?)", null, false);
    }

    @Override
    public List<Message> getAllMessagesByRole(String role) {
        if ((role == null) || role.isEmpty()) return new ArrayList<>();
        List<Message> result = new ArrayList<>();
        Message message = new Message();
        message.setToUserGroup(role);
        List<String> secondaryParam = new ArrayList<>(Collections.singletonList(MessageMapper.TOUSERGROUP));
        List<DBObject> list = getter(message, selectPrefix, null, wherePostfix, secondaryParam, true);
        for (DBObject object : list) result.add((Message) object);
        return result;
    }

    @Override
    public List<Message> getAllMessagesByToUserId(int toUserId) {
        List<Message> result = new ArrayList<>();
        Message message = new Message();
        message.setToUserId(toUserId);
        List<String> secondaryParam = new ArrayList<>(Collections.singletonList(MessageMapper.TOUSERID));
        List<DBObject> list = getter(message, selectPrefix, null, wherePostfix, secondaryParam, true);
        for (DBObject object : list) result.add((Message) object);
        return result;
    }

    @Override
    public boolean deleteMessageById(int id) {
        if (id < 0) return false;
        Message message = new Message();
        message.setId(id);
        List<String> secondaryParam = new ArrayList<>(Collections.singletonList(MessageMapper.ID));
        return executor(message, "DELETE FROM messages ", null, wherePostfix, secondaryParam, true);
    }

    @Override
    public Message getMessageById(int id) {
        if (id < 0) return null;
        Message message = new Message();
        message.setId(id);
        List<String> seondaryParam = new ArrayList<>(Collections.singletonList(MessageMapper.ID));
        return (Message) getter(message, selectPrefix, null, wherePostfix, seondaryParam, true).get(0);
    }
}
