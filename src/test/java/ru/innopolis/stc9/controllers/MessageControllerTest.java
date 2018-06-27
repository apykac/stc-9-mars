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
import ru.innopolis.stc9.pojo.Message;
import ru.innopolis.stc9.pojo.User;
import ru.innopolis.stc9.service.interfaces.MessageService;
import ru.innopolis.stc9.service.interfaces.UserService;

import java.util.*;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestContext.class})
@WebAppConfiguration
public class MessageControllerTest {
    private MockMvc mockMvc;
    private Map<String, Object> sessionAttr;
    @Autowired
    private MessageService messageService;
    @Autowired
    private UserService userService;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(new MessageController(messageService, userService)).build();
        sessionAttr = new HashMap<>();
        sessionAttr.put(SessionDataInform.ID, 1);
        sessionAttr.put(SessionDataInform.LOGIN, "login");
        sessionAttr.put(SessionDataInform.NAME, "name");
        sessionAttr.put(SessionDataInform.MSG, 5);
    }

    @Test
    public void feedbackGetTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/university/profile/feedback")).
                andExpect(MockMvcResultMatchers.status().isOk()).
                andExpect(MockMvcResultMatchers.view().name("/views/feedbackPage"));
    }

    @Test
    public void messagesToPageTest() throws Exception {
        List<Message> commonList = new ArrayList<>();
        List<Message> privateList = new ArrayList<>();
        List<Message>[] lists = new List[]{commonList, privateList};
        User user = new User();
        PowerMockito.when(userService.findUserById(1)).thenReturn(user);
        PowerMockito.when(messageService.getAllMessages(user)).thenReturn(lists);

        mockMvc.perform(MockMvcRequestBuilders.get("/university/messages").
                sessionAttrs(sessionAttr).
                session(new MockHttpSession())).
                andExpect(MockMvcResultMatchers.status().isOk()).
                andExpect(MockMvcResultMatchers.view().name("/views/allMessages")).
                andExpect(MockMvcResultMatchers.model().attribute("commonList", commonList)).
                andExpect(MockMvcResultMatchers.model().attribute("privateList", privateList));
    }

    @Test
    public void editMessageGetTest() throws Exception {
        Message message = new Message();
        PowerMockito.when(messageService.getMessageByIdWithFromUser(1)).thenReturn(message);
        mockMvc.perform(MockMvcRequestBuilders.get("/university/messages/{id}", 1).
                sessionAttrs(sessionAttr).
                session(new MockHttpSession())).
                andExpect(MockMvcResultMatchers.status().isOk()).
                andExpect(MockMvcResultMatchers.view().name("/views/messagePage")).
                andExpect(MockMvcResultMatchers.model().attribute("message", message)).
                andExpect(MockMvcResultMatchers.model().attribute("fromUser", 1));
    }

    @Test
    public void deleteMessageGetTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/university/messages/{id}/delete", 1)).
                andExpect(MockMvcResultMatchers.redirectedUrl("/university/start"));
    }

    @Test
    public void deleteMessagePostTest() throws Exception {
        Message message = new Message();
        PowerMockito.when(messageService.getMessageByIdWithFromUser(1)).thenReturn(message);
        PowerMockito.when(messageService.deleteMessageById(1)).thenReturn(true);
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.post("/university/messages/{id}/delete", 1).
                sessionAttrs(sessionAttr).
                session(new MockHttpSession());

        mockMvc.perform(builder).
                andExpect(MockMvcResultMatchers.redirectedUrl("/university/start?message=deleted"));

        PowerMockito.when(messageService.deleteMessageById(1)).thenReturn(false);

        mockMvc.perform(builder).
                andExpect(MockMvcResultMatchers.status().isOk()).
                andExpect(MockMvcResultMatchers.view().name("/views/messagePage")).
                andExpect(MockMvcResultMatchers.model().attribute("error", "Fail to delete message by DAO")).
                andExpect(MockMvcResultMatchers.model().attribute("message", message)).
                andExpect(MockMvcResultMatchers.model().attribute("fromUser", 1));
    }

    @Test
    public void replyMessageGetTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/university/messages/{id}/reply", 1)).
                andExpect(MockMvcResultMatchers.redirectedUrl("/university/start"));
    }

    @Test
    public void feedbackPostTest() throws Exception {
        String body = "toUserGroup=ROLE_ADMIN&theme=Theme&text=text";
        List<String> errors = new ArrayList<>(Collections.singletonList("Error adding by DAO"));
        List<String> success = new ArrayList<>(Collections.singletonList("Сообщение отправлено"));
        PowerMockito.when(messageService.isCorrectData(Mockito.any(MultiValueMap.class))).thenReturn(new ArrayList());
        PowerMockito.when(messageService.addMessage(Mockito.any(MultiValueMap.class))).thenReturn(true);
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.post("/university/profile/feedback").
                contentType(MediaType.APPLICATION_FORM_URLENCODED).
                sessionAttrs(sessionAttr).
                session(new MockHttpSession()).
                content(body);

        mockMvc.perform(builder).
                andExpect(MockMvcResultMatchers.status().isOk()).
                andExpect(MockMvcResultMatchers.view().name("/views/feedbackPage")).
                andExpect(MockMvcResultMatchers.model().attribute("errors", new ArrayList<>())).
                andExpect(MockMvcResultMatchers.model().attribute("success_list", success));

        PowerMockito.when(messageService.addMessage(Mockito.any(MultiValueMap.class))).thenReturn(false);

        mockMvc.perform(builder).
                andExpect(MockMvcResultMatchers.status().isOk()).
                andExpect(MockMvcResultMatchers.view().name("/views/feedbackPage")).
                andExpect(MockMvcResultMatchers.model().attribute("errors", errors)).
                andExpect(MockMvcResultMatchers.model().attribute("success_list", new ArrayList<>()));

        PowerMockito.when(messageService.isCorrectData(Mockito.any(MultiValueMap.class))).thenReturn(errors);

        mockMvc.perform(builder).
                andExpect(MockMvcResultMatchers.status().isOk()).
                andExpect(MockMvcResultMatchers.view().name("/views/feedbackPage")).
                andExpect(MockMvcResultMatchers.model().attribute("errors", errors)).
                andExpect(MockMvcResultMatchers.model().attribute("success_list", new ArrayList<>()));
    }

    @Test
    public void replyMessagePostTest() throws Exception {
        String body = "toUserGroup=ROLE_ADMIN&theme=Theme&text=text&fromUserId=1";
        Message message = new Message();
        List<String> errors = new ArrayList<>(Collections.singletonList("Error adding by DAO"));
        PowerMockito.when(messageService.addMessage(Mockito.any(MultiValueMap.class))).thenReturn(true);
        PowerMockito.when(messageService.isCorrectData(Mockito.any(MultiValueMap.class))).thenReturn(new ArrayList());
        PowerMockito.when(messageService.getMessageByIdWithFromUser(1)).thenReturn(message);
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.
                post("/university/messages/{id}/reply", 1).
                contentType(MediaType.APPLICATION_FORM_URLENCODED).
                sessionAttrs(sessionAttr).
                session(new MockHttpSession()).
                content(body);

        mockMvc.perform(builder).
                andExpect(MockMvcResultMatchers.status().isOk()).
                andExpect(MockMvcResultMatchers.view().name("/views/messagePage")).
                andExpect(MockMvcResultMatchers.model().attribute("success", "Сообщение отправлено успешно")).
                andExpect(MockMvcResultMatchers.model().attribute("message", message)).
                andExpect(MockMvcResultMatchers.model().attribute("fromUser", 1));

        PowerMockito.when(messageService.addMessage(Mockito.any(MultiValueMap.class))).thenReturn(false);

        mockMvc.perform(builder).
                andExpect(MockMvcResultMatchers.status().isOk()).
                andExpect(MockMvcResultMatchers.view().name("/views/messagePage")).
                andExpect(MockMvcResultMatchers.model().attribute("error", "Fail to add message by DAO")).
                andExpect(MockMvcResultMatchers.model().attribute("message", message)).
                andExpect(MockMvcResultMatchers.model().attribute("fromUser", 1));

        PowerMockito.when(messageService.isCorrectData(Mockito.any(MultiValueMap.class))).
                thenReturn(new ArrayList(Collections.singletonList("some")));

        mockMvc.perform(builder).
                andExpect(MockMvcResultMatchers.status().isOk()).
                andExpect(MockMvcResultMatchers.view().name("/views/messagePage")).
                andExpect(MockMvcResultMatchers.model().attribute("error", "Сообщение пустое, введите текст")).
                andExpect(MockMvcResultMatchers.model().attribute("message", message)).
                andExpect(MockMvcResultMatchers.model().attribute("fromUser", 1));
    }
}
