package ru.innopolis.stc9.service.implementation;

import org.springframework.stereotype.Service;
import ru.innopolis.stc9.pojo.Group;
import ru.innopolis.stc9.service.interfaces.GroupService;

import java.util.List;

/**
 * Created by Сергей on 23.05.2018.
 * Реализация интерфейса GroupService
 */
@Service
public class GroupServiceImpl implements GroupService {
    @Override
    public boolean addGroup(Group group) {
        return false;
    }

    @Override
    public boolean updateGroup(Group group) {
        return false;
    }

    @Override
    public boolean deleteGroup(int groupId) {
        return false;
    }

    @Override
    public Group findGroupById(Integer id) {
        return null;
    }

    @Override
    public List<Group> findAllGroups() {
        return null;
    }

    @Override
    public boolean isEntityFound(int groupId, int studentId) {
        return false;
    }

    @Override
    public void addSubjectToGroup(int groupId, int subjectId) {

    }

    @Override
    public void deleteSubjectFromGroup(int groupId, int subjectId) {

    }

    /*@Override
    public boolean addGroup(Group group) {
        if (group == null) return false;
        return groupDao.addGroup(group);
    }*/

    /*@Override
    public boolean updateGroup(Group group) {
        if (group == null) return false;
        return groupDao.updateGroup(group);
    }*/

    /*@Override
    public boolean deleteGroup(int groupId) {
        if (groupId < 0) return false;
        return groupDao.deleteGroup(groupId);
    }*/

    /*@Override
    public Group findGroupById(Integer id) {
        if ((id == null) || (id < 0)) return null;
        return groupDao.findGroupById(id);
    }*/

    /*@Override
    public List<Group> findAllGroups() {
        return groupDao.findAllGroups();
    }*/

    /*@Override
    public boolean isEntityFound(int groupId, int studentId) {
        boolean foundGroup = false;
        boolean foundStudent = false;
        if (groupId != 0) {
            for (Group g : findAllGroups()) {
                if (g.getId() == groupId) {
                    foundGroup = true;
                    break;
                }
            }
        } else {
            foundGroup = true;
        }
        if (studentId != 0) {
            for (User u : userService.getAllStudents()) {
                if (u.getId() == studentId) {
                    foundStudent = true;
                    break;
                }
            }
        } else {
            foundStudent = true;
        }
        return (foundGroup && foundStudent);
    }*/

    /*public void addSubjectToGroup(int groupId, int subjectId) {
        Group tempGroup = groupDao.findGroupById(groupId);
        List<Subject> subjects = tempGroup.getSubjects();
        subjects.add(subjectDao.findById(subjectId));
        tempGroup.setSubjects(subjects);
        groupDao.updateGroup(tempGroup);
    }*/

    /*public void deleteSubjectFromGroup(int groupId, int subjectId) {
        Group tempGroup = groupDao.findGroupById(groupId);
        List<Subject> subjects = tempGroup.getSubjects();
        subjects.remove(subjectDao.findById(subjectId));
        tempGroup.setSubjects(subjects);
        groupDao.updateGroup(tempGroup);
    }*/

}
