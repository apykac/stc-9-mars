package ru.innopolis.stc9.controllers;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ru.innopolis.stc9.TestContext;

import java.util.HashMap;
import java.util.Map;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestContext.class})
@WebAppConfiguration
public class LoginControllerTest {
    private MockMvc mockMvc;
    private Map<String, Object> sessionAttr;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(new LoginController()).build();
        sessionAttr = new HashMap<>();
        sessionAttr.put(SessionDataInform.ID, 1);
        sessionAttr.put(SessionDataInform.LOGIN, "login");
        sessionAttr.put(SessionDataInform.NAME, "name");
        sessionAttr.put(SessionDataInform.MSG, 5);
    }

    @Test
    public void loginTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/login")).
                andExpect(MockMvcResultMatchers.status().isOk()).
                andExpect(MockMvcResultMatchers.view().name("loginPage"));
    }

    @Test
    public void logoutTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/logout").
                sessionAttrs(sessionAttr).
                session(new MockHttpSession())).
                andExpect(MockMvcResultMatchers.redirectedUrl("/j_spring_security_logout"));
    }

    @Test
    public void startPageTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/university/start")).
                andExpect(MockMvcResultMatchers.status().isOk()).
                andExpect(MockMvcResultMatchers.view().name("views/startPage")).
                andExpect(MockMvcResultMatchers.model().attributeDoesNotExist("message"));

        mockMvc.perform(MockMvcRequestBuilders.get("/university/start?message=true")).
                andExpect(MockMvcResultMatchers.status().isOk()).
                andExpect(MockMvcResultMatchers.view().name("views/startPage")).
                andExpect(MockMvcResultMatchers.model().attribute("message", "true"));
    }
}
