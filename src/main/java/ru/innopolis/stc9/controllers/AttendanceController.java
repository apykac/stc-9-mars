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

    private void addHeadAttributesToModel(Model model, long lessonId) {
        model.addAttribute("lessonId", lessonId);
        model.addAttribute("groups", groupService.findAllGroups());
    }

    @RequestMapping(value = "/university/teacher/attendance", method = RequestMethod.GET)
    private String getGroups(@RequestParam long lessonId, Model model) {
        addHeadAttributesToModel(model, lessonId);
        return defaultPath;
    }

    @RequestMapping(value = "/university/teacher/attendanceSelectGroup")
    private String editAttendance(@RequestParam("selectGroup") long groupId, @RequestParam("lessonId") int lessonId, Model model) {
        addHeadAttributesToModel(model, lessonId);
        model.addAttribute("groupSelected", groupService.findGroupById(groupId));
        model.addAttribute("studentsInGroup", userService.getStudentsByGroupId(groupId));
        model.addAttribute("savedAttendance", attendanceService.getLessonAttendance(lessonId, groupId));
        return defaultPath;
    }

    @RequestMapping(value = "/university/teacher/attendanceSendStudentsList", method = RequestMethod.POST)
    private String sendStudentsList(@RequestParam(value = "list", required = false) long[] studentsList,
                                    @RequestParam("lessonId") long lessonId,
                                    @RequestParam("groupSelected") long groupSelected,
                                    Model model) {
        addHeadAttributesToModel(model, lessonId);
        if (studentsList == null) {
            attendanceService.clearLessonAttendance(lessonId, groupSelected);
        } else {
            attendanceService.addLessonAttendance(groupSelected, lessonId, studentsList);
        }
        String groupName = groupService.findGroupById(groupSelected).getName();
        model.addAttribute("message", "Список присутствовавших студентов по группе " + groupName + " обновлен.");
        return defaultPath;
    }
}

