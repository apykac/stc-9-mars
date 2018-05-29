package ru.innopolis.stc9.controllers;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import ru.innopolis.stc9.pojo.Subject;
import ru.innopolis.stc9.service.SubjectService;
import ru.innopolis.stc9.service.SubjectServiceImpl;

@Controller
@RequestMapping(value = "/views/subject")
public class SubjectController {
    private final Logger logger = Logger.getLogger(GroupController.class);
    @Autowired
    private final SubjectService subjectService = new SubjectServiceImpl();

    @RequestMapping(method = RequestMethod.GET)
    private String doGet(Model model) {
        model.addAttribute("subjects", subjectService.findAllSubject());
        return "views/subject";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String addSubjectMethodPost(@RequestParam(value = "name", required = false) String name, Model model) {
        Subject subject = new Subject(name);
        subjectService.addSubject(subject);
        logger.info("subject (" + name + ") added");
        return doGet(model);
    }

    @RequestMapping(value = "/views/subject/del")
    private String delSubjectMethodPost(@RequestParam int id, Model model) {
        subjectService.deleteSubject(id);
        logger.info("subject " + id + " deleted");
        return doGet(model);
    }

}