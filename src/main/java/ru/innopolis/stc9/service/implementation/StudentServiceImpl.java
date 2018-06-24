package ru.innopolis.stc9.service.implementation;

import org.apache.log4j.Logger;
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
    private static Logger logger = Logger.getLogger(StudentServiceImpl.class);
    @Autowired
    GroupService groupService;
    @Autowired
    UserService userService;

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
    public void addingMainAttributeToModel(Model model, int id, int filterId) {
        if ((model == null) || (id < 0) || ((filterId != 0) && (filterId < 0)))
            return;
        List<Group> allGroupsList = groupService.findAllGroups();
        List<User> allStudentsList = userService.getAllStudents();
        model.addAttribute("groups", allGroupsList);
        model.addAttribute("groupName", groupService.findGroupById(id).getName());
        model.addAttribute("id", id);
        model.addAttribute("students", groupService.findGroupById(id).getUsers());
        model.addAttribute("studentsWOG", studentFilter(allStudentsList, filterId, id));
    }

    @Override
    public List<User> studentFilter(List<User> studentList, int filterId, int currentGroupId) {
        List<User> filteredStudents = new ArrayList<>();
        if (studentList == null || studentList.isEmpty() || filterId < 0 || currentGroupId < 0) {
            return filteredStudents;
        }
        for (User u : studentList) {
            if (filterId == 0) {
                if (u.getGroup() == null) {
                    filteredStudents.add(u);
                }
            } else {
                if (u.getGroup() != null && u.getGroup().getId() == filterId && u.getGroup().getId() != currentGroupId) {
                    filteredStudents.add(u);
                }
            }
        }
        return filteredStudents;
    }
}
