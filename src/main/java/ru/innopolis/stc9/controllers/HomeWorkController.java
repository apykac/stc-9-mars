package ru.innopolis.stc9.controllers;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.innopolis.stc9.pojo.HomeWork;
import ru.innopolis.stc9.pojo.User;
import ru.innopolis.stc9.service.HomeWorkService;
import ru.innopolis.stc9.service.UserService;

/**
 * Created by Сергей on 01.06.2018.
 */
@Controller
public class HomeWorkController {
    @Autowired
    private HomeWorkService homeWorkService;
    @Autowired
    private UserService userService;
    private Logger logger = Logger.getLogger(HomeWorkController.class);
    private String addHomeWork = "views/addHomeWork";


    @RequestMapping("views/homework/lessonId/{lessonId}")
    public String viewHomeWorkForm(@PathVariable("lessonId") int lessonId, Model model) {
        model.addAttribute("lessonId", lessonId);
        return addHomeWork;
    }

    @RequestMapping("views/homework/add")
    public String addHomeWork(@RequestParam("url") String url, @RequestParam("lessonId") int lessonId,  Model model) {
        if(url.length() > 200) {
            model.addAttribute("error", "Слишком длинная ссылка! Сократите");
            model.addAttribute("lessonId", lessonId);
            return addHomeWork;
        }
        if(url.isEmpty()) {
            model.addAttribute("error", "Кажется вы пытаетесь отправить пустоту)");
            model.addAttribute("lessonId", lessonId);
            return addHomeWork;
        }
        org.springframework.security.core.userdetails.User activeUser =
                (org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        homeWorkService.addHomeWork(new HomeWork(url, userService.findUserByLogin(activeUser.getUsername()).getId(), lessonId));
        model.addAttribute("lessonId", lessonId);
        logger.info("Homework added");
        return "redirect: /views/student/studentDashBoard" ;
    }


}
