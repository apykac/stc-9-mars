package ru.innopolis.stc9.service.implementation;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import ru.innopolis.stc9.pojo.Group;
import ru.innopolis.stc9.pojo.User;
import ru.innopolis.stc9.service.interfaces.GroupService;
import ru.innopolis.stc9.service.interfaces.StudentService;
import ru.innopolis.stc9.service.interfaces.UserService;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class StudentServiceImpl implements StudentService {
    private static Logger logger = Logger.getLogger(StudentServiceImpl.class);
    GroupService groupService;
    UserService userService;

    @Autowired
    public StudentServiceImpl(GroupService groupService, UserService userService) {
        this.groupService = groupService;
        this.userService = userService;
    }

    @Override
    public boolean isDuplicate(List<Group> groupsList, Model model, String name) {
        if ((groupsList == null) || (model == null) || (name == null) || name.equals(""))
            return false;
        for (Group g : groupsList) {
            if (g.getName().equals(name)) {
                model.addAttribute("errorName", "Такое имя группы уже используется. Введите другое");
                logger.info("duplicate names to adding ");
                return true;
            }
        }
        return false;
    }

    @Override
    public void addingMainAttributeToModel(Model model, int id, Integer filterId) {
        if ((model == null) || (id < 0) || ((filterId != null) && (filterId < 0)))
            return;
        List<Group> allGroupsList = groupService.findAllGroups();
        List<User> allStudentsList = userService.getAllStudents();
        distributionStudentsByGroup(allGroupsList, allStudentsList);
        model.addAttribute("groups", allGroupsList);
        model.addAttribute("groupName", findNameById(allGroupsList, id));
        model.addAttribute("id", id);
        model.addAttribute("students", getStudentsByGroupId(allStudentsList, id));
        model.addAttribute("studentsWOG", studentFilter(allStudentsList, filterId, id));
    }

    @Override
    public List<User> studentFilter(List<User> studentsList, Integer filterId, int currentGroupId) {
        List<User> list = new ArrayList<>();
        if ((studentsList == null) || studentsList.isEmpty())
            return list;
        if (((filterId != null) && (filterId < 0)) || (currentGroupId < 0))
            return list;
        for (User u : getAllStudentExceptId(studentsList, currentGroupId))
            if (filterId == null) {
                if (u.getGroupId() == null) list.add(u);
            } else {
                if (filterId.equals(u.getGroupId())) list.add(u);
            }
        return list;
    }

    @Override
    public String findNameById(List<Group> groupsList, int id) {
        if ((groupsList == null) || (id < 0)) return "";
        for (Group group : groupsList)
            if (group.getId() == id)
                return group.getName();
        return "";
    }

    @Override
    public List<User> getStudentsByGroupId(List<User> studentsList, Integer groupId) {
        List<User> allStudentsByGroupId = new ArrayList<>();
        if ((studentsList == null) || studentsList.isEmpty() || ((groupId != null) && groupId < 0))
            return allStudentsByGroupId;
        for (User student : studentsList)
            if (groupId == null) {
                if (student.getGroupId() == null)
                    allStudentsByGroupId.add(student);
            } else if (groupId.equals(student.getGroupId()))
                allStudentsByGroupId.add(student);
        return allStudentsByGroupId;
    }

    @Override
    public List<User> getAllStudentExceptId(List<User> studentsList, Integer groupId) {
        List<User> allStudentsExceptId = new ArrayList<>();
        if ((studentsList == null) || studentsList.isEmpty() || ((groupId != null) && groupId < 0))
            return allStudentsExceptId;
        for (User student : studentsList)
            if (groupId == null) {
                if (student.getGroupId() != null)
                    allStudentsExceptId.add(student);
            } else if (!groupId.equals(student.getGroupId()))
                allStudentsExceptId.add(student);
        return allStudentsExceptId;
    }

    @Override
    public void distributionStudentsByGroup(List<Group> groupsList, List<User> studentsList) {
        if ((groupsList == null) || groupsList.isEmpty())
            return;
        if ((studentsList == null) || studentsList.isEmpty())
            return;
        for (Group group : groupsList) {
            Integer groupId = group.getId();
            for (User student : studentsList)
                if (groupId.equals(student.getGroupId())) student.setGroup(group);
        }
    }
}
