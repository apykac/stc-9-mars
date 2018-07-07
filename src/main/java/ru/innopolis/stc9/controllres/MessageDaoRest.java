package ru.innopolis.stc9.controllres;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ru.innopolis.stc9.dao.interfaces.MessageDao;
import ru.innopolis.stc9.pojo.Message;
import ru.innopolis.stc9.pojo.User;

import java.lang.reflect.Type;
import java.util.List;

@RestController
@RequestMapping("/message")
public class MessageDaoRest {
    private Gson gson;
    private MessageDao messageDao;

    @Autowired
    public MessageDaoRest(MessageDao messageDao) {
        this.messageDao = messageDao;
        GsonBuilder builder = new GsonBuilder();
        this.gson = builder.create();
    }

    private void nullSetter(Message message) {
        message.setFromUser(null);
        message.setToUser(null);
    }

    @RequestMapping(
            value = "/addMessage",
            method = RequestMethod.POST,
            produces = "application/json; charset=utf-8",
            consumes = "application/json; charset=utf-8")
    public String addMessage(@RequestBody String incParam) {
        Type type = new TypeToken<List<Object>>() {
        }.getType();
        List<Object> param = gson.fromJson(incParam, type);
        return gson.toJson(messageDao.addMessage((Message) param.get(0), (Integer) param.get(1), (Integer) param.get(2)));
    }

    @RequestMapping(
            value = "/getAllMessagesByRole",
            method = RequestMethod.POST,
            produces = "application/json; charset=utf-8",
            consumes = "application/json; charset=utf-8")
    public String getAllMessagesByRole(@RequestBody String user) {
        List<Message> messages = messageDao.getAllMessagesByRole(gson.fromJson(user, User.class));
        for (Message message : messages) nullSetter(message);
        return gson.toJson(messages);
    }

    @RequestMapping(
            value = "/getAllMessagesByToUserId",
            method = RequestMethod.POST,
            produces = "application/json; charset=utf-8",
            consumes = "application/json; charset=utf-8")
    public String getAllMessagesByToUserId(@RequestBody String user) {
        List<Message> messages = messageDao.getAllMessagesByToUserId(gson.fromJson(user, User.class));
        for (Message message : messages) nullSetter(message);
        return gson.toJson(messages);
    }

    @RequestMapping(
            value = "/deleteMessageById",
            method = RequestMethod.POST,
            produces = "application/json; charset=utf-8",
            consumes = "application/json; charset=utf-8")
    public String deleteMessageById(@RequestBody String messageId) {
        return gson.toJson(messageDao.deleteMessageById(gson.fromJson(messageId, Integer.class)));
    }

    @RequestMapping(
            value = "/getMessageByIdWithFromUser",
            method = RequestMethod.POST,
            produces = "application/json; charset=utf-8",
            consumes = "application/json; charset=utf-8")
    public String getMessageByIdWithFromUser(@RequestBody String messageId) {
        Message message = messageDao.getMessageByIdWithFromUser(gson.fromJson(messageId, Integer.class));
        message.setToUser(null);
        if (message.getFromUser() != null) {
            if (message.getFromUser().getGroup() != null) {
                message.getFromUser().getGroup().setUsers(null);
                message.getFromUser().getGroup().setSubjects(null);
            }
            message.getFromUser().setAttendances(null);
            message.getFromUser().setHomeWorks(null);
            message.getFromUser().setIncomingMessages(null);
            message.getFromUser().setMarks(null);
            message.getFromUser().setUpcomingMessages(null);
        }
        return gson.toJson(message);
    }

    @RequestMapping(
            value = "/getNumberOfMessage",
            method = RequestMethod.POST,
            produces = "application/json; charset=utf-8",
            consumes = "application/json; charset=utf-8")
    public String getNumberOfMessage(@RequestBody String user) {
        return gson.toJson(messageDao.getNumberOfMessage(gson.fromJson(user, User.class)));
    }
}
