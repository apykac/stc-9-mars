package ru.innopolis.stc9.service.interfaces;

import org.springframework.ui.Model;
import ru.innopolis.stc9.pojo.Group;
import ru.innopolis.stc9.pojo.User;

import java.util.List;

public interface StudentService {
    void addingMainAttributeToModel(Model model, int id, Integer filterId);

    boolean isDuplicate(List<Group> groupsList, Model model, String name);

    List<User> studentFilter(List<User> studentsList, Integer filterId, int currentGroupId);

    String findNameById(List<Group> groupsList, int id);

    List<User> getStudentsByGroupId(List<User> studentsList, Integer groupId);

    List<User> getAllStudentExceptId(List<User> studentsList, Integer groupId);

    void distributionStudentsByGroup(List<Group> groupsList, List<User> studentsList);
}
