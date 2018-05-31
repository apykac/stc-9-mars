package ru.innopolis.stc9.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import ru.innopolis.stc9.pojo.Mark;
import ru.innopolis.stc9.service.MarkService;
import ru.innopolis.stc9.service.MarkServiceImpl;
import ru.innopolis.stc9.service.UserService;
import ru.innopolis.stc9.service.UserServiceImpl;

@Controller
public class MarksController {
    @Autowired
    private final MarkService markService = new MarkServiceImpl();
    private final UserService userService = new UserServiceImpl();

    @RequestMapping(value = "/views/marks", method = RequestMethod.GET)
    private String getMarks(@RequestParam int lessonId, Model model) {
        model.addAttribute("marks", markService.getMarksByLessonId(lessonId));
        model.addAttribute("lessonName");
        return "views/marks";
    }

    @RequestMapping("/views/editMark/{id}")
    public String forMarkUpdate(@PathVariable("id") int id, Model model) {
        model.addAttribute("studentName", markService.getFullStudentNameInOneString(id));
        //model.addAttribute("lessonName", markService.getLessonName(id));
        model.addAttribute("value", markService.getMarkById(id).getValue());
        model.addAttribute("comment", markService.getMarkById(id).getComment());
        model.addAttribute("id", id);
        return "views/editMark";
    }

    @RequestMapping("/views/updateMark")
    public ModelAndView updateMark(@RequestParam("id") int id, @RequestParam("value") int value, @RequestParam("comment") String comment, Model model) {
        Mark mark = markService.getMarkById(id);
        mark.setValue(value);
        mark.setComment(comment);
        markService.updateMark(mark);

        return new ModelAndView("redirect:/views/marks?lessonId=" + mark.getLessonId());
    }
}

