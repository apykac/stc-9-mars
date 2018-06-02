package ru.innopolis.stc9.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(@RequestParam(value = "error", required = false) String error,
                        @RequestParam(value = "logout", required = false) String logout,
                        Model model) {
        if (error != null) {
            model.addAttribute("error", "invalid username or password");
        }
        if (logout != null) {
            model.addAttribute("msg", "logged out successfully");
        }
        return "login";
    }

    @RequestMapping(value = "/start", method = RequestMethod.GET)
    public String startPage() {
        return "views/startPage";
    }
}
