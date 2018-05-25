package ru.innopolis.stc9.service;

import ru.innopolis.stc9.dao.LoginDaoImpl;
import ru.innopolis.stc9.dao.UserDaoImpl;
import ru.innopolis.stc9.pojo.Login;
import ru.innopolis.stc9.pojo.User;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class AdminService {
    private UserDaoImpl userDao = new UserDaoImpl();
    private LoginDaoImpl loginDao = new LoginDaoImpl();

    public List<User> getUsersList() {
        return userDao.getUsersList();
    }


    public User getUser(int id) {
        return userDao.getUser(id);
    }

    public Login getLogin(int id) {
        return loginDao.findLoginByUserId(id);
    }
}
