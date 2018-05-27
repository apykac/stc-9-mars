package ru.innopolis.stc9.service;

import ru.innopolis.stc9.dao.UserDao;
import ru.innopolis.stc9.dao.UserDaoImpl;
import ru.innopolis.stc9.pojo.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class UserServiceImpl implements UserService {
    private UserDao userDao = new UserDaoImpl();

    @Override
    public List<String> isCorrectData(Map<String, String[]> incParam) {
        List<String> result = new ArrayList<>();
        if (incParam == null || incParam.isEmpty()) return result;
        if ((incParam.get("userName") != null) && incParam.get("userName")[0].equals(""))
            result.add("Invalid/Exist Login");
        if (incParam.get("hash_password") != null && incParam.get("hash_password")[0].equals(""))
            result.add("Invalid password");
        if (incParam == null || incParam.isEmpty()) return result;
        if ((incParam.get("first_name") != null) && incParam.get("first_name")[0].equals(""))
            result.add("Invalid first name");
        if ((incParam.get("second_name") != null) && incParam.get("second_name")[0].equals(""))
            result.add("Invalid second name");
        try {
            if (incParam.get("groupId") != null)
                Integer.parseInt(incParam.get("groupId")[0]);
        } catch (NumberFormatException e) {
            result.add("Invalid ID of group");
        }
        return result;
    }

    @Override
    public boolean addUser(User user) {
        if (user == null) return false;
        return userDao.addUser(user);
    }

    @Override
    public Integer addUserWithoutAutoInc(Map<String, String[]> incParam) {
        return userDao.addUserWithoutAutoInc(
                new User(incParam.get("first_name")[0],
                        incParam.get("second_name")[0],
                        incParam.get("middle_name")[0],
                        null)
        );
    }

    @Override
    public boolean addUserByParam(Map<String, String[]> incParam) {
        if ((incParam == null) || incParam.isEmpty()) return false;
        User user = new User(
                incParam.get("login")[0],
                incParam.get("hash_password")[0],
                incParam.get("first_name")[0],
                incParam.get("second_name")[0],
                incParam.get("middle_name")[0]);
        return userDao.addUser(user);
    }
}
