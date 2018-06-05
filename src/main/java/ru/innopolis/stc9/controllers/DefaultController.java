package ru.innopolis.stc9.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
public class DefaultController {

    @RequestMapping
    public String defUrlRedirect(HttpSession session) {
        if (session.getAttribute("entered_user_id") == null) return "redirect:/login";
        else return "redirect:/university/start";
    }
}
