package ru.innopolis.stc9.controllers;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import ru.innopolis.stc9.service.AttendanceService;
import ru.innopolis.stc9.service.GroupService;
import ru.innopolis.stc9.service.UserService;

@Controller
public class AttendanceController {
    private static Logger logger = Logger.getLogger(AttendanceController.class);
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
        model.addAttribute("studentsInGroup", userService.getStudentsByGroupId(groupId));
        return defaultPath;
    }

    @RequestMapping(value = "/university/teacher/attendanceSendStudentsList", method = RequestMethod.POST)
    private String sendStudentsList(@RequestParam("list") int[] studentsList, @RequestParam("lessonId") int lessonId, Model model) {
        for (int i = 0; i < studentsList.length; i++) {
            logger.info("student id " + studentsList[i]);
        }
        logger.info("lesson id " + lessonId);
        attendanceService.addLessonAttendance(lessonId, studentsList);
        return defaultPath;
    }
}

