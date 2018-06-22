package ru.innopolis.stc9.dao.interfaces;

import ru.innopolis.stc9.pojo.User;

import java.util.List;

/**
 * Created by Семушев on 24.05.2018.
 */
public interface UserDao {
    boolean addUser(User user);

    boolean delUserById(long id);

    boolean deactivateUser(long id);

    boolean updateUserByFIOL(User newUser);

    boolean updateUserPassword(User newUser);

    User findUserByUserId(long id);

    User findUserByIdWithSubjectList(long id);

    List<User> getUsersList();

    User findLoginByName(String login);

    boolean updateGroupId(long userId, Long groupId);

    List<User> getAllStudents();
}
