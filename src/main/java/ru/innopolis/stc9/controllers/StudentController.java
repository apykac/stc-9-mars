package ru.innopolis.stc9.controllers;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.innopolis.stc9.pojo.Lessons;
import ru.innopolis.stc9.pojo.Subject;
import ru.innopolis.stc9.pojo.User;
import ru.innopolis.stc9.service.GroupService;
import ru.innopolis.stc9.service.LessonsService;
import ru.innopolis.stc9.service.SubjectService;
import ru.innopolis.stc9.service.UserService;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Сергей on 01.06.2018.
 */
@Controller
public class StudentController {
    @Autowired
    private UserService userService;
    @Autowired
    private GroupService groupService;
    @Autowired
    private SubjectService subjectService;
    @Autowired
    private LessonsService lessonsService;
    private Logger logger = Logger.getLogger(HomeWorkController.class);
    private String studentPage = "views/studentPage";
    private String studentLessons = "views/studentLessons";

    @RequestMapping("/university/student/studentDashBoard")
    public String studentView(Model model) {
        org.springframework.security.core.userdetails.User activeUser =
                (org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User student = userService.findUserByLogin(activeUser.getUsername());
        student.setGroup(groupService.findGroupById(student.getGroupId()));
        model.addAttribute("student", student);
        model.addAttribute("groupName", groupService.findGroupById(student.getGroupId()).getName());
       List<Subject> subjectList = subjectService.findByGroupId(student.getGroupId());
        model.addAttribute("subject", subjectList);
        logger.info("student's dashboard done!");
        return studentPage;
    }

    @RequestMapping("/university/student/update")
    public String updateStudent(@RequestParam("userId") int userId, @RequestParam("editFirstName") String firstName,
                                @RequestParam("editLastName") String lastName,
                                @RequestParam("editMiddleName") String middleName) {
        User student = userService.findUserById(userId);
        student.setFirstName(firstName);
        student.setSecondName(lastName);
        student.setMiddleName(middleName);
        userService.updateUser(student);
        logger.info("student updated");
        return "redirect:/university/student/studentDashBoard";
    }

    @RequestMapping("/university/student/subject/{subjectId}")
    public String viewLessonForSubject(@PathVariable("subjectId") int subjectId, Model model) {
        ArrayList<Lessons> lessons = new ArrayList<>();
        for(Lessons l: lessonsService.findAllLessons()) {
            if(l.getSubject_id() == subjectId) {
                lessons.add(l);
            }
        }
        model.addAttribute("lessons", lessons);
        model.addAttribute("subject", subjectService.findById(subjectId).getName());
        logger.info("view lessons for subject id= "+ subjectId);
        return studentLessons;
    }
}
