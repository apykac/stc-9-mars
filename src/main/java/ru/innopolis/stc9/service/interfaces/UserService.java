package ru.innopolis.stc9.service.interfaces;

import org.springframework.util.MultiValueMap;
import ru.innopolis.stc9.pojo.User;

import java.util.List;

public interface UserService {
    List<String> isCorrectData(MultiValueMap<String, String> incParam);

    boolean addUserByParam(MultiValueMap<String, String> incParam);

    boolean delUserById(int id);

    boolean isExist(String login);

    List<User> getUserList();

    boolean updateUser(User user);

    List<User> getStudentsByGroupId(Integer groupId);

    List<User> getStudentsWithoutGroup(Integer groupId);

    User findUserById(int userId);

    User findUserByLogin(String login);

    boolean deactivationCurrentAccount(int id);

    boolean checkPasswordOfCurrentAccount(int id, String candidate);

    Object[] editUser(MultiValueMap<String, String> incParam);

    boolean updateGroupId(int userId, Integer groupId);
}
