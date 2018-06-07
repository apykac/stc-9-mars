package ru.innopolis.stc9.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import ru.innopolis.stc9.service.interfaces.AttendanceService;
import ru.innopolis.stc9.service.interfaces.GroupService;
import ru.innopolis.stc9.service.interfaces.UserService;

@Controller
public class AttendanceController {
    private String defaultPath = "views/attendance";

    @Autowired
    private GroupService groupService;
    @Autowired
    private UserService userService;
    @Autowired
    private AttendanceService attendanceService;

    @RequestMapping(value = "/university/teacher/attendance", method = RequestMethod.GET)
    private String getGroups(@RequestParam int lessonId, Model model) {
        model.addAttribute("lessonId", lessonId);
        model.addAttribute("groups", groupService.findAllGroups());
        return defaultPath;
    }

    @RequestMapping(value = "/university/teacher/attendanceSelectGroup")
    private String editAttendance(@RequestParam("selectGroup") int groupId, @RequestParam("lessonId") int lessonId, Model model) {
        model.addAttribute("lessonId", lessonId);
        model.addAttribute("groupSelected", groupService.findGroupById(groupId));
        model.addAttribute("groups", groupService.findAllGroups());
        model.addAttribute("studentsInGroup", userService.getStudentsByGroupId(groupId));
        model.addAttribute("savedAttendance", attendanceService.getLessonAttendance(lessonId, groupId));
        return defaultPath;
    }

    @RequestMapping(value = "/university/teacher/attendanceSendStudentsList", method = RequestMethod.POST)
    private String sendStudentsList(@RequestParam(value = "list", required = false) int[] studentsList, @RequestParam("lessonId") int lessonId, @RequestParam("groupSelected") int groupSelected, Model model) {
        if (studentsList == null) {
            attendanceService.clearLessonAttendance(lessonId, groupSelected);
        } else {
            attendanceService.addLessonAttendance(groupSelected, lessonId, studentsList);
        }
        model.addAttribute("lessonId", lessonId);
        model.addAttribute("groups", groupService.findAllGroups());
        String groupName = groupService.findGroupById(groupSelected).getName();
        model.addAttribute("message", "Список присутствовавших студентов по группе " + groupName + " обновлен.");
        return defaultPath;
    }
}

