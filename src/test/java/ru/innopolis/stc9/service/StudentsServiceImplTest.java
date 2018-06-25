package ru.innopolis.stc9.service;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.ui.Model;
import org.springframework.validation.support.BindingAwareModelMap;
import org.springframework.web.servlet.mvc.support.RedirectAttributesModelMap;
import ru.innopolis.stc9.pojo.Group;
import ru.innopolis.stc9.pojo.User;
import ru.innopolis.stc9.service.implementation.GroupServiceImpl;
import ru.innopolis.stc9.service.implementation.StudentServiceImpl;
import ru.innopolis.stc9.service.implementation.UserServiceImpl;
import ru.innopolis.stc9.service.interfaces.GroupService;
import ru.innopolis.stc9.service.interfaces.StudentService;
import ru.innopolis.stc9.service.interfaces.UserService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@RunWith(PowerMockRunner.class)
public class StudentsServiceImplTest {
    private Model model;
    private StudentService studentService;
    private UserServiceImplTest userTest;
    private GroupService groupService;
    private UserService userService;
    private List<Group> groupList;
    private List<User> studentList;
    private Group group;

    @Before
    public void setUp() {
        groupService = PowerMockito.mock(GroupServiceImpl.class);
        userService = PowerMockito.mock(UserServiceImpl.class);
        studentService = new StudentServiceImpl(groupService, userService);
        userTest = new UserServiceImplTest();
        groupList = getGroupList();
        studentList = getStudentList();
        model = new BindingAwareModelMap();
        group = PowerMockito.mock(Group.class);
    }

    private List<Group> getGroupList() {
        return new ArrayList<>(Arrays.asList(new Group(1, "1"), new Group(2, "2"),
                new Group(3, "3")));
    }

    private List<User> getStudentList() {
        return new ArrayList<>(Arrays.asList(userTest.createLegalUser(1, "ROLE_STUDENT", 1, 1),
                userTest.createLegalUser(2, "ROLE_STUDENT", 1, 1),
                userTest.createLegalUser(3, "ROLE_STUDENT", 2, 1),
                userTest.createLegalUser(4, "ROLE_STUDENT", 2, 1),
                userTest.createLegalUser(5, "ROLE_STUDENT", 3, 1),
                userTest.createLegalUser(6, "ROLE_STUDENT", null, 1)));
    }

    @Test
    public void addingMainAttributeToModelCorrectDataFullMatchesTest() {
        PowerMockito.when(groupService.findAllGroups()).thenReturn(groupList);
        PowerMockito.when(userService.getAllStudents()).thenReturn(studentList);
        PowerMockito.when(groupService.findGroupById(2)).thenReturn(group);
        PowerMockito.when(group.getName()).thenReturn("2");
        List<User> studentsWithoutGroupId = new ArrayList<>(Arrays.asList(studentList.get(0), studentList.get(1)));
        studentService.addingMainAttributeToModel(model, 2, 1);
        Assert.assertEquals(groupList, model.asMap().get("groups"));
        Assert.assertEquals("2", model.asMap().get("groupName"));
        Assert.assertEquals(2, model.asMap().get("id"));
        Assert.assertEquals(studentsWithoutGroupId, model.asMap().get("studentsWOG"));
    }

    @Test
    public void addingMainAttributeToModelCorrectDataNotFullMatchesTest1() {
        PowerMockito.when(groupService.findAllGroups()).thenReturn(groupList);
        PowerMockito.when(userService.getAllStudents()).thenReturn(studentList);
        PowerMockito.when(groupService.findGroupById(1)).thenReturn(group);
        PowerMockito.when(group.getName()).thenReturn("1");
        studentService.addingMainAttributeToModel(model, 1, 1);
        Assert.assertEquals(groupList, model.asMap().get("groups"));
        Assert.assertEquals("1", model.asMap().get("groupName"));
        Assert.assertEquals(1, model.asMap().get("id"));
        Assert.assertEquals(new ArrayList<>(), model.asMap().get("studentsWOG"));
    }

    @Test
    public void addingMainAttributeToModelCorrectDataNotFullMatchesTest2() {
        PowerMockito.when(groupService.findAllGroups()).thenReturn(groupList);
        PowerMockito.when(userService.getAllStudents()).thenReturn(studentList);
        PowerMockito.when(groupService.findGroupById(123)).thenReturn(group);
        PowerMockito.when(group.getName()).thenReturn("");
        List<User> studentsWithoutGroupId = new ArrayList<>(Arrays.asList(studentList.get(0), studentList.get(1)));
        studentService.addingMainAttributeToModel(model, 123, 1);
        Assert.assertEquals(groupList, model.asMap().get("groups"));
        Assert.assertEquals("", model.asMap().get("groupName"));
        Assert.assertEquals(123, model.asMap().get("id"));
        Assert.assertEquals(new ArrayList<>(), model.asMap().get("students"));
        Assert.assertEquals(studentsWithoutGroupId, model.asMap().get("studentsWOG"));
    }

    @Test
    public void addingMainAttributeToModelCorrectDataNotFullMatchesTest3() {
        PowerMockito.when(groupService.findAllGroups()).thenReturn(groupList);
        PowerMockito.when(userService.getAllStudents()).thenReturn(studentList);
        PowerMockito.when(groupService.findGroupById(1)).thenReturn(group);
        PowerMockito.when(group.getName()).thenReturn("1");
        studentService.addingMainAttributeToModel(model, 1, 123);
        Assert.assertEquals(groupList, model.asMap().get("groups"));
        Assert.assertEquals("1", model.asMap().get("groupName"));
        Assert.assertEquals(1, model.asMap().get("id"));
        Assert.assertEquals(new ArrayList<>(), model.asMap().get("studentsWOG"));
    }

    @Test
    public void addingMainAttributeToModelIncorrectDataTest() {
        studentService.addingMainAttributeToModel(null, 1, 1);
        Assert.assertTrue(model.asMap().isEmpty());
        studentService.addingMainAttributeToModel(null, -1, 0);
        Assert.assertTrue(model.asMap().isEmpty());
        studentService.addingMainAttributeToModel(null, 1, -1);
        Assert.assertTrue(model.asMap().isEmpty());
    }

    @Test
    public void isDuplicateCorrectDataTest() {
        Assert.assertTrue(studentService.isDuplicate(groupList, model, "1"));
        Assert.assertEquals("Такое имя группы уже используется. Введите другое", model.asMap().get("errorName"));
        model = new RedirectAttributesModelMap();
        Assert.assertFalse(studentService.isDuplicate(groupList, model, "2323"));
        Assert.assertNull(model.asMap().get("errorName"));
    }

    @Test
    public void isDuplicateIncorrectDataTest() {
        Assert.assertFalse(studentService.isDuplicate(null, model, "1"));
        Assert.assertFalse(studentService.isDuplicate(groupList, null, "1"));
        Assert.assertFalse(studentService.isDuplicate(groupList, model, null));
        Assert.assertFalse(studentService.isDuplicate(groupList, model, ""));
    }

    @Test
    public void studentFilterCorrectDataTest() {
        List<User> resultList1 = new ArrayList<>(Arrays.asList(studentList.get(0), studentList.get(1)));
        List<User> resultList2 = new ArrayList<>(Collections.singletonList(studentList.get(5)));
        Assert.assertEquals(resultList1, studentService.studentFilter(studentList, 1, 2));
        Assert.assertEquals(resultList2, studentService.studentFilter(studentList, 0, 2));
        Assert.assertEquals(new ArrayList<>(), studentService.studentFilter(studentList, 1, 1));
        Assert.assertEquals(resultList1, studentService.studentFilter(studentList, 1, 123));
        Assert.assertEquals(resultList2, studentService.studentFilter(studentList, 0, 123));
        Assert.assertEquals(new ArrayList<>(), studentService.studentFilter(studentList, 123, 2));
    }

    @Test
    public void studentFilterIncorrectDataTest() {
        Assert.assertEquals(new ArrayList<>(), studentService.studentFilter(new ArrayList<>(), 2, 1));
        Assert.assertEquals(new ArrayList<>(), studentService.studentFilter(studentList, -1, 1));
        Assert.assertEquals(new ArrayList<>(), studentService.studentFilter(studentList, 2, -1));
    }


}
