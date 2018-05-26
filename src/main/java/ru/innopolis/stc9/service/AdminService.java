package ru.innopolis.stc9.service;

import org.apache.log4j.Logger;
import ru.innopolis.stc9.controllers.GroupController;
import ru.innopolis.stc9.dao.LoginDaoImpl;
import ru.innopolis.stc9.dao.UserDaoImpl;
import ru.innopolis.stc9.pojo.Login;
import ru.innopolis.stc9.pojo.User;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class AdminService {
    private final Logger logger = Logger.getLogger(GroupController.class);
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

    public String editUser(HttpServletRequest req) {
        String result = "";
        int userId = (Integer) getSessionAttribute(req, "user-id");
        User user = getUserFromHttpRequest(req);
        Login login = null;
        String oldPassword = req.getParameter("oldPassword");
        String newPassword = req.getParameter("newPassword");
        String repeatNewPassword = req.getParameter("repeatNewPassword");
        if (oldPassword != null && !oldPassword.isEmpty()) {
            if (CryptService.isMatched(oldPassword, loginDao.findLoginByUserId(userId).getHashPassword())) {
                if (checkNewPassword(newPassword, repeatNewPassword)) {
                    login = getLoginFromResultSet(req);
                }
            }
        }
        String userUpdateMsg = "";
        String loginUpdateMsg = "";
        if (userDao.updateUser(userId, user)) {
            userUpdateMsg = "updated";
        }
        if (login != null) {
            if (loginDao.updateLogin(login)) {
                loginUpdateMsg = "updated";
            }
        }
        return "?user-id=" + userId + "&user-edit=" + userUpdateMsg + "&login-edit=" + loginUpdateMsg;
    }

    private User getUserFromHttpRequest(HttpServletRequest req) {
        User result = new User();
        String userId = req.getParameter("userId");
        logger.info("userId " + userId);
        String firstName = req.getParameter("editFirstName");
        logger.info("firstName " + firstName);
        String lastName = req.getParameter("editLastName");
        String middleName = req.getParameter("editMiddleName");
        result.setFirstName(firstName);
        result.setSecondName(lastName);
        result.setMiddleName(middleName);
        result.setId(Integer.parseInt(userId));
        return result;
    }

    private Login getLoginFromResultSet(HttpServletRequest req) {
        Login result = new Login();
        String userName = req.getParameter("editUsername");
        String password = req.getParameter("newPassword");
        String hashPassword = CryptService.crypting(password);
        int role = Integer.parseInt(req.getParameter("editRole"));
        int userId = Integer.parseInt(req.getParameter("userId"));
        int id = loginDao.findLoginByUserId(userId).getId();
        result.setId(id);
        result.setUserName(userName);
        result.setHashPassword(hashPassword);
        result.setPermissionGroup(role);
        result.setUserId(userId);
        return result;
    }

    private boolean checkNewPassword(String newPassword, String repeatNewPassword) {
        boolean result = false;
        if (newPassword != null && !newPassword.isEmpty()) {
            if (repeatNewPassword.equals(newPassword)) {
                result = true;
            }
        }
        return result;
    }

    private Object getSessionAttribute(HttpServletRequest req, String attribute) {
        Object result = null;
        if (req.getSession().getAttribute(attribute) != null) {
            result = req.getSession().getAttribute(attribute);
            req.getSession().setAttribute(attribute, null);
        }
        return result;
    }
}
