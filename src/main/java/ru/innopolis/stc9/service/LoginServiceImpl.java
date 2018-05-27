package ru.innopolis.stc9.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.innopolis.stc9.dao.LoginDao;
import ru.innopolis.stc9.dao.LoginDaoImpl;
import ru.innopolis.stc9.pojo.Login;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by aldar on 24.05.2018.
 * Реализация интерфейса аутентификации
 */
@Service
public class LoginServiceImpl implements LoginService {
    @Autowired
    private static LoginDao loginDao = new LoginDaoImpl();
    private String userName = "userName";
    private String hashPss = "hash_password";

    public List<String> isCorrectData(Map<String, String[]> incParam) {
        List<String> result = new ArrayList<>();
        if (incParam == null || incParam.isEmpty()) return result;
        if ((incParam.get(userName) != null) && incParam.get(userName)[0].equals(""))
            result.add("Invalid/Exist Login");
        if (incParam.get(hashPss) != null && incParam.get(hashPss)[0].equals(""))
            result.add("Invalid password");
        return result;
    }

    @Override
    public boolean checkAuth(String login, String password) {
        if ((login == null) || (password == null) || login.isEmpty() || password.isEmpty()) return false;
        Login loginPojo = loginDao.findLoginByName(login);
        return (loginPojo != null) && (CryptService.isMatched(password, loginPojo.getHashPassword()));
    }

    @Override
    public boolean isExist(String login) {
        if ((login == null) || login.equals("")) return false;
        return loginDao.findLoginByName(login) != null;
    }

    @Override
    public Integer getRole(String login) {
        if ((login == null) || login.isEmpty()) return -1;
        Login loginPojo = loginDao.findLoginByName(login);
        if (loginPojo == null) {
            return 0;
        }
        return loginPojo.getPermissionGroup();
    }

    @Override
    public boolean addLogin(Map<String, String[]> incParam, Integer userId) {
        return loginDao.addLogin(
                new Login(incParam.get(userName)[0],
                        CryptService.crypting(incParam.get(hashPss)[0]),
                        userId)
        );
    }
}
