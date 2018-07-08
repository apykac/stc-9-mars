package ru.innopolis.stc9.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import ru.innopolis.stc9.pojo.HomeWork;
import ru.innopolis.stc9.pojo.Mark;
import ru.innopolis.stc9.service.interfaces.HomeWorkService;
import ru.innopolis.stc9.service.interfaces.MarkService;

import java.util.List;

@Controller
public class MarksController {
    private MarkService markService;
    private HomeWorkService homeWorkService;

    @Autowired
    public MarksController(MarkService markService, HomeWorkService homeWorkService) {
        this.markService = markService;
        this.homeWorkService = homeWorkService;
    }

    @RequestMapping(value = "/university/teacher/marks", method = RequestMethod.GET)
    private String getMarks(@RequestParam int lessonId, Model model) {
        List<Mark> marks = markService.getMarksByLessonId(lessonId);
        model.addAttribute("marks", marks);
        model.addAttribute("lessonName", marks.isEmpty() ? "" : marks.get(0).getLesson().getName());
        return "views/marks";
    }

    @RequestMapping("/university/teacher/editMark/{id}")
    public String forMarkUpdate(@PathVariable("id") int id, Model model) {
        Mark mark = markService.getMarkByIdWithFullInfo(id);
        HomeWork homeWork = homeWorkService.findHomeWorkByMarkId(id);
        model.addAttribute("mark", mark);
        model.addAttribute("homework", homeWork);
        model.addAttribute("homeWorkIsUrl", homeWorkService.homeWorkIsURL(homeWork.getHomeWorkURL()));
        return "views/editMark";
    }

    @RequestMapping("/university/teacher/updateMark")
    public ModelAndView updateMark(@RequestParam("id") int id, @RequestParam("value") int value, @RequestParam("comment") String comment, Model model) {
        Mark mark = markService.getMarkById(id);
        mark.setValue(value);
        mark.setComment(comment);
        markService.updateMark(mark);
        return new ModelAndView("redirect:/university/teacher/marks?lessonId=" + mark.getLesson().getId());
    }
}

