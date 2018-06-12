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
    private Mapper mapper = new MessageMapper();
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
    public List<Message>[] getAllMessages(int toUserId, String role) {
        if ((toUserId < 0) || (role == null) || role.isEmpty()) return new List[2];
        List<Message> commonList = messageDao.getAllMessagesByRole(role);
        List<Message> privateList = messageDao.getAllMessagesByToUserId(toUserId);
        return splitList(commonList, privateList);
    }

    @Override
    public Message getMessageById(int id) {
        if (id < 0) return null;
        return messageDao.getMessageById(id);
    }

    @Override
    public boolean deleteMessageById(int id) {
        if (id < 0) return false;
        return messageDao.deleteMessageById(id);
    }

    private List<Message>[] splitList(List<Message> commonList, List<Message> privateList) {
        List<Message>[] result = new List[2];
        for (Message message : privateList)
            for (int j = 0; j < commonList.size(); j++)
                if (message.getId() == commonList.get(j).getId()) {
                    commonList.remove(j);
                    break;
                }
        result[0] = commonList;
        result[1] = privateList;
        return result;
    }
}
