package ru.innopolis.stc9.dao.interfaces;

import ru.innopolis.stc9.pojo.Message;

import java.util.List;

public interface MessageDao {
    boolean addMessage(Message message);

    List<Message> getAllMessagesByRole(String role);

    List<Message> getAllMessagesByToUserId(int toUserId);

    boolean deleteMessageById(int id);

    Message getMessageById(int id);
}
