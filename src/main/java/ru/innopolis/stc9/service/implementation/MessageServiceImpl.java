package ru.innopolis.stc9.service.implementation;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import ru.innopolis.stc9.pojo.Message;
import ru.innopolis.stc9.pojo.MessageMapper;
import ru.innopolis.stc9.pojo.User;
import ru.innopolis.stc9.service.interfaces.MessageService;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class MessageServiceImpl implements MessageService {
    private Gson gson;

    public MessageServiceImpl() {
        GsonBuilder builder = new GsonBuilder();
        this.gson = builder.create();
    }

    @Override
    public List<String> isCorrectData(MultiValueMap<String, String> incParam) {
        List<String> result = new ArrayList<>();
        if ((incParam == null) || incParam.isEmpty())
            return result;
        if ((incParam.get("text") != null) && incParam.get("text").get(0).isEmpty())
            result.add("Не пытайтесь отправить пустое сообщение!");
        return result;
    }

    @Override
    public boolean addMessage(MultiValueMap<String, String> incParam) {
        if ((incParam == null) || incParam.isEmpty())
            return false;
        String toUser = "toUserId";
        String fromUser = "fromUserId";
        Integer toUserId = incParam.get(toUser) == null ? null : Integer.parseInt(incParam.get(toUser).get(0));
        Integer fromUserId = incParam.get(fromUser) == null ? null : Integer.parseInt(incParam.get(fromUser).get(0));
        Message message = MessageMapper.getByParam(incParam);
        List<Object> param = new ArrayList<>(Arrays.asList(message, toUserId, fromUserId));
        Boolean isSuccess = gson.fromJson(
                RestBridge.doConnectAction("http://localhost:8181/message/addMessage",
                        param, gson, true), Boolean.class);
        return isSuccess;
    }

    @Override
    public List<Message>[] getAllMessages(User user) {
        Type type = new TypeToken<List<Message>>() {
        }.getType();
        List<Message>[] result = new List[2];
        result[0] = gson.fromJson(
                RestBridge.doConnectAction("http://localhost:8181/message/getAllMessagesByRole",
                        user, gson, true), type);
        result[1] = gson.fromJson(
                RestBridge.doConnectAction("http://localhost:8181/message/getAllMessagesByToUserId",
                        user, gson, true), type);
        return result;
    }

    @Override
    public Message getMessageByIdWithFromUser(int id) {
        if (id < 0) return null;
        return gson.fromJson(
                RestBridge.doConnectAction("http://localhost:8181/message/getMessageByIdWithFromUser",
                        new Integer(id), gson, true), Message.class);
    }

    @Override
    public boolean deleteMessageById(int id) {
        if (id < 0) return false;
        return gson.fromJson(
                RestBridge.doConnectAction("http://localhost:8181/message/deleteMessageById",
                        new Integer(id), gson, true), Boolean.class);
    }

    @Override
    public int getNumberOfMessage(User user) {
        if (user == null) return 0;
        return gson.fromJson(
                RestBridge.doConnectAction("http://localhost:8181/message/getNumberOfMessage",
                        user, gson, true), Integer.class);
    }
}
