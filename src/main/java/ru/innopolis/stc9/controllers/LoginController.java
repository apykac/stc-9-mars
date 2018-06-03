package ru.innopolis.stc9.controllers;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ru.innopolis.stc9.pojo.User;
import ru.innopolis.stc9.service.UserService;

import javax.servlet.http.HttpSession;

@Controller
public class LoginController {
    private static Logger logger = Logger.getLogger(LoginController.class);
    private static String userId = "entered_user_id";
    private static String login = "entered_login";
    private static String name = "entered_name";
    private static String role = "entered_role";
    @Autowired
    private UserService userService;

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login() {
        return "login";
    }

    @RequestMapping(value = "/start", method = RequestMethod.GET)
    public String startPage(HttpSession session) {
        if (session.getAttribute(userId) == null) {
            User user = userService.findUserByLogin(SecurityContextHolder.getContext().getAuthentication().getName());
            session.setAttribute(userId, user.getId());
            session.setAttribute(login, user.getLogin());
            session.setAttribute(name, user.getFirstName() + " " + user.getSecondName());
            session.setAttribute(role, user.getPermissionGroup());
            logger.info("User: [" + user.getId() + "] " + user.getLogin() + " is login");
        }

        return "views/startPage";
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logout(HttpSession session) {
        if (session.getAttribute(userId) != null) {
            logger.info("User: [" + session.getAttribute(userId) + "] " + session.getAttribute(login) + " is logout");
            session.removeAttribute(userId);
            session.removeAttribute(login);
            session.removeAttribute(name);
            session.removeAttribute(role);
        }
        return "redirect:/j_spring_security_logout";
    }
}
