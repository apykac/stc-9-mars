package ru.innopolis.stc9.service.implementation;

import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import ru.innopolis.stc9.pojo.Message;
import ru.innopolis.stc9.pojo.User;
import ru.innopolis.stc9.service.interfaces.MessageService;

import java.util.List;

@Service
public class MessageServiceImpl implements MessageService {
    @Override
    public List<String> isCorrectData(MultiValueMap<String, String> incParam) {
        return null;
    }

    @Override
    public boolean addMessage(MultiValueMap<String, String> incParam) {
        return false;
    }

    @Override
    public List<Message>[] getAllMessages(User user) {
        return new List[0];
    }

    @Override
    public Message getMessageByIdWithFromUser(int id) {
        return null;
    }

    @Override
    public boolean deleteMessageById(int id) {
        return false;
    }

    @Override
    public int getNumberOfMessage(User user) {
        return 0;
    }

    /*@Override
    public List<String> isCorrectData(MultiValueMap<String, String> incParam) {
        List<String> result = new ArrayList<>();
        if ((incParam == null) || incParam.isEmpty())
            return result;
        if ((incParam.get(MessageMapper.TEXT) != null) && incParam.get(MessageMapper.TEXT).get(0).isEmpty())
            result.add("Не пытайтесь отправить пустое сообщение!");
        return result;
    }*/

    /*@Override
    public boolean addMessage(MultiValueMap<String, String> incParam) {
        if ((incParam == null) || incParam.isEmpty())
            return false;
        String toUser = "toUserId";
        String fromUser = "fromUserId";
        Integer toUserId = incParam.get(toUser) == null ? null : Integer.parseInt(incParam.get(toUser).get(0));
        Integer fromUserId = incParam.get(fromUser) == null ? null : Integer.parseInt(incParam.get(fromUser).get(0));
        Message message = (Message) mapper.getByParam(incParam);
        boolean result = messageDao.addMessage(message, toUserId, fromUserId);

        return result;
    }*/

    /*@Override
    public List<Message>[] getAllMessages(User user) {
        List<Message>[] result = new List[2];
        result[0] = messageDao.getAllMessagesByRole(user);
        result[1] = messageDao.getAllMessagesByToUserId(user);
        return result;
    }*/

    /*@Override
    public Message getMessageByIdWithFromUser(int id) {
        if (id < 0) return null;
        return messageDao.getMessageByIdWithFromUser(id);
    }*/

    /*@Override
    public boolean deleteMessageById(int id) {
        if (id < 0) return false;
        return messageDao.deleteMessageById(id);
    }*/

    /*@Override
    public int getNumberOfMessage(User user) {
        if (user == null) return 0;
        return messageDao.getNumberOfMessage(user);
    }*/
}
