package ru.innopolis.stc9.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import ru.innopolis.stc9.pojo.Lessons;
import ru.innopolis.stc9.service.interfaces.LessonsService;
import ru.innopolis.stc9.service.interfaces.SubjectService;

@Controller
@RequestMapping(value = "/university/teacher/lessons")
public class LessonsController {
    private final LessonsService lessonsService;
    private final SubjectService subjectService;

    @Autowired
    public LessonsController(LessonsService lessonsService, SubjectService subjectService) {
        this.lessonsService = lessonsService;
        this.subjectService = subjectService;
    }

    /**
     * выводим список уроков
     */
    @RequestMapping(method = RequestMethod.GET)
    private String doGet(Model model) {
        model.addAttribute("lessons", lessonsService.findAllLessons());
        model.addAttribute("subjects", subjectService.findAllSubject());
        return "views/lessons";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String addLessonsMethodPost(@RequestParam(value = "add_subject_id", required = false) int subject_id,
                                       @RequestParam(value = "add_date", required = false) String strDate,
                                       @RequestParam(value = "add_name", required = false) String name,
                                       Model model) {
        Lessons lessons = new Lessons(subject_id, lessonsService.stringToDate(strDate), name);
        lessonsService.addLesson(lessons);
        return doGet(model);
    }

    /**
     * Удаляем урок по идентификатору
     *
     * @param id идентификатор урока
     */
    @RequestMapping(value = "/delete")
    private String delLessonsMethodPost(@RequestParam("idLesson") int id, Model model) {
        if (id > 0) {
            lessonsService.deleteLesson(id);
        }
        return doGet(model);
    }
}
