package ru.innopolis.stc9.dao;

import ru.innopolis.stc9.pojo.User;

import java.util.List;

/**
 * Created by Семушев on 24.05.2018.
 */
public interface UserDao {
    boolean addUser(User user);

    boolean updateUserByFIOL(User newUser);

    boolean updateUserPassword(User newUser);

    User findUserByUserId(int id);

    List<User> getUsersList();

    User findLoginByName(String login);
}
