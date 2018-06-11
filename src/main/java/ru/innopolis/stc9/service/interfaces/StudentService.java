package ru.innopolis.stc9.service.interfaces;

import org.springframework.ui.Model;
import ru.innopolis.stc9.pojo.Group;

import java.util.List;

public interface StudentService {
    void addingMainAttributeToModel(Model model, int id, Integer filterId);

    boolean isDuplicate(List<Group> groupsList, Model model, String name);
}
