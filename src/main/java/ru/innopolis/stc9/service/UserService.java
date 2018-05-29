package ru.innopolis.stc9.service;

import ru.innopolis.stc9.pojo.User;

import java.util.List;
import java.util.Map;

public interface UserService {
    List<String> isCorrectData(Map<String, String[]> incParam);

    boolean addUser(User user);

    boolean addUserByParam(Map<String, String[]> incParam);

    boolean isExist(String login);

    Integer getRole(String login);

    boolean checkAuth(String login, String password);

    List<User> getUserList();

    boolean updateUser(User user);

    List<User> getStudentsByGroupId(int groupId);

    List<User> getStudentsWithoutGroup(int groupId);

    User findUserById(int userId);
}
