package ru.innopolis.stc9.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ru.innopolis.stc9.service.SubjectService;
import ru.innopolis.stc9.service.SubjectServiceImpl;

@Controller
public class SubjectController {
    @Autowired
    private final SubjectService subjectService = new SubjectServiceImpl();

    @RequestMapping(value = "/views/subject", method = RequestMethod.GET)
    private String doGet(Model model) {
        model.addAttribute("subjects", subjectService.findAllSubject());
        return "views/subject";
    }
}