package ru.innopolis.stc9.service;

import ru.innopolis.stc9.dao.LoginDao;
import ru.innopolis.stc9.dao.LoginDaoImpl;
import ru.innopolis.stc9.pojo.Login;

/**
 * Created by aldar on 24.05.2018.
 * Реализация интерфейса аутентификации
 */
public class LoginServiceImpl implements LoginService {
    private static LoginDao loginDao = new LoginDaoImpl();

    @Override
    public boolean checkAuth(String login, String password) {
        Login loginPojo = loginDao.findLoginByName(login);
        return (loginPojo != null) && (Long.toString(loginPojo.getHashPassword()).equals(password));
    }

    @Override
    public Integer getRole(String login) {
        Login loginPojo = loginDao.findLoginByName(login);
        if (loginPojo == null) {
            return 0;
        }
        return loginPojo.getPremissionGroup();
    }
}
