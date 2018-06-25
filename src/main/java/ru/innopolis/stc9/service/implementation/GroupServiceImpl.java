package ru.innopolis.stc9.service.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.innopolis.stc9.dao.interfaces.GroupDao;
import ru.innopolis.stc9.pojo.Group;
import ru.innopolis.stc9.pojo.User;
import ru.innopolis.stc9.service.interfaces.GroupService;
import ru.innopolis.stc9.service.interfaces.UserService;

import java.util.List;

/**
 * Created by Сергей on 23.05.2018.
 * Реализация интерфейса GroupService
 */
@Service
@Transactional
public class GroupServiceImpl implements GroupService {
    private GroupDao groupDao;
    private UserService userService;

    @Autowired
    public GroupServiceImpl(GroupDao groupDao, UserService userService) {
        this.groupDao = groupDao;
        this.userService = userService;
    }

    @Override
    public boolean addGroup(Group group) {
        if (group == null) return false;
        return groupDao.addGroup(group);
    }

    @Override
    public boolean updateGroup(Group group) {
        if (group == null) return false;
        return groupDao.updateGroup(group);
    }

    @Override
    public boolean deleteGroup(int groupId) {
        if (groupId < 0) return false;
        return groupDao.deleteGroup(groupId);
    }

    @Override
    public Group findGroupById(Integer id) {
        if ((id == null) || (id < 0)) return null;
        return groupDao.findGroupById(id);
    }

    @Override
    public Group findGroupByName(String name) {
        if ((name == null) || name.isEmpty()) return null;
        return groupDao.findGroupByName(name);
    }

    @Override
    public List<Group> findAllGroups() {
        return groupDao.findAllGroups();
    }

    /**
     * Метод проверяет, есть ли в БД группа и студент с заданными id.
     *
     * @param groupId   - id группы, если не нужно проверять, то указываем "0"
     * @param studentId - id тудента, если не нужно проверять, то указываем "0"
     * @return true - если сущности найдены; false - если хотя б одной сущности нет
     */
    @Override
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
    }
}
