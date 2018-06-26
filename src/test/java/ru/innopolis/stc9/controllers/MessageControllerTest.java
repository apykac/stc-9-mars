package ru.innopolis.stc9.controllers;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.MultiValueMap;
import ru.innopolis.stc9.TestContext;
import ru.innopolis.stc9.service.interfaces.MessageService;
import ru.innopolis.stc9.service.interfaces.UserService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestContext.class})
@WebAppConfiguration
public class MessageControllerTest {
    MockMvc mockMvc;
    @Autowired
    private MessageService messageService;
    @Autowired
    private UserService userService;
    private MockHttpSession session;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(new MessageController(messageService, userService)).build();

    }

    @Test
    public void feedbackGetTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/university/profile/feedback")).
                andExpect(MockMvcResultMatchers.status().isOk()).
                andExpect(MockMvcResultMatchers.view().name("/views/feedbackPage"));
    }

    @Test
    public void feedbackPostWithSuccessTest() throws Exception {
        MultiValueMap<String, String> incParam = new HttpHeaders();
        Map<String, Object> sessionAttr = new HashMap<>();
        sessionAttr.put(SessionDataInform.ID, 1);
        sessionAttr.put(SessionDataInform.LOGIN, "login");
        sessionAttr.put(SessionDataInform.NAME, "name");
        PowerMockito.when(messageService.isCorrectData(incParam)).thenReturn(new ArrayList<>());
        PowerMockito.when(messageService.addMessage(incParam)).thenReturn(true);
        mockMvc.perform(MockMvcRequestBuilders.post("/university/profile/feedback").
                contentType(MediaType.APPLICATION_FORM_URLENCODED).
                params(incParam).
                sessionAttrs(sessionAttr)).
                andExpect(MockMvcResultMatchers.view().name("/views/feedbackPage")).
                andExpect(MockMvcResultMatchers.model().attribute("errors", null)).
                andExpect(MockMvcResultMatchers.model().attribute("success_list", new ArrayList<>()));
    }
}
