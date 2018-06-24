package ru.innopolis.stc9.service.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.innopolis.stc9.dao.interfaces.GroupDao;
import ru.innopolis.stc9.pojo.Group;
import ru.innopolis.stc9.service.interfaces.GroupService;

import java.util.List;

/**
 * Created by Сергей on 23.05.2018.
 * Реализация интерфейса GroupService
 */
@Service
@Transactional
public class GroupServiceImpl implements GroupService {
    @Autowired
    private GroupDao groupDao;

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
}
