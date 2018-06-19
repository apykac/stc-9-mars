package ru.innopolis.stc9.controllers;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.innopolis.stc9.pojo.Lessons;
import ru.innopolis.stc9.service.interfaces.LessonsService;
import ru.innopolis.stc9.service.interfaces.SubjectService;

import java.util.ArrayList;

/**
 * Created by Сергей on 01.06.2018.
 */
@Controller
public class StudentController {
    private Logger logger = Logger.getLogger(HomeWorkController.class);
    @Autowired
    private SubjectService subjectService;
    @Autowired
    private LessonsService lessonsService;

    @RequestMapping("/university/student/subject/{subjectId}")
    public String viewLessonForSubject(@PathVariable("subjectId") int subjectId, Model model) {
        ArrayList<Lessons> lessons = new ArrayList<>();
        for(Lessons l: lessonsService.findAllLessons()) {
            if (l.getSubjectId() == subjectId) {
                lessons.add(l);
            }
        }
        model.addAttribute("lessons", lessons);
        model.addAttribute("subject", subjectService.findById(subjectId).getName());
        logger.info("view lessons for subject id= "+ subjectId);
        return "views/studentLessons";
    }
}
