package ru.innopolis.stc9.controllers;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import ru.innopolis.stc9.service.LessonsService;
import ru.innopolis.stc9.service.LessonsServiceImpl;

@Controller
@RequestMapping(value = "/views/lessons")
public class LessonsController {
    private final Logger logger = Logger.getLogger(GroupController.class);
    @Autowired
    private final LessonsService lessonsService = new LessonsServiceImpl();

    @RequestMapping(method = RequestMethod.GET)
    private String doGet(Model model) {
        model.addAttribute("lessons", lessonsService.findAllLessons());
        return "views/lessons";
    }

/*    @RequestMapping(method = RequestMethod.POST)
    public String addLessonsMethodPost(@RequestParam(value = "name", required = false) String name, Model model) {
        Lessons lessons = new Lessons(name);
        lessonsService.addLesson(lessons);
        logger.info("lesson (" + name + ") added");
        return doGet(model);
    }*/

    @RequestMapping(value = "/delete")
    private String delLessonsMethodPost(@RequestParam("idLesson") int id, Model model) {
        if (id > 0) {
            lessonsService.deleteLesson(id);
            logger.info("lesson " + id + " deleted");
        }
        return doGet(model);
    }
}
