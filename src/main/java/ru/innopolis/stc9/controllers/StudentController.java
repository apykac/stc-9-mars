package ru.innopolis.stc9.controllers;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.innopolis.stc9.pojo.Lessons;
import ru.innopolis.stc9.service.interfaces.LessonsService;

import java.util.List;

/**
 * Created by Сергей on 01.06.2018.
 */
@Controller
public class StudentController {
    private Logger logger = Logger.getLogger(HomeWorkController.class);
    private LessonsService lessonsService;

    @Autowired
    public StudentController(LessonsService lessonsService) {
        this.lessonsService = lessonsService;
    }

    @RequestMapping("/university/student/subject/{subjectId}")
    public String viewLessonForSubject(@PathVariable("subjectId") int subjectId, Model model) {
        List<Lessons> lessons = lessonsService.findAllLessonsByWithSubject(subjectId);
        model.addAttribute("lessons", lessons);
        logger.info("view lessons for subject id= " + subjectId);
        return "views/studentLessons";
    }
}
