package ru.innopolis.stc9.controllers;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.MultiValueMap;
import ru.innopolis.stc9.TestContext;
import ru.innopolis.stc9.pojo.User;
import ru.innopolis.stc9.service.interfaces.UserService;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestContext.class})
@WebAppConfiguration
public class UsersControllerTest {
    private MockMvc mockMvc;
    private Map<String, Object> sessionAttr;
    private User user;
    private LoginController loginController = new LoginController();
    @Autowired
    private UserService userService;

    @Before
    public void setUp() throws IllegalAccessException {
        UsersController usersController = new UsersController(userService);
        Field field = PowerMockito.field(UsersController.class, "loginController");
        field.set(usersController, loginController);
        mockMvc = MockMvcBuilders.standaloneSetup(usersController).build();
        sessionAttr = new HashMap<>();
        sessionAttr.put(SessionDataInform.ID, 1);
        sessionAttr.put(SessionDataInform.LOGIN, "login");
        sessionAttr.put(SessionDataInform.NAME, "name");
        sessionAttr.put(SessionDataInform.MSG, 5);
        user = new User();
        user.setEnabled(1);
        user.setPermissionGroup("ROLE_ADMIN");
        user.setLogin("login");
        user.setFirstName("fname");
        user.setSecondName("sname");
    }

    @Test
    public void editUserGetSmallTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/temp/edit_user")).
                andExpect(MockMvcResultMatchers.redirectedUrl("/start"));
    }

    @Test
    public void getUserListGetTest() throws Exception {
        PowerMockito.when(userService.getUserList()).thenReturn(new ArrayList<>());
        mockMvc.perform(MockMvcRequestBuilders.get("/admin/users_list")).
                andExpect(MockMvcResultMatchers.status().isOk()).
                andExpect(MockMvcResultMatchers.view().name("views/allUsers")).
                andExpect(MockMvcResultMatchers.model().attribute("usersList", new ArrayList<>()));
    }

    @Test
    public void editUserGetTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/admin/edit_user/{id}", -1).
                sessionAttrs(sessionAttr).
                session(new MockHttpSession())).
                andExpect(MockMvcResultMatchers.view().name("views/editUser")).
                andExpect(MockMvcResultMatchers.status().isOk());

        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.get("/admin/edit_user/{id}", 1).
                sessionAttrs(sessionAttr).
                session(new MockHttpSession()).
                param("isOwner", "false");
        PowerMockito.when(userService.findUserByIdWithSubjectList(1)).thenReturn(user);

        mockMvc.perform(builder).
                andExpect(MockMvcResultMatchers.redirectedUrl("/start"));

        user.setId(1);

        mockMvc.perform(builder).
                andExpect(MockMvcResultMatchers.redirectedUrl("/start"));

        builder = MockMvcRequestBuilders.get("/admin/edit_user/{id}", 1).
                sessionAttrs(sessionAttr).
                session(new MockHttpSession()).
                param("isOwner", "true");

        mockMvc.perform(builder).
                andExpect(MockMvcResultMatchers.status().isOk()).
                andExpect(MockMvcResultMatchers.view().name("views/editUser")).
                andExpect(MockMvcResultMatchers.model().attribute("user", user)).
                andExpect(MockMvcResultMatchers.model().attribute("isOwner", true));
    }

    @Test
    public void delUserPostTest() throws Exception {
        PowerMockito.when(userService.delUserById(1)).thenReturn(true);
        PowerMockito.when(userService.getUserList()).thenReturn(new ArrayList<>());
        PowerMockito.when(userService.findUserByIdWithSubjectList(1)).thenReturn(user);
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.post("/admin/edit_user/{id}/delete", 1).
                sessionAttrs(sessionAttr).
                session(new MockHttpSession());

        mockMvc.perform(builder).
                andExpect(MockMvcResultMatchers.status().isOk()).
                andExpect(MockMvcResultMatchers.view().name("views/allUsers")).
                andExpect(MockMvcResultMatchers.model().attribute("usersList", new ArrayList<>()));

        PowerMockito.when(userService.delUserById(1)).thenReturn(false);
        user.setEnabled(0);

        mockMvc.perform(builder).
                andExpect(MockMvcResultMatchers.status().isOk()).
                andExpect(MockMvcResultMatchers.view().name("views/editUser")).
                andExpect(MockMvcResultMatchers.model().attribute("user", user)).
                andExpect(MockMvcResultMatchers.model().attribute("isOwner", false));
    }

    @Test
    public void editUserPostNotOwnerTest() throws Exception {
        String body = "newPassword=123&" +
                "id=23&" +
                "login=login&" +
                "hashPassword=hash&" +
                "firstName=fname&" +
                "secondName=sname&" +
                "middleName=mname&" +
                "permissionGroup=ROLE_STUDENT&" +
                "enabled=1&" +
                "isOwner=false&" +
                "repeatNewPassword=123";
        Object[] objects = new Object[]{new ArrayList<>(), new ArrayList<>(), false};
        user.setPermissionGroup("ROLE_STUDENT");
        user.setId(23);
        PowerMockito.when(userService.editUser(Mockito.any(MultiValueMap.class))).thenReturn(objects);
        PowerMockito.when(userService.findUserByIdWithSubjectList(23)).thenReturn(user);


        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.post("/temp/edit_user").
                sessionAttrs(sessionAttr).
                session(new MockHttpSession()).
                contentType(MediaType.APPLICATION_FORM_URLENCODED).
                content(body);

        mockMvc.perform(builder).
                andExpect(MockMvcResultMatchers.status().isOk()).
                andExpect(MockMvcResultMatchers.view().name("views/editUser")).
                andExpect(MockMvcResultMatchers.model().attribute("user", user)).
                andExpect(MockMvcResultMatchers.model().attribute("isOwner", false)).
                andExpect(MockMvcResultMatchers.model().attributeDoesNotExist("errors")).
                andExpect(MockMvcResultMatchers.model().attributeDoesNotExist("success_list"));

        objects[0] = new ArrayList<>(Collections.singletonList("some"));
        objects[1] = new ArrayList<>(Collections.singletonList("some"));

        mockMvc.perform(builder).
                andExpect(MockMvcResultMatchers.status().isOk()).
                andExpect(MockMvcResultMatchers.view().name("views/editUser")).
                andExpect(MockMvcResultMatchers.model().attribute("user", user)).
                andExpect(MockMvcResultMatchers.model().attribute("isOwner", false)).
                andExpect(MockMvcResultMatchers.model().attribute("errors", (objects[0]))).
                andExpect(MockMvcResultMatchers.model().attribute("success_list", objects[1]));
    }

    @Test
    public void editUserPostOwnerTest() throws Exception {
        String body = "newPassword=123&" +
                "id=1&" +
                "login=login&" +
                "hashPassword=hash&" +
                "firstName=fname&" +
                "secondName=sname&" +
                "middleName=mname&" +
                "permissionGroup=ROLE_STUDENT&" +
                "enabled=1&" +
                "isOwner=true&" +
                "repeatNewPassword=123";
        Object[] objects = new Object[]{new ArrayList<>(), new ArrayList<>(), true};
        PowerMockito.when(userService.findUserById(1)).thenReturn(user);
        PowerMockito.when(userService.findUserByIdWithSubjectList(1)).thenReturn(user);
        PowerMockito.when(userService.editUser(Mockito.any(MultiValueMap.class))).thenReturn(objects);
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.post("/temp/edit_user").
                sessionAttrs(sessionAttr).
                session(new MockHttpSession()).
                contentType(MediaType.APPLICATION_FORM_URLENCODED).
                content(body);

        mockMvc.perform(builder).
                andExpect(MockMvcResultMatchers.status().isOk()).
                andExpect(MockMvcResultMatchers.view().name("views/editUser")).
                andExpect(MockMvcResultMatchers.model().attribute("user", user)).
                andExpect(MockMvcResultMatchers.model().attribute("isOwner", true)).
                andExpect(MockMvcResultMatchers.model().attributeDoesNotExist("errors")).
                andExpect(MockMvcResultMatchers.model().attributeDoesNotExist("success_list"));
    }

    @Test
    public void editOwnerProfileTest() throws Exception {
        PowerMockito.when(userService.findUserByIdWithSubjectList(1)).thenReturn(user);
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.get("/university/profile").
                sessionAttrs(sessionAttr).
                session(new MockHttpSession());

        mockMvc.perform(builder).
                andExpect(MockMvcResultMatchers.status().isOk()).
                andExpect(MockMvcResultMatchers.view().name("views/editUser")).
                andExpect(MockMvcResultMatchers.model().attribute("user", user)).
                andExpect(MockMvcResultMatchers.model().attribute("isOwner", true)).
                andExpect(MockMvcResultMatchers.model().attributeDoesNotExist("errors")).
                andExpect(MockMvcResultMatchers.model().attributeDoesNotExist("success_list"));
    }

    @Test
    public void deleteAccountGetTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/university/profile/delete")).
                andExpect(MockMvcResultMatchers.status().isOk()).
                andExpect(MockMvcResultMatchers.view().name("views/deleteAccountPage"));
    }

    @Test
    public void deleteAccountPostTest() throws Exception {
        MockHttpSession mockHttpSession = new MockHttpSession();
        PowerMockito.when(userService.checkPasswordOfCurrentAccount(1, "password")).thenReturn(true);
        PowerMockito.when(userService.deactivationCurrentAccount(1)).thenReturn(true);
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.post("/university/profile/delete").
                sessionAttrs(sessionAttr).
                session(mockHttpSession).
                param("password", "password");

        mockMvc.perform(builder).
                andExpect(MockMvcResultMatchers.redirectedUrl("/j_spring_security_logout"));

        PowerMockito.when(userService.deactivationCurrentAccount(1)).thenReturn(false);

        mockMvc.perform(builder).
                andExpect(MockMvcResultMatchers.status().isOk()).
                andExpect(MockMvcResultMatchers.view().name("views/deleteAccountPage")).
                andExpect(MockMvcResultMatchers.model().attribute("error", "Error deactivation by DAO"));

        PowerMockito.when(userService.checkPasswordOfCurrentAccount(1, "password")).thenReturn(false);

        mockMvc.perform(builder).
                andExpect(MockMvcResultMatchers.status().isOk()).
                andExpect(MockMvcResultMatchers.view().name("views/deleteAccountPage")).
                andExpect(MockMvcResultMatchers.model().attribute("error", "Incorrect password"));
    }
}
