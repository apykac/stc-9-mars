package ru.innopolis.stc9.service;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.springframework.http.HttpHeaders;
import org.springframework.util.MultiValueMap;
import ru.innopolis.stc9.dao.interfaces.MessageDao;
import ru.innopolis.stc9.dao.mappers.Mapper;
import ru.innopolis.stc9.dao.mappers.MessageMapper;
import ru.innopolis.stc9.pojo.Message;
import ru.innopolis.stc9.service.implementation.MessageServiceImpl;
import ru.innopolis.stc9.service.interfaces.MessageService;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class MessageServiceImplTest {
    private MessageService messageService;
    private Mapper mapper;
    private MessageDao messageDao;

    @Before
    public void setUp() throws IllegalAccessException {
        messageService = new MessageServiceImpl();
        mapper = PowerMockito.mock(MessageMapper.class);
        messageDao = PowerMockito.mock(MessageDao.class);
        Field fieldMapper = PowerMockito.field(MessageServiceImpl.class, "mapper");
        Field fieldMessageDao = PowerMockito.field(MessageServiceImpl.class, "messageDao");
        fieldMapper.set(messageService, mapper);
        fieldMessageDao.set(messageService, messageDao);
    }

    @Test
    public void isCorrectDataCorrectDataTest() {
        List<String> expectedList = new ArrayList<>(Collections.singletonList("Не пытайтесь отправить пустое сообщение!"));
        MultiValueMap<String, String> param = new HttpHeaders();
        param.put("some", new ArrayList<>(Collections.singletonList("not empty")));
        Assert.assertEquals(new ArrayList<>(), messageService.isCorrectData(param));
        param.put(MessageMapper.TEXT, new ArrayList<>(Collections.singletonList("not empty")));
        Assert.assertEquals(new ArrayList<>(), messageService.isCorrectData(param));
        param.put(MessageMapper.TEXT, new ArrayList<>(Collections.singletonList("")));
        Assert.assertEquals(expectedList, messageService.isCorrectData(param));
    }

    @Test
    public void isCorrectDataIncorrectDataTest() {
        Assert.assertEquals(new ArrayList<>(), messageService.isCorrectData(null));
        Assert.assertEquals(new ArrayList<>(), messageService.isCorrectData(new HttpHeaders()));
    }

    @Test
    public void addMessageCorrectDataTest() {
        MultiValueMap<String, String> param = new HttpHeaders();
        param.put("some", new ArrayList<>(Collections.singletonList("not empty")));
        PowerMockito.when(mapper.getByParam(Mockito.any(MultiValueMap.class))).thenReturn(new Message());
        PowerMockito.when(messageDao.addMessage(Mockito.any(Message.class))).thenReturn(true);
        Assert.assertTrue(messageService.addMessage(param));
    }

    @Test
    public void addMessageIncorrectDataTest() {
        Assert.assertFalse(messageService.addMessage(null));
        Assert.assertFalse(messageService.addMessage(new HttpHeaders()));
    }

    @Test
    public void getMessageByIdCorrectDataTest() {
        Message message = new Message();
        PowerMockito.when(messageDao.getMessageById(1)).thenReturn(message);
        Assert.assertEquals(message, messageService.getMessageById(1));
        PowerMockito.when(messageDao.getMessageById(1)).thenReturn(null);
        Assert.assertNull(messageService.getMessageById(1));
    }

    @Test
    public void getMessageByIdIncorrectDataTest() {
        Assert.assertNull(messageService.getMessageById(-1));
    }

    @Test
    public void deleteMessageByIdCorrectDataTest() {
        PowerMockito.when(messageDao.deleteMessageById(1)).thenReturn(true);
        Assert.assertTrue(messageService.deleteMessageById(1));
        PowerMockito.when(messageDao.deleteMessageById(1)).thenReturn(false);
        Assert.assertFalse(messageService.deleteMessageById(1));
    }

    @Test
    public void deleteMessageByIdCIncorrectDataTest() {
        Assert.assertFalse(messageService.deleteMessageById(-1));
    }

    @Test
    public void getAllMessagesCorrectDataTest() {
        List<Message> commonList = new ArrayList<>(Arrays.asList(new Message(), new Message(), new Message(), new Message()));
        List<Message> privateList = new ArrayList<>(Arrays.asList(new Message(), new Message()));
        PowerMockito.when(messageDao.getAllMessagesByRole("ROLE_STUDENT")).thenReturn(commonList);
        PowerMockito.when(messageDao.getAllMessagesByToUserId(1)).thenReturn(privateList);
        List<Message>[] result = messageService.getAllMessages(1,"ROLE_STUDENT");
        Assert.assertEquals(commonList, result[0]);
        Assert.assertEquals(privateList,result[1]);
    }

    @Test
    public void getAllMessagesIncorrectDataTest() {
        List<Message>[] result = messageService.getAllMessages(-1,"ROLE_STUDENT");
        Assert.assertNull(result[0]);
        Assert.assertNull(result[0]);
        result = messageService.getAllMessages(1,null);
        Assert.assertNull(result[0]);
        Assert.assertNull(result[0]);
        result = messageService.getAllMessages(1,"");
        Assert.assertNull(result[0]);
        Assert.assertNull(result[0]);
    }
}
