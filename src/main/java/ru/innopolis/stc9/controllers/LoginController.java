package ru.innopolis.stc9.controllers;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

@Controller
public class LoginController {
    private static Logger logger = Logger.getLogger(LoginController.class);

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login() {
        return "loginPage";
    }

    @RequestMapping(value = "/university/start", method = RequestMethod.GET)
    public String startPage(@RequestParam(value = "message", required = false) String isDeleted, Model model) {
        model.addAttribute("message", isDeleted);
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
            session.removeAttribute(SessionDataInform.MSG);
        }
        return "redirect:/j_spring_security_logout";
    }


}
