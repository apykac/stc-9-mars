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


}
