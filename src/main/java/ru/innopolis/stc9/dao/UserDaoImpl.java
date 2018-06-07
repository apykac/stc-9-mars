package ru.innopolis.stc9.dao;

import org.springframework.stereotype.Component;
import ru.innopolis.stc9.pojo.DBObject;
import ru.innopolis.stc9.pojo.User;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by Семушев on 24.05.2018.
 */
@Component
public class UserDaoImpl extends DBObjectDao implements UserDao {
    private String selectPrefix = "SELECT * FROM users ";
    private String updatePrefix = "UPDATE users SET ";
    private String wherePostfix = " WHERE ";

    @Override
    public Mapper getMapper() {
        return new UserMapper();
    }

    @Override
    public boolean delUserById(int id) {
        if (id < 0) return false;
        User user = new User();
        user.setId(id);
        List<String> secondaryParam = new ArrayList<>(Collections.singletonList(UserMapper.ID));
        return executor(user, "DELETE FROM users ", null, wherePostfix, secondaryParam, true);
    }

    @Override
    public boolean addUser(User user) {
        if (user == null) return false;
        List<String> mainParam = new ArrayList<>(Arrays.asList(UserMapper.LOGIN, UserMapper.HASH,
                UserMapper.FNAME, UserMapper.SNAME, UserMapper.MNAME));
        return executor(user, "INSERT INTO users (", mainParam, ") VALUES (?, ?, ?, ?, ?)", null, false);
    }

    @Override
    public List<User> getUsersList() {
        List<DBObject> getList = getter(new User(), selectPrefix, null, null, null, true);
        List<User> result = new ArrayList<>();
        for (int i = 0; i < getList.size(); i++) result.add((User) getList.get(i));
        return result;
    }

    @Override
    public User findUserByUserId(int id) {
        if (id < 0) return null;
        User user = new User();
        user.setId(id);
        List<String> secondaryParam = new ArrayList<>(Collections.singletonList(UserMapper.ID));
        List<DBObject> result = getter(user, selectPrefix, null, wherePostfix, secondaryParam, true);
        return result.isEmpty() ? null : (User) result.get(0);
    }


    @Override
    public User findLoginByName(String login) {
        if (login.equals("anonymousUser") || login.isEmpty()) return null;
        User user = new User();
        user.setLogin(login);
        List<String> secondaryParam = new ArrayList<>(Collections.singletonList(UserMapper.LOGIN));
        List<DBObject> result = getter(user, selectPrefix, null, wherePostfix, secondaryParam, true);
        return result.isEmpty() ? null : (User) result.get(0);
    }

    @Override
    public boolean deactivateUser(int id) {
        if (id < 0) return false;
        User user = new User();
        user.setId(id);
        user.setEnabled(0);
        List<String> mainParam = new ArrayList<>(Collections.singletonList(UserMapper.ENABLED));
        List<String> secondaryParam = new ArrayList<>(Collections.singletonList(UserMapper.ID));
        return executor(user, updatePrefix, mainParam, wherePostfix, secondaryParam, true);
    }

    @Override
    public boolean updateUserByFIOL(User newUser) {
        if (newUser == null) return false;
        List<String> mainParam = new ArrayList<>(Arrays.asList(UserMapper.FNAME, UserMapper.SNAME, UserMapper.MNAME,
                UserMapper.LOGIN, UserMapper.ENABLED, UserMapper.PERMGORUP));
        List<String> secondaryParam = new ArrayList<>(Collections.singletonList(UserMapper.ID));
        return executor(newUser, updatePrefix, mainParam, wherePostfix, secondaryParam, true);
    }

    @Override
    public boolean updateUserPassword(User newUser) {
        if (newUser == null) return false;
        List<String> mainParam = new ArrayList<>(Collections.singletonList(UserMapper.HASH));
        List<String> secondaryParam = new ArrayList<>(Collections.singletonList(UserMapper.ID));
        return executor(newUser, updatePrefix, mainParam, wherePostfix, secondaryParam, true);
    }
}
