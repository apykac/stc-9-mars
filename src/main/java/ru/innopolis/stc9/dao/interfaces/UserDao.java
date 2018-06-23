package ru.innopolis.stc9.dao.interfaces;

import ru.innopolis.stc9.pojo.User;

import java.util.List;

/**
 * Created by Семушев on 24.05.2018.
 */
public interface UserDao {
    boolean addUser(User user);

    boolean delUserById(int id);

    boolean deactivateUser(int id);

    boolean updateUserByFIOL(User newUser);

    boolean updateUserPassword(User newUser);

    User findUserByUserId(int id);

    User findUserByIdWithSubjectList(int id);

    List<User> getUsersList();

    User findLoginByName(String login);

    boolean updateGroupId(int userId, Integer groupId);

    List<User> getAllStudents();
}
