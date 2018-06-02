package ru.innopolis.stc9.service;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import ru.innopolis.stc9.controllers.GroupController;
import ru.innopolis.stc9.dao.UserDao;
import ru.innopolis.stc9.dao.UserMapper;
import ru.innopolis.stc9.pojo.User;

import java.util.List;

@Service
public class AdminServiceImpl implements AdminService {
    private final Logger logger = Logger.getLogger(GroupController.class);
    private boolean passwordUpdateIsPossible;
    @Autowired
    private UserService userService;
    @Autowired
    private UserDao userDao;

    @Override
    public List<User> getUsersList() {
        return userDao.getUsersList();
    }

    @Override
    public User getUser(int id) {
        return userDao.findUserByUserId(id);
    }

    @Override
    public List<String> editUser(MultiValueMap<String, String> incParam) {
        List<String> errors = userService.isCorrectData(incParam);
        if (!errors.isEmpty()) return errors;
        User user = UserMapper.getByParam(incParam);

        if (!userDao.updateUserByFIOL(user)) errors.add("Updating user(FIO) failed by DAO");

        if ((user.getHashPassword() != null) && !user.getHashPassword().isEmpty()) {
            String incPass = checkPasswordUpdateIsPossible(incParam, user);
            if (!incPass.equals("")) errors.add(incPass);
            if (passwordUpdateIsPossible && !userDao.updateUserPassword(user))
                errors.add("Updating user(Pass) failed by DAO");
        }

        return errors;
    }

    private String checkPasswordUpdateIsPossible(MultiValueMap<String, String> incParam, User user) {
        passwordUpdateIsPossible = false;
        String result = "";
        if (CryptService.isMatched(user.getHashPassword(), userDao.findUserByUserId(user.getId()).getHashPassword())) {
            if (checkNewPassword(incParam.get("newPassword").get(0), incParam.get("repeatNewPassword").get(0))) {
                passwordUpdateIsPossible = true;
            } else result = "passwords-not-match";
        } else result = "wrong-old-password";
        return result;
    }

    private boolean checkNewPassword(String newPassword, String repeatNewPassword) {
        return (newPassword != null) && !newPassword.isEmpty() && newPassword.equals(repeatNewPassword);
    }

    /*private User getUserFromHttpRequest(HttpServletRequest req, boolean withPassword) {
        User result = new User();
        String userId = req.getParameter("userId");
        int id = Integer.parseInt(userId);
        result.setId(id);
        if (withPassword) {
            String password = req.getParameter("newPassword");
            String hashPassword = CryptService.crypting(password);
            result.setHashPassword(hashPassword);
        } else {
            String login = req.getParameter("editLogin");
            String role = req.getParameter("editRole");
            int enabled = Integer.parseInt(req.getParameter("editEnabled"));
            String firstName = req.getParameter("editFirstName");
            String lastName = req.getParameter("editLastName");
            String middleName = req.getParameter("editMiddleName");
            result.setLogin(login);
            result.setPermissionGroup(role);
            result.setEnabled(enabled);
            result.setFirstName(firstName);
            result.setSecondName(lastName);
            result.setMiddleName(middleName);
        }
        return result;
    }*/

    /*@Override
    public String editUser(HttpServletRequest req) {
        String userUpdateMsg;
        String incPass = "";
        User user = getUserFromHttpRequest(req, false);
        int userId = (Integer) getSessionAttribute(req, "user-id");
        String role = req.getParameter("editRole");
        user.setPermissionGroup(role);

        if (userDao.updateUserByFIOL(user)) userUpdateMsg = "updated";
        else userUpdateMsg = "error";

        if (req.getParameter("oldPassword") != null && !req.getParameter("oldPassword").isEmpty()) {
            incPass = checkPasswordUpdateIsPossible(req);
            logger.info(incPass);
            if (passwordUpdateIsPossible) {
                user = getUserFromHttpRequest(req, true);
                if (!userDao.updateUserPassword(user)) incPass = "error";
            }
        }

        return "?user-id=" + userId + "&user-msg=" + userUpdateMsg + "&pass-msg=" + incPass;
    }*/

    /*private String checkPasswordUpdateIsPossible(HttpServletRequest req) {
        passwordUpdateIsPossible = false;
        String result = "";
        int userId = Integer.parseInt(req.getParameter("userId"));
        String oldPassword = req.getParameter("oldPassword");
        String newPassword = req.getParameter("newPassword");
        String repeatNewPassword = req.getParameter("repeatNewPassword");

        if ((oldPassword != null) && !oldPassword.isEmpty())
            if (CryptService.isMatched(oldPassword, userDao.findUserByUserId(userId).getHashPassword())) {
                if (checkNewPassword(newPassword, repeatNewPassword)) {
                    passwordUpdateIsPossible = true;
                    result = "updated";
                } else result = "passwords-not-match";
            } else result = "wrong-old-password";
        return result;
    }*/

    /*private Object getSessionAttribute(HttpServletRequest req, String attribute) {
        Object result = null;
        if (req.getSession().getAttribute(attribute) != null) {
            result = req.getSession().getAttribute(attribute);
            req.getSession().setAttribute(attribute, null);
        }
        return result;
    }*/

    /*@Override
    public String getUserUpdateMessage(HttpServletRequest req) {
        String result = "";
        String userMsg = req.getParameter("user-msg");
        if (userMsg != null) {
            switch (userMsg) {
                case "updated":
                    result = "Логин, Ф.И.О. пользователя успешно обновлены";
                    break;
                case "error":
                    result = "Ошибка. Невозможно обновить Логин, Ф.И.О. пользователя";
                    break;
                default:
                    break;
            }
        }
        return result;
    }*/

    /*@Override
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
    }*/
}
