package ru.innopolis.stc9.controllers;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
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
import ru.innopolis.stc9.service.interfaces.UserService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestContext.class})
@WebAppConfiguration
public class RegistrationControllerTest {
    private MockMvc mockMvc;
    @Autowired
    private UserService userService;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(new RegistrationController(userService)).build();
    }

    @Test
    public void infoHandlerOfGetTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/registration")).
                andExpect(MockMvcResultMatchers.status().isOk()).
                andExpect(MockMvcResultMatchers.view().name("registrationPage"));
    }

    @Test
    public void registrationFormHandlerTest() throws Exception {
        String body = "login=login&hashPassword=hash&firstName=fname&secondName=sname&middleName=mname";
        List<String> errorList = new ArrayList<>();
        PowerMockito.when(userService.isCorrectData(Mockito.any(MultiValueMap.class))).thenReturn(errorList);
        PowerMockito.when(userService.isExist("login")).thenReturn(false);
        PowerMockito.when(userService.addUserByParam(Mockito.any(MultiValueMap.class))).thenReturn(true);
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.post("/registration").
                contentType(MediaType.APPLICATION_FORM_URLENCODED).
                content(body);

        mockMvc.perform(builder).
                andExpect(MockMvcResultMatchers.redirectedUrl("/login?registration=true"));

        errorList.add("some");

        mockMvc.perform(builder).
                andExpect(MockMvcResultMatchers.status().isOk()).
                andExpect(MockMvcResultMatchers.view().name("registrationPage")).
                andExpect(MockMvcResultMatchers.model().attribute("errorMsg", errorList));

        PowerMockito.when(userService.isExist("login")).thenReturn(true);
        errorList = new ArrayList<>(Collections.singletonList("Login is Exist"));

        mockMvc.perform(builder).
                andExpect(MockMvcResultMatchers.status().isOk()).
                andExpect(MockMvcResultMatchers.view().name("registrationPage")).
                andExpect(MockMvcResultMatchers.model().attribute("errorMsg", errorList));
    }
}
