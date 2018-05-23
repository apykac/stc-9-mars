package ru.innopolis.stc9.service;

import ru.innopolis.stc9.pogo.Group;

import java.util.List;

/**
 * Created by Сергей on 23.05.2018.
 * Интерфейс сервисного слоя для студенческих групп
 */
public interface GroupService {
    boolean addGroup(Group group);
    boolean updateGroup(Group group);
    boolean deleteGroup(int groupId);
    Group findGroupById(int id);
    Group findGroupByName(String name);
    List<Group> findAllGroups();
}
