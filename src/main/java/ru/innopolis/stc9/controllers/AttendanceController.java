package ru.innopolis.stc9.controllers;

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

    @Autowired
    private GroupService groupService;
    @Autowired
    private UserService userService;
    @Autowired
    private AttendanceService attendanceService;

    //private int lesson;

    @RequestMapping(value = "/views/attendance", method = RequestMethod.GET)
    private String getGroups(@RequestParam int lessonId, Model model) {
        model.addAttribute("lessonId", lessonId);
        model.addAttribute("groups", groupService.findAllGroups());
        return "views/attendance";
    }

    @RequestMapping(value = "/views/attendanceSelectGroup")
    private String editAttendance(@RequestParam("selectGroup") int groupId, @RequestParam("lessonId") int lessonId, Model model) {
        model.addAttribute("lessonId", lessonId);
        model.addAttribute("groupSelected", groupService.findGroupById(groupId));
        model.addAttribute("studentsInGroup", userService.getStudentsByGroupId(groupId));
        return "views/attendance";
    }

    @RequestMapping(value = "/views/attendanceSendStudentsList", method = RequestMethod.POST)
    private String sendStudentsList(@RequestParam("list") int[] studentsList, @RequestParam("lessonId") int lessonId, Model model) {
        //int[] students = new int[studentsList.length];
        for (int i = 0; i < studentsList.length; i++) {
            System.out.println("student id " + studentsList[i]);
        }
        System.out.println("lesson id " + lessonId);
        attendanceService.addLessonAttendance(lessonId, studentsList);
        return "views/attendance";
    }
}

