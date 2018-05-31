package ru.innopolis.stc9.controllers;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ru.innopolis.stc9.service.UserService;
import ru.innopolis.stc9.service.UserServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/registration")
public class RegistrationController {
    private static Logger logger = Logger.getLogger(RegistrationController.class);
    private String regPath = "registration";
    @Autowired
    private UserService userService;

    @RequestMapping(method = RequestMethod.GET)
    public String infoHandlerOfGet() {
        return regPath;
    }

    @RequestMapping(method = RequestMethod.POST)
    public String registrationFormHandler(@RequestBody MultiValueMap<String, String> incParam, Model model) {
        List<String> errorList = new ArrayList<>();
        if (userService.isExist(incParam.get("login").get(0))) errorList.add("Login is Exist");
        else errorList = userService.isCorrectData(incParam);
        if (!errorList.isEmpty()) {
            model.addAttribute("errorMsg", errorList);
            return regPath;
        }
        userService.addUserByParam(incParam);
        return "redirect:/login?registration=true";
    }
}
