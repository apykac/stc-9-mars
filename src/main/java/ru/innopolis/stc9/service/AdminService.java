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

    private boolean passwordUpdateIsPossible;

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

    public String editUser(HttpServletRequest req) {
        String userUpdateMsg = "";
        String loginUpdateMsg = "";
        String passwordUpdateMsg = "";
        String result = "";
        int userId = (Integer) getSessionAttribute(req, "user-id");
        User user = getUserFromHttpRequest(req);
        int role = Integer.parseInt(req.getParameter("editRole"));
        Login login = loginDao.findLoginByUserId(userId);
        login.setPermissionGroup(role);
        if (userDao.updateUser(userId, user)) {
            userUpdateMsg = "updated";
        } else {
            userUpdateMsg = "error";
        }
        if (loginDao.updateLogin(login)) {
            loginUpdateMsg = "updated";
        } else {
            loginUpdateMsg = "error";
        }
        if (req.getParameter("oldPassword") != null && !req.getParameter("oldPassword").isEmpty()) {
            passwordUpdateMsg = checkPasswordUpdateIsPossible(req);
            logger.info(passwordUpdateMsg);
            if (passwordUpdateIsPossible) {
                login = getLoginFromHttpRequest(req);
                if (loginDao.updateLogin(login)) {
                } else {
                    passwordUpdateMsg = "error";
                }
            }
        }
        return "?user-id=" + userId + "&user-msg=" + userUpdateMsg + "&login-msg=" + loginUpdateMsg + "&pass-msg=" + passwordUpdateMsg;
    }

    private Login getLoginFromHttpRequest(HttpServletRequest req) {
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

    private String checkPasswordUpdateIsPossible(HttpServletRequest req) {
        passwordUpdateIsPossible = false;
        String result = "";
        int userId = Integer.parseInt(req.getParameter("userId"));
        String oldPassword = req.getParameter("oldPassword");
        String newPassword = req.getParameter("newPassword");
        String repeatNewPassword = req.getParameter("repeatNewPassword");
        if (oldPassword != null && !oldPassword.isEmpty()) {
            if (CryptService.isMatched(oldPassword, loginDao.findLoginByUserId(userId).getHashPassword())) {
                if (checkNewPassword(newPassword, repeatNewPassword)) {
                    passwordUpdateIsPossible = true;
                    result = "updated";
                } else {
                    result = "passwords-not-match";
                    //result = "Ошибка обновления пароля. Введенные пароли не совпадают.";
                }
            } else {
                result = "wrong-old-password";
                //result = "Ошибка обновления пароля. Старый пароль указан неверно.";

            }
        }
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

    public String getUserUpdateMessage(HttpServletRequest req) {
        String result = "";
        String userMsg = req.getParameter("user-msg");
        if (userMsg != null) {
            switch (userMsg) {
                case "updated":
                    result = "Ф.И.О. пользователя успешно обновлены";
                    break;
                case "error":
                    result = "Ошибка. Невозможно обновить Ф.И.О. пользователя";
                    break;
                default:
                    break;
            }
        }
        return result;
    }

    public String getLoginUpdateMessage(HttpServletRequest req) {
        String result = "";
        String loginMsg = req.getParameter("login-msg");
        if (loginMsg != null) {
            switch (loginMsg) {
                case "updated":
                    result = "Учетные данные пользователя успешно обновлены";
                    break;
                case "error":
                    result = "Ошибка. Невозможно обновить учетные даные пользователя.";
                    break;
                default:
                    break;
            }
        }
        return result;
    }

    public String getPasswordUpdateMessage(HttpServletRequest req) {
        String result = "";
        String userMsg = req.getParameter("pass-msg");
        if (userMsg != null) {
            switch (userMsg) {
                case "updated":
                    result = "Пароль пользователя успешно обновлен";
                    break;
                case "error":
                    result = "Ошибка. Невозможно обновить пароль пользователя";
                    break;
                case "passwords-not-match":
                    result = "Ошибка. Введенные пароли не совпадают.";
                    break;
                case "wrong-old-password":
                    result = "Ошибка. Старый пароль указан неверно.";
                    break;
                default:
                    break;
            }
        }
        return result;
    }
}
