package ru.innopolis.stc9.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import ru.innopolis.stc9.dao.GroupDao;
import ru.innopolis.stc9.dao.GroupDaoImpl;
import ru.innopolis.stc9.pogo.Group;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by Сергей on 23.05.2018.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({GroupServiceImpl.class})
public class GroupServiceImplTest {
    private Group group;
    private GroupDao groupDao;
    private List<Group> list;
    private GroupService groupService;
    @Before
    public void setUp() throws Exception {
        group = new Group(1, "test");
        groupService = new GroupServiceImpl();
        groupDao = PowerMockito.mock(GroupDaoImpl.class);
        Field field = PowerMockito.field(GroupServiceImpl.class, "groupDao");
        field.set(groupService, groupDao);
        list = new ArrayList<>();
    }

    @Test
    public void addGroup() throws Exception {
        PowerMockito.when(groupDao.addGroup(group)).thenReturn(true);
        assertTrue(groupService.addGroup(group));
    }

    @Test
    public void updateGroup() throws Exception {
        PowerMockito.when(groupDao.updateGroup(group)).thenReturn(true);
        assertTrue(groupService.updateGroup(group));
    }

    @Test
    public void deleteGroup() throws Exception {
        PowerMockito.when(groupDao.deleteGroup(1)).thenReturn(true);
        assertTrue(groupService.deleteGroup(1));
    }

    @Test
    public void findGroupById() throws Exception {
        PowerMockito.when(groupDao.findGroupById(1)).thenReturn(group);
        assertEquals(groupService.findGroupById(1), group);
    }

    @Test
    public void findGroupByName() throws Exception {
        PowerMockito.when(groupDao.findGroupByName("test")).thenReturn(group);
        assertEquals(groupService.findGroupByName("test"), group);
    }

    @Test
    public void findAllGroups() throws Exception {
        PowerMockito.when(groupDao.findAllGroups()).thenReturn(list);
        assertEquals(groupService.findAllGroups(), list);
    }

}