package ru.innopolis.stc9.dao.interfaces;

import ru.innopolis.stc9.pojo.Message;
import ru.innopolis.stc9.pojo.User;

import java.util.List;

public interface MessageDao {
    boolean addMessage(Message message);

    List<Message> getAllMessagesByRole(String role);

    List<Message> getAllMessagesByToUserId(long toUserId);

    boolean deleteMessageById(long id);

    Message getMessageById(long id);

    long getNumberOfMessage(User user);
}
