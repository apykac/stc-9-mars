package ru.innopolis.stc9.dao.interfaces;

import ru.innopolis.stc9.pojo.Message;
import ru.innopolis.stc9.pojo.User;

import java.util.List;

public interface MessageDao {
    boolean addMessage(Message message, Integer toUserId, Integer fromUserId);

    List<Message> getAllMessagesByRole(User user);

    List<Message> getAllMessagesByToUserId(User user);

    boolean deleteMessageById(int id);

    Message getMessageById(int id);

    Message getMessageByIdWithFromUser(int id);

    int getNumberOfMessage(User user);
}
