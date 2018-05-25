package ru.innopolis.stc9.service;

import ru.innopolis.stc9.dao.GroupDao;
import ru.innopolis.stc9.dao.GroupDaoImpl;
import ru.innopolis.stc9.pojo.Group;

import java.util.List;

/**
 * Created by Сергей on 23.05.2018.
 * Реализация интерфейса GroupService
 */
public class GroupServiceImpl implements GroupService {
    private GroupDao groupDao = new GroupDaoImpl();

    @Override
    public boolean addGroup(Group group) {
        return groupDao.addGroup(group);
    }

    @Override
    public boolean updateGroup(Group group) {
        return groupDao.updateGroup(group);
    }

    @Override
    public boolean deleteGroup(int groupId) {
        return groupDao.deleteGroup(groupId);
    }

    @Override
    public Group findGroupById(int id) {
        return groupDao.findGroupById(id);
    }

    @Override
    public Group findGroupByName(String name) {
        return groupDao.findGroupByName(name);
    }

    @Override
    public List<Group> findAllGroups() {
        return groupDao.findAllGroups();
    }
}
