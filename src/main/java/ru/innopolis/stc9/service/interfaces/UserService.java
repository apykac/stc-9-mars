package ru.innopolis.stc9.service.interfaces;

import org.springframework.util.MultiValueMap;
import ru.innopolis.stc9.pojo.User;

import java.util.List;

public interface UserService {

    String checkPasswordUpdateIsPossible(MultiValueMap<String, String> incParam, User user);

    List<String> isCorrectData(MultiValueMap<String, String> incParam);

    boolean addUserByParam(MultiValueMap<String, String> incParam);

    boolean delUserById(int id);

    boolean isExist(String login);

    List<User> getUserList();

    List<User> getStudentsByGroupId(Integer groupId);

    List<User> getAllStudents();

    User findUserById(int userId);

    User findUserByLogin(String login);

    boolean deactivationCurrentAccount(int id);

    boolean checkPasswordOfCurrentAccount(int id, String candidate);

    Object[] editUser(MultiValueMap<String, String> incParam);

    boolean updateGroupId(int userId, Integer groupId);
}
