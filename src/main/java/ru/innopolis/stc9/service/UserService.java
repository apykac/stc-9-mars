package ru.innopolis.stc9.service;

import ru.innopolis.stc9.pojo.User;

import java.util.List;
import java.util.Map;

public interface UserService {
    public List<String> isCorrectData(Map<String, String[]> incParam);

    boolean addUser(User user);

    Integer addUserWithoutAutoInc(Map<String, String[]> incParam);

    boolean addUserByParam(Map<String, String[]> incParam);
}
