package ru.innopolis.stc9.service.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import ru.innopolis.stc9.pojo.Group;
import ru.innopolis.stc9.pojo.User;
import ru.innopolis.stc9.service.interfaces.GroupService;
import ru.innopolis.stc9.service.interfaces.StudentService;
import ru.innopolis.stc9.service.interfaces.UserService;

import java.util.ArrayList;
import java.util.List;

@Component
public class StudentServiceImpl implements StudentService {
    @Autowired
    GroupService groupService;
    @Autowired
    UserService userService;

    public void addingMainAttributeToModel(Model model, int id, Integer filterId) {
        List<Group> allGroupsList = groupService.findAllGroups();
        List<User> allStudentsList = userService.getAllStudents();
        distributionStudentsByGroup(allGroupsList, allStudentsList);
        model.addAttribute("groups", allGroupsList);
        model.addAttribute("groupName", findNameById(allGroupsList, id));
        model.addAttribute("id", id);
        model.addAttribute("students", getStudentsByGroupId(allStudentsList, id));
        model.addAttribute("studentsWOG", studentFilter(allStudentsList, filterId, id));
    }

    private List<User> studentFilter(List<User> studentsList, Integer filterId, int currentGroupId) {
        List<User> list = new ArrayList<>();
        for (User u : getAllStudentExceptId(studentsList, currentGroupId))
            if (filterId == null) {
                if (u.getGroupId() == null) list.add(u);
            } else {
                if (filterId.equals(u.getGroupId())) list.add(u);
            }
        return list;
    }

    private String findNameById(List<Group> groupsList, int id) {
        for (Group group : groupsList) if (group.getId() == id) return group.getName();
        return "";
    }

    private List<User> getStudentsByGroupId(List<User> studentsList, Integer groupId) {
        List<User> allStudentsByGroupId = new ArrayList<>();
        for (User student : studentsList)
            if (groupId.equals(student.getGroupId()))
                allStudentsByGroupId.add(student);
        return allStudentsByGroupId;
    }

    private List<User> getAllStudentExceptId(List<User> studentsList, Integer groupId) {
        List<User> allStudentsExceptId = new ArrayList<>();
        for (User student : studentsList)
            if (!groupId.equals(student.getGroupId()))
                allStudentsExceptId.add(student);
        return allStudentsExceptId;
    }

    private void distributionStudentsByGroup(List<Group> groupsList, List<User> studentsList) {
        for (Group group : groupsList) {
            Integer groupId = group.getId();
            for (User student : studentsList)
                if (groupId.equals(student.getGroupId())) student.setGroup(group);
        }
    }
}
