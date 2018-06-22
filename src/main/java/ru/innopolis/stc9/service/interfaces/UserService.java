package ru.innopolis.stc9.service.interfaces;

import org.springframework.util.MultiValueMap;
import ru.innopolis.stc9.pojo.User;

import java.util.List;

public interface UserService {

    String checkPasswordUpdateIsPossible(MultiValueMap<String, String> incParam, User user);

    List<String> isCorrectData(MultiValueMap<String, String> incParam);

    boolean addUserByParam(MultiValueMap<String, String> incParam);

    boolean delUserById(long id);

    boolean isExist(String login);

    List<User> getUserList();

    List<User> getStudentsByGroupId(Long groupId);

    List<User> getAllStudents();

    User findUserById(long userId);

    User findUserByIdWithSubjectList(long userId);

    User findUserByLogin(String login);

    boolean deactivationCurrentAccount(long id);

    boolean checkPasswordOfCurrentAccount(long id, String candidate);

    Object[] editUser(MultiValueMap<String, String> incParam);

    boolean updateGroupId(long userId, Long groupId);

}
