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

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login() {
        return "login";
    }

    @RequestMapping(value = {"/start", "/university/start"}, method = RequestMethod.GET)
    public String startPage(HttpSession session) {
        if (session.getAttribute(SessionDataInform.ID) == null) {
            User user = userService.findUserByLogin(SecurityContextHolder.getContext().getAuthentication().getName());
            session.setAttribute(SessionDataInform.ID, user.getId());
            session.setAttribute(SessionDataInform.LOGIN, user.getLogin());
            session.setAttribute(SessionDataInform.NAME, user.getFirstName() + " " + user.getSecondName());
            session.setAttribute(SessionDataInform.ROLE, user.getPermissionGroup());
            logger.info("User: [" + user.getId() + "] " + user.getLogin() + " is login");
        }
        return "views/startPage";
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logout(HttpSession session) {
        if (session.getAttribute(SessionDataInform.ID) != null) {
            logger.info("User: [" + session.getAttribute(SessionDataInform.ID) + "] "
                    + session.getAttribute(SessionDataInform.LOGIN) + " is logout");
            session.removeAttribute(SessionDataInform.ID);
            session.removeAttribute(SessionDataInform.LOGIN);
            session.removeAttribute(SessionDataInform.NAME);
            session.removeAttribute(SessionDataInform.ROLE);
        }
        return "redirect:/j_spring_security_logout";
    }


}
