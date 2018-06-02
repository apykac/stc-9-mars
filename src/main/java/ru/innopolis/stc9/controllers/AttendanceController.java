package ru.innopolis.stc9.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import ru.innopolis.stc9.service.GroupService;
import ru.innopolis.stc9.service.UserService;

@Controller
public class AttendanceController {

    @Autowired
    private GroupService groupService;
    @Autowired
    private UserService userService;

    @RequestMapping(value = "/views/attendance", method = RequestMethod.GET)
    private String getGroups(@RequestParam int lessonId, Model model) {
        model.addAttribute("lessonIdSelected", lessonId);
        model.addAttribute("groups", groupService.findAllGroups());
        return "views/attendance";
    }

    @RequestMapping(value = "/views/attendanceSelectGroup")
    private String editAttendance(@RequestParam("selectGroup") int groupId, Model model) {
        model.addAttribute("groupSelected", groupService.findGroupById(groupId));
        model.addAttribute("studentsInGroup", userService.getStudentsByGroupId(groupId));
        return "views/attendance";
    }
}

