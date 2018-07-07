package ru.innopolis.stc9.service.interfaces;

import ru.innopolis.stc9.pojo.Group;

import java.util.List;

/**
 * Created by Сергей on 23.05.2018.
 * Интерфейс сервисного слоя для студенческих групп
 */
public interface GroupService {
    boolean addGroup(Group group);

    boolean updateGroup(Group group);

    boolean deleteGroup(int groupId);

    Group findGroupById(Integer id);

    List<Group> findAllGroups();

    boolean isEntityFound(int groupId, int studentId);

    void addSubjectToGroup(int groupId, int subjectId);

    void deleteSubjectFromGroup(int groupId, int subjectId);
}
