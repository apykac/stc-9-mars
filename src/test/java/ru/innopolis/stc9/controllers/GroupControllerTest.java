package ru.innopolis.stc9.controllers;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ru.innopolis.stc9.TestContext;
import ru.innopolis.stc9.pojo.Group;
import ru.innopolis.stc9.pojo.User;
import ru.innopolis.stc9.service.interfaces.GroupService;
import ru.innopolis.stc9.service.interfaces.StudentService;
import ru.innopolis.stc9.service.interfaces.UserService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Created by Сергей on 11.06.2018.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestContext.class})
@WebAppConfiguration
public class GroupControllerTest {
    MockMvc mockMvc;
    private List<Group> groupList;
    @Autowired
    GroupService groupServiceMock;
    @Autowired
    UserService userServiceMock;
    @Autowired
    StudentService studentService;

    @Before
    public void setUp() {
        groupList = new ArrayList<>();
        mockMvc = MockMvcBuilders.standaloneSetup(new GroupController(groupServiceMock, userServiceMock, studentService)).build();
        Group one = new Group(1, "test");
        Group two = new Group(2, "test2");
        User user = new User();
        User user2 = new User();
        groupList.add(one);
        groupList.add(two);
        when(groupServiceMock.findAllGroups()).thenReturn(groupList);
        when(groupServiceMock.addGroup(new Group("test3"))).thenReturn(true);
        when(userServiceMock.getStudentsByGroupId(1)).thenReturn(Arrays.asList(user, user2));
        when(groupServiceMock.findGroupById(1)).thenReturn(new Group("test"));

    }

    @Test
    public void viewAllGroupTest() throws Exception {

        mockMvc.perform(post("/university/teacher/allgroup"))
                .andExpect(status().isOk())
                .andExpect(view().name("views/allGroups"))
                .andExpect(model().attribute("groups", hasSize(2)));


    }

    @Test
    public void addGroupWithDuplicateNameTest() throws Exception {

        mockMvc.perform(post("/university/teacher/addgroups")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("name", "test"))
                .andExpect(status().isOk())
                .andExpect(view().name("views/allGroups"))
                .andExpect(model().attribute("errorName", "Такое имя группы уже используется. Введите другое"))
                .andExpect(model().attribute("groups", hasSize(2)));
    }

    @Test
    public void addGroupTest() throws Exception {
        mockMvc.perform(post("/university/teacher/addgroups")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("name", "test3"))
                .andExpect(status().isOk())
                .andExpect(view().name("views/allGroups"))
                .andExpect(model().attribute("groups", hasSize(2)));
    }

    @Test
    public void forUpdateGroupTest() throws Exception {
        mockMvc.perform(post("/university/teacher/group/{id}", 1)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("groupStatus", "0"))
                .andExpect(status().isOk())
                .andExpect(view().name("views/group"))
                .andExpect(model().attribute("groups", hasSize(2)))
                .andExpect(model().attribute("groupName", "test"))
                .andExpect(model().attribute("id", 1))
                .andExpect(model().attribute("students", hasSize(0)))
                .andExpect(model().attribute("studentsWOG", hasSize(0)));
    }

    @Test
    public void UpdateGroupWithDuplicateNameTest() throws Exception {
        mockMvc.perform(post("/university/teacher/updateGroup")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("name", "test2")
                .param("id", "1"))
                .andExpect(status().isOk());
        forUpdateGroupTest();

    }

    @Test
    public void UpdateGroupWithOutDuplicateNameTest() throws Exception {
        mockMvc.perform(post("/university/teacher/updateGroup")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("name", "test1")
                .param("id", "1"))
                .andExpect(status().isOk());
        viewAllGroupTest();

    }

    @Test
    public void deleteGroupTest() throws Exception {
        mockMvc.perform(post("/university/teacher/deleteGroup")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("id", "1"))
                .andExpect(status().isOk())
                .andExpect(view().name("views/allGroups"))
                .andExpect(model().attribute("groups", hasSize(2)));
    }

    @Test
    public void addStudentToGroupTest() throws Exception {
        mockMvc.perform(post("/university/teacher/addStudent")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("id", "1")
                .param("studentId", "2"))
                .andExpect(status().isOk());
        forUpdateGroupTest();
    }

    @Test
    public void deleteStudentFromGroupTest() throws Exception {
        mockMvc.perform(post("/university/teacher/group/deleteStudentFromGroup/{id}/{studentId}", 1, 1))
                .andExpect(status().isOk());
        forUpdateGroupTest();



    }

}