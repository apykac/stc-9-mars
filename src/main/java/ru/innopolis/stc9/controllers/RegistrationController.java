package ru.innopolis.stc9.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ru.innopolis.stc9.service.interfaces.UserService;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/registration")
public class RegistrationController {
    private String regPath = "registrationPage";
    private UserService userService;

    @Autowired
    public RegistrationController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public String infoHandlerOfGet() {
        return regPath;
    }

    @RequestMapping(method = RequestMethod.POST)
    public String registrationFormHandler(@RequestBody MultiValueMap<String, String> incParam, Model model) {
        List<String> errorList = new ArrayList<>();
        if (userService.isExist(incParam.get("login").get(0)))
            errorList.add("Login is Exist");
        else errorList = userService.isCorrectData(incParam);
        if (!errorList.isEmpty()) {
            model.addAttribute("errorMsg", errorList);
            return regPath;
        }
        userService.addUserByParam(incParam);
        return "redirect:/login?registration=true";
    }
}
