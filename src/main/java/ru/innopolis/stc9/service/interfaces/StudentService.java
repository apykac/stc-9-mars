package ru.innopolis.stc9.service.interfaces;

import org.springframework.ui.Model;
import ru.innopolis.stc9.pojo.Group;
import ru.innopolis.stc9.pojo.User;

import java.util.List;

public interface StudentService {
    void addingMainAttributeToModel(Model model, int id, int filterId);

    boolean isDuplicate(List<Group> groupsList, Model model, String name);

    List<User> studentFilter(List<User> studentsList, int filterId, int currentGroupId);
}
