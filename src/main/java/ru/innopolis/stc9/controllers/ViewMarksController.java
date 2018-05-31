package ru.innopolis.stc9.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import ru.innopolis.stc9.service.MarkService;
import ru.innopolis.stc9.service.MarkServiceImpl;

@Controller
public class ViewMarksController {
    @Autowired
    private final MarkService marksService = new MarkServiceImpl();

    @RequestMapping(value = "/views/marks", method = RequestMethod.GET)
    private String getMarks(@RequestParam int lessonId, Model model) {
        model.addAttribute("marks", marksService.getMarksByLessonId(lessonId));
        return "views/marks";
    }

    /*@RequestMapping("/views/updateMarks")
    public String updateMark(@RequestParam("id") int id, @RequestParam("value") int value, Model model) {
        Mark mark = groupService.findGroupById(id);
        group.setName(name);
        groupService.updateGroup(group);
        model.addAttribute("groups", groupService.findAllGroups());
        logger.info("group " + id + " updated");
        return "views/allGroups";
    }*/
}

