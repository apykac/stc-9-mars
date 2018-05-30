package ru.innopolis.stc9.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ru.innopolis.stc9.service.ProgressService;
import ru.innopolis.stc9.service.UserService;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping(value = "/views/progress")
public class ProgressController {
    @Autowired
    private ProgressService progressService;
    @Autowired
    private UserService userService;

    @RequestMapping(method = RequestMethod.GET)
    private String doGet(HttpSession session, Model model) {
        String login = (String) session.getAttribute("login");
        model.addAttribute("progress",
                progressService.getProgress(0, 5, userService.findUserByLogin(login)));
        return "views/progress";
    }
}
