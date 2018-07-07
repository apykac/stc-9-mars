package ru.innopolis.stc9.controllers;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import ru.innopolis.stc9.service.interfaces.SubjectService;

@Controller
@RequestMapping(value = "/university/teacher/subject")
public class SubjectController {
    private final Logger logger = Logger.getLogger(SubjectController.class);
    private final SubjectService subjectService;

    @Autowired
    public SubjectController(SubjectService subjectService) {
        this.subjectService = subjectService;
    }

    /**
     * Выводим список предметов
     */
    @RequestMapping(method = RequestMethod.GET)
    private String doGet(Model model) {
        model.addAttribute("subjects", subjectService.findAllSubject());
        return "views/subject";
    }

    /**
     * Добавляем название предмета, проверяем на совпадение
     *
     * @param name название предмета
     */
    @RequestMapping(method = RequestMethod.POST)
    public String addSubjectMethodPost(@RequestParam(value = "name", required = false) String name, Model model) {
        if (subjectService.checkSubjectName(name)) {
            model.addAttribute("errorName", "Предмет (" + name + ") присутствует в списке предметов");
        } else {
            subjectService.addSubject(name);
            logger.info("subject (" + name + ") added");
        }
        return doGet(model);
    }

    /**
     * Удаляем предмет по идентификатору
     *
     * @param id идентификатор предмета
     */
    @RequestMapping(value = "/delete")
    private String delSubjectMethodPost(@RequestParam("idSubj") int id, Model model) {
        if (id > 0) {
            subjectService.deleteSubject(id);
            logger.info("subject " + id + " deleted");
        }
        return doGet(model);
    }

}