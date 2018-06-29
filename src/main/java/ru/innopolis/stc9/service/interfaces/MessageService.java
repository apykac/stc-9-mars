package ru.innopolis.stc9.service.interfaces;

import org.springframework.util.MultiValueMap;
import ru.innopolis.stc9.pojo.Message;
import ru.innopolis.stc9.pojo.User;

import java.util.List;

public interface MessageService {
    List<String> isCorrectData(MultiValueMap<String, String> incParam);

    boolean addMessage(MultiValueMap<String, String> incParam);

    List<Message>[] getAllMessages(User user);

    Message getMessageByIdWithFromUser(int id);

    boolean deleteMessageById(int id);

    int getNumberOfMessage(User user);
}
