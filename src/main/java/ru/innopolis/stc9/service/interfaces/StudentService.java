package ru.innopolis.stc9.service.interfaces;

import org.springframework.ui.Model;

public interface StudentService {
    void addingMainAttributeToModel(Model model, int id, Integer filterId);
}
