package ru.innopolis.stc9.service;

import org.springframework.util.MultiValueMap;
import ru.innopolis.stc9.pojo.User;

import java.util.List;

public interface UserService {
    List<String> isCorrectData(MultiValueMap<String, String> incParam);

    boolean addUserByParam(MultiValueMap<String, String> incParam);

    boolean isExist(String login);

    List<User> getUserList();

    boolean updateUser(User user);

    List<User> getStudentsByGroupId(int groupId);

    List<User> getStudentsWithoutGroup(int groupId);

    User findUserById(int userId);

    User findUserByLogin(String login);

    Object[] editUser(MultiValueMap<String, String> incParam);
}
