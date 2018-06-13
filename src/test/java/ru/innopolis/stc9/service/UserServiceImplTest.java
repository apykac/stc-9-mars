package ru.innopolis.stc9.service;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.http.HttpHeaders;
import org.springframework.util.MultiValueMap;
import ru.innopolis.stc9.dao.implementation.GroupDaoImpl;
import ru.innopolis.stc9.dao.implementation.UserDaoImpl;
import ru.innopolis.stc9.dao.interfaces.GroupDao;
import ru.innopolis.stc9.dao.interfaces.UserDao;
import ru.innopolis.stc9.pojo.Group;
import ru.innopolis.stc9.pojo.User;
import ru.innopolis.stc9.service.implementation.UserServiceImpl;
import ru.innopolis.stc9.service.interfaces.UserService;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RunWith(PowerMockRunner.class)
@PrepareForTest({User.class, CryptService.class})
public class UserServiceImplTest {
    private Group group;
    private User legalUser;
    private User illegalUser;
    private UserDao userDao;
    private GroupDao groupDao;
    private UserService userService;
    private MultiValueMap<String, String> incParam;

    @Before
    public void setUp() throws IllegalAccessException {
        group = new Group(1, "1");
        userDao = PowerMockito.mock(UserDaoImpl.class);
        groupDao = PowerMockito.mock(GroupDaoImpl.class);
        userService = new UserServiceImpl(userDao);
        Field fieldUserDao = PowerMockito.field(UserServiceImpl.class, "userDao");
        Field fieldGroupDao = PowerMockito.field(UserServiceImpl.class, "groupDao");
        fieldUserDao.set(userService, userDao);
        fieldGroupDao.set(userService, groupDao);
    }

    private User createLegalUser(int id, String role, Integer groupId, int enabled) {
        User user = new User();
        user.setId(id);
        user.setLogin("user" + id);
        user.setHashPassword("password" + id);
        user.setPermissionGroup(role);
        user.setFirstName("FirstName");
        user.setSecondName("SecondName");
        user.setMiddleName("MiddleName");
        user.setGroupId(groupId);
        user.setEnabled(enabled);
        return user;
    }

    private User createIllegalUser() {
        User user = new User();
        user.setPermissionGroup(null);
        user.setEnabled(-1);
        return user;
    }

    private MultiValueMap<String, String> createLegalMap(boolean someNulls) {
        MultiValueMap<String, String> map = new HttpHeaders();
        map.put("login", new ArrayList<>(Collections.singletonList("user1")));
        map.put("hash_password", new ArrayList<>(Collections.singletonList("password1")));
        if (!someNulls) {
            map.put("first_name", new ArrayList<>(Collections.singletonList("FirstName")));
            map.put("second_name", new ArrayList<>(Collections.singletonList("SecondName")));
        }
        map.put("middle_name", new ArrayList<>(Collections.singletonList("MiddleName")));
        map.put("group_id", new ArrayList<>(Collections.singletonList("1")));
        map.put("enabled", new ArrayList<>(Collections.singletonList("1")));
        return map;
    }

    private MultiValueMap<String, String> createIllegalMap(boolean someNulls) {
        MultiValueMap<String, String> map = new HttpHeaders();
        map.put("login", new ArrayList<>(Collections.singletonList("user1")));
        map.put("hash_password", new ArrayList<>(Collections.singletonList("password1")));
        if (!someNulls) {
            map.put("first_name", new ArrayList<>(Collections.singletonList("1")));
            map.put("second_name", new ArrayList<>(Collections.singletonList("2")));
        }
        map.put("middle_name", new ArrayList<>(Collections.singletonList("3")));
        map.put("group_id", new ArrayList<>(Collections.singletonList("-1")));
        map.put("enabled", new ArrayList<>(Collections.singletonList("3")));
        return map;
    }

    @Test
    public void isCorrectDataWithCorrectDataTest() {
        PowerMockito.when(groupDao.findGroupById(1)).thenReturn(group);
        List<String> result1 = userService.isCorrectData(createLegalMap(false));
        List<String> result2 = userService.isCorrectData(createLegalMap(true));
        Assert.assertEquals(0, result1.size());
        Assert.assertEquals(0, result2.size());
    }

    @Test
    public void isCorrectDataWithIncorrectDataTest() {
        PowerMockito.when(groupDao.findGroupById(-1)).thenReturn(null);
        List<String> result1 = userService.isCorrectData(createIllegalMap(false));
        List<String> result2 = userService.isCorrectData(createIllegalMap(true));
        List<String> result3 = userService.isCorrectData(null);
        Assert.assertEquals(5, result1.size());
        Assert.assertEquals(3, result2.size());
        Assert.assertEquals(0,result3.size());
    }

    @Test
    public void addUserByParamCorrectDataTest() throws Exception {
        legalUser = createLegalUser(1, "ROLE_ADMIN", 1, 1);
        PowerMockito.mockStatic(CryptService.class);
        PowerMockito.when(CryptService.crypting("password1")).thenReturn("password1");
        PowerMockito.whenNew(User.class).withArguments(Mockito.anyString(), Mockito.anyString(), Mockito.anyString(),
                Mockito.anyString(), Mockito.anyString()).thenReturn(legalUser);
        PowerMockito.when(userDao.addUser(Mockito.any(User.class))).thenReturn(true);
        Assert.assertTrue(userService.addUserByParam(createLegalMap(false)));
    }

    @Test
    public void addUserByParamIncorrectDataTest(){
        Assert.assertFalse(userService.addUserByParam(new HttpHeaders()));
        Assert.assertFalse(userService.addUserByParam(null));
    }
}
