package ru.innopolis.stc9.service.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import ru.innopolis.stc9.dao.interfaces.MessageDao;
import ru.innopolis.stc9.dao.mappers.Mapper;
import ru.innopolis.stc9.dao.mappers.MessageMapper;
import ru.innopolis.stc9.pojo.Message;
import ru.innopolis.stc9.service.interfaces.MessageService;

import java.util.ArrayList;
import java.util.List;

@Service
public class MessageServiceImpl implements MessageService {
    Mapper mapper = new MessageMapper();
    @Autowired
    MessageDao messageDao;

    @Override
    public List<String> isCorrectData(MultiValueMap<String, String> incParam) {
        List<String> result = new ArrayList<>();
        if ((incParam.get(MessageMapper.TEXT) != null) && incParam.get(MessageMapper.TEXT).get(0).isEmpty())
            result.add("Не пытайтесь отправить пустое сообщение!");
        return result;
    }

    @Override
    public boolean addMessage(MultiValueMap<String, String> incParam) {
        return messageDao.addMessage((Message) mapper.getByParam(incParam));
    }

    @Override
    public List<Message>[] getAllMessages(int userId, String role) {
        if ((userId < 0) || (role == null) || role.isEmpty()) return new List[2];
        return splitList(messageDao.getAllMessagesByRole(role), userId);
    }

    private List<Message>[] splitList(List<Message> list, int id) {
        List<Message>[] result = new List[2];
        result[0] = new ArrayList<>();
        result[1] = new ArrayList<>();
        for (Message message : list) {
            if (message.getUserId() == id) result[1].add(message);
            else result[0].add(message);
        }
        return result;
    }
}
