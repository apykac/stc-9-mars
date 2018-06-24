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
import ru.innopolis.stc9.dao.mappers.Mapper;
import ru.innopolis.stc9.dao.mappers.UserMapper;
import ru.innopolis.stc9.pojo.Group;
import ru.innopolis.stc9.pojo.User;
import ru.innopolis.stc9.service.implementation.UserServiceImpl;
import ru.innopolis.stc9.service.interfaces.UserService;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@RunWith(PowerMockRunner.class)
@PrepareForTest({User.class, CryptService.class})
public class UserServiceImplTest {
    private Mapper mapper;
    private Group group;
    private User legalUser;
    private UserDao userDao;
    private GroupDao groupDao;
    private UserService userService;

    @Before
    public void setUp() throws IllegalAccessException {
        legalUser = createLegalUser(1, "ROLE_ADMIN", 1, 1);
        group = new Group(1, "1");
        userDao = PowerMockito.mock(UserDaoImpl.class);
        groupDao = PowerMockito.mock(GroupDaoImpl.class);
        userService = new UserServiceImpl(userDao);
        mapper = PowerMockito.mock(UserMapper.class);
        Field fieldMapper = PowerMockito.field(UserServiceImpl.class, "mapper");
        Field fieldUserDao = PowerMockito.field(UserServiceImpl.class, "userDao");
        Field fieldGroupDao = PowerMockito.field(UserServiceImpl.class, "groupDao");
        fieldMapper.set(userService, mapper);
        fieldUserDao.set(userService, userDao);
        fieldGroupDao.set(userService, groupDao);
    }

    User createLegalUser(int id, String role, Integer groupId, int enabled) {
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
        if (groupId!=null) {
            user.setGroup(new Group(groupId, "test" + groupId));
        } else {
            user.setGroup(null);
        }
        return user;
    }

    private MultiValueMap<String, String> createLegalMap(boolean someNulls) {
        MultiValueMap<String, String> map = new HttpHeaders();
        map.put(UserMapper.LOGIN, new ArrayList<>(Collections.singletonList("user1")));
        map.put(UserMapper.HASH, new ArrayList<>(Collections.singletonList("password1")));
        if (!someNulls) {
            map.put(UserMapper.FNAME, new ArrayList<>(Collections.singletonList("FirstName")));
            map.put(UserMapper.SNAME, new ArrayList<>(Collections.singletonList("SecondName")));
        }
        map.put(UserMapper.MNAME, new ArrayList<>(Collections.singletonList("MiddleName")));
        map.put(UserMapper.GROUPID, new ArrayList<>(Collections.singletonList("1")));
        map.put(UserMapper.ENABLED, new ArrayList<>(Collections.singletonList("1")));
        return map;
    }

    private MultiValueMap<String, String> createIllegalMap(boolean someNulls) {
        MultiValueMap<String, String> map = new HttpHeaders();
        map.put(UserMapper.LOGIN, new ArrayList<>(Collections.singletonList("user1")));
        map.put(UserMapper.HASH, new ArrayList<>(Collections.singletonList("password1")));
        if (!someNulls) {
            map.put(UserMapper.FNAME, new ArrayList<>(Collections.singletonList("1")));
            map.put(UserMapper.SNAME, new ArrayList<>(Collections.singletonList("2")));
        }
        map.put(UserMapper.MNAME, new ArrayList<>(Collections.singletonList("3")));
        map.put(UserMapper.GROUPID, new ArrayList<>(Collections.singletonList("-1")));
        map.put(UserMapper.ENABLED, new ArrayList<>(Collections.singletonList("3")));
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
        MultiValueMap<String, String> map = createLegalMap(false);
        PowerMockito.when(groupDao.findGroupById(-1)).thenReturn(null);
        List<String> result1 = userService.isCorrectData(createIllegalMap(false));
        List<String> result2 = userService.isCorrectData(createIllegalMap(true));
        List<String> result3 = userService.isCorrectData(null);
        map.put(UserMapper.GROUPID, new ArrayList<>(Collections.singletonList("s")));
        map.put(UserMapper.ENABLED, new ArrayList<>(Collections.singletonList("s")));
        List<String> result4 = userService.isCorrectData(map);
        Assert.assertEquals(5, result1.size());
        Assert.assertEquals(3, result2.size());
        Assert.assertEquals(0, result3.size());
        Assert.assertEquals(2, result4.size());
    }

    @Test
    public void addUserByParamCorrectDataTest() throws Exception {
        PowerMockito.mockStatic(CryptService.class);
        PowerMockito.when(CryptService.crypting("password1")).thenReturn("password1");
        PowerMockito.whenNew(User.class).withArguments(Mockito.anyString(), Mockito.anyString(), Mockito.anyString(),
                Mockito.anyString(), Mockito.anyString()).thenReturn(legalUser);
        PowerMockito.when(userDao.addUser(Mockito.any(User.class))).thenReturn(true);
        Assert.assertTrue(userService.addUserByParam(createLegalMap(false)));
    }

    @Test
    public void addUserByParamIncorrectDataTest() {
        Assert.assertFalse(userService.addUserByParam(new HttpHeaders()));
        Assert.assertFalse(userService.addUserByParam(null));
    }

    @Test
    public void delUserByIdCorrectDataTest() {
        PowerMockito.when(userDao.delUserById(1)).thenReturn(true);
        Assert.assertTrue(userService.delUserById(1));
        PowerMockito.when(userDao.delUserById(1)).thenReturn(false);
        Assert.assertFalse(userService.delUserById(1));
    }

    @Test
    public void delUserByIdIncorrectDataTest() {
        PowerMockito.when(userDao.delUserById(-1)).thenReturn(false);
        Assert.assertFalse(userService.delUserById(-1));
    }

    @Test
    public void isExistCorrectDataTest() {
        PowerMockito.when(userDao.findLoginByName("user1")).thenReturn(legalUser);
        PowerMockito.when(userDao.findLoginByName("user2")).thenReturn(null);
        Assert.assertTrue(userService.isExist("user1"));
        Assert.assertFalse(userService.isExist("user2"));
    }

    @Test
    public void isExistIncorrectDataTest() {
        Assert.assertFalse(userService.isExist(null));
        Assert.assertFalse(userService.isExist(""));
    }

    @Test
    public void getUserListTest() {
        List<User> list = new ArrayList<>(Arrays.asList(createLegalUser(1, "ROLE_ADMIN", 1, 1),
                createLegalUser(2, "ROLE_STUDENT", 1, 0)));
        PowerMockito.when(userDao.getUsersList()).thenReturn(list);
        Assert.assertEquals(userService.getUserList(), list);
    }

    @Test
    public void findUserByIdCorrectDataTest() {
        PowerMockito.when(userDao.findUserByUserId(1)).thenReturn(legalUser);
        Assert.assertEquals(userService.findUserById(1), legalUser);
    }

    @Test
    public void findUserByIdIncorrectDataTest() {
        PowerMockito.when(userDao.findUserByUserId(2)).thenReturn(null);
        Assert.assertNull(userService.findUserById(-2));
        Assert.assertNull(userService.findUserById(2));
    }

    @Test
    public void findUserByLoginCorrectDataTest() {
        PowerMockito.when(userDao.findLoginByName("user1")).thenReturn(legalUser);
        PowerMockito.when(userDao.findLoginByName("user2")).thenReturn(null);
        Assert.assertNull(userService.findUserByLogin("user2"));
        Assert.assertEquals(userService.findUserByLogin("user1"), legalUser);
    }

    @Test
    public void findUserByLoginIncorrectDataTest() {
        Assert.assertNull(userService.findUserByLogin(""));
        Assert.assertNull(userService.findUserByLogin(null));
    }

    @Test
    public void getStudentsByGroupIdTest() {
        List<User> fullMatch = new ArrayList<>(Arrays.asList(
                createLegalUser(1, "ROLE_STUDENT", 1, 1),
                createLegalUser(2, "ROLE_STUDENT", 1, 0),
                createLegalUser(3, "ROLE_STUDENT", 1, 1)));
        List<User> oneMatch = new ArrayList<>(Arrays.asList(
                createLegalUser(1, "ROLE_STUDENT", 1, 1),
                createLegalUser(2, "ROLE_STUDENT", 3, 0),
                createLegalUser(3, "ROLE_STUDENT", null, 1)));
        List<User> noMatches = new ArrayList<>(Arrays.asList(
                createLegalUser(1, "ROLE_STUDENT", 2, 1),
                createLegalUser(2, "ROLE_STUDENT", 3, 0),
                createLegalUser(3, "ROLE_STUDENT", null, 1)));
        PowerMockito.when(userDao.getAllStudents()).thenReturn(fullMatch);
        Assert.assertEquals(3, userService.getStudentsByGroupId(1).size());
        Assert.assertEquals(userService.getStudentsByGroupId(1), fullMatch);

        PowerMockito.when(userDao.getAllStudents()).thenReturn(oneMatch);
        Assert.assertEquals(1, userService.getStudentsByGroupId(1).size());
        oneMatch.remove(1);
        oneMatch.remove(1);
        Assert.assertEquals(userService.getStudentsByGroupId(1), oneMatch);

        PowerMockito.when(userDao.getAllStudents()).thenReturn(noMatches);
        Assert.assertEquals(0, userService.getStudentsByGroupId(1).size());
        noMatches = new ArrayList<>();
        Assert.assertEquals(userService.getStudentsByGroupId(1), noMatches);
    }

    @Test
    public void getAllStudentsTest() {
        List<User> list = new ArrayList<>(Arrays.asList(
                createLegalUser(1, "ROLE_STUDENT", 1, 1),
                createLegalUser(2, "ROLE_STUDENT", 1, 0),
                createLegalUser(3, "ROLE_STUDENT", 1, 1)));
        PowerMockito.when(userDao.getAllStudents()).thenReturn(list);
        List<User> resultList = userService.getAllStudents();
        Assert.assertEquals(3, resultList.size());
        Assert.assertEquals(resultList, list);
    }

    @Test
    public void checkPasswordOfCurrentAccountCorrectDataTest() {
        PowerMockito.when(userDao.findUserByUserId(1)).thenReturn(legalUser);
        PowerMockito.when(userDao.findUserByUserId(2)).thenReturn(null);
        PowerMockito.mockStatic(CryptService.class);
        PowerMockito.when(CryptService.isMatched("password1", "password1")).thenReturn(true);
        PowerMockito.when(CryptService.isMatched("password1", "password2")).thenReturn(false);
        Assert.assertTrue(userService.checkPasswordOfCurrentAccount(1, "password1"));
        legalUser.setHashPassword("password2");
        Assert.assertFalse(userService.checkPasswordOfCurrentAccount(1, "password1"));
        Assert.assertFalse(userService.checkPasswordOfCurrentAccount(2, Mockito.anyString()));
    }

    @Test
    public void checkPasswordOfCurrentAccountIncorrectDataTest() {
        Assert.assertFalse(userService.checkPasswordOfCurrentAccount(-1, Mockito.anyString()));
        Assert.assertFalse(userService.checkPasswordOfCurrentAccount(2, null));
        Assert.assertFalse(userService.checkPasswordOfCurrentAccount(2, ""));
    }

    @Test
    public void deactivationCurrentAccountTest() {
        PowerMockito.when(userDao.deactivateUser(1)).thenReturn(true);
        Assert.assertTrue(userService.deactivationCurrentAccount(1));
        PowerMockito.when(userDao.deactivateUser(1)).thenReturn(false);
        Assert.assertFalse(userService.deactivationCurrentAccount(1));
    }

    @Test
    public void updateGroupIdCorrectDataTest() {
        PowerMockito.when(userDao.updateGroupId(1, null)).thenReturn(true);
        Assert.assertTrue(userService.updateGroupId(1, null));
        PowerMockito.when(userDao.updateGroupId(1, 3)).thenReturn(false);
        Assert.assertFalse(userService.updateGroupId(1, 3));
    }

    @Test
    public void updateGroupIdIncorrectDataTest() {
        Assert.assertFalse(userService.updateGroupId(-1, null));
        Assert.assertFalse(userService.updateGroupId(3, -1));
    }

    @Test
    public void checkPasswordUpdateIsPossibleCorrectDataTest() {
        MultiValueMap<String, String> map = new HttpHeaders();
        map.put(UserMapper.HASH, new ArrayList<>(Collections.singletonList("password1")));
        map.put("newPassword", new ArrayList<>(Collections.singletonList("1")));
        map.put("repeatNewPassword", new ArrayList<>(Collections.singletonList("1")));
        PowerMockito.when(userDao.findUserByUserId(1)).thenReturn(createLegalUser(1, "ROLE_ADMIN", 1, 1));
        PowerMockito.mockStatic(CryptService.class);
        PowerMockito.when(CryptService.isMatched("password1", "password1")).thenReturn(true);
        Assert.assertEquals("", userService.checkPasswordUpdateIsPossible(map, legalUser));
    }

    @Test
    public void checkPasswordUpdateIsPossibleCorrectDataWrongOldPassTest() {
        MultiValueMap<String, String> map = new HttpHeaders();
        map.put(UserMapper.HASH, new ArrayList<>(Collections.singletonList("password1")));
        map.put("newPassword", new ArrayList<>(Collections.singletonList("1")));
        map.put("repeatNewPassword", new ArrayList<>(Collections.singletonList("1")));
        User userFounded = createLegalUser(1, "ROLE_ADMIN", 1, 1);
        userFounded.setHashPassword("password2");
        PowerMockito.when(userDao.findUserByUserId(1)).thenReturn(userFounded);
        PowerMockito.mockStatic(CryptService.class);
        PowerMockito.when(CryptService.isMatched("password1", "password2")).thenReturn(false);
        Assert.assertEquals("Wrong old password", userService.checkPasswordUpdateIsPossible(map, legalUser));
    }

    @Test
    public void checkPasswordUpdateIsPossibleCorrectDataNewPassNotMatchedTest() {
        MultiValueMap<String, String> map = new HttpHeaders();
        map.put(UserMapper.HASH, new ArrayList<>(Collections.singletonList("password1")));
        map.put("newPassword", new ArrayList<>(Collections.singletonList("1")));
        map.put("repeatNewPassword", new ArrayList<>(Collections.singletonList("2")));
        PowerMockito.when(userDao.findUserByUserId(1)).thenReturn(createLegalUser(1, "ROLE_ADMIN", 1, 1));
        PowerMockito.mockStatic(CryptService.class);
        PowerMockito.when(CryptService.isMatched("password1", "password1")).thenReturn(true);
        Assert.assertEquals("Passwords not match", userService.checkPasswordUpdateIsPossible(map, legalUser));
    }

    @Test
    public void checkPasswordUpdateIsPossibleIncorrectDataTest() {
        MultiValueMap<String, String> map = new HttpHeaders();
        map.put(UserMapper.HASH, new ArrayList<>(Collections.singletonList("password1")));
        PowerMockito.when(userDao.findUserByUserId(1)).thenReturn(createLegalUser(1, "ROLE_ADMIN", 1, 1));
        PowerMockito.mockStatic(CryptService.class);
        PowerMockito.when(CryptService.isMatched("password1", "password1")).thenReturn(true);
        Assert.assertEquals("Wrong old password", userService.checkPasswordUpdateIsPossible(null, legalUser));
        Assert.assertEquals("Wrong old password", userService.checkPasswordUpdateIsPossible(new HttpHeaders(), legalUser));
        Assert.assertEquals("Wrong old password", userService.checkPasswordUpdateIsPossible(map, null));
        Assert.assertEquals("Wrong old password", userService.checkPasswordUpdateIsPossible(null, legalUser));
        Assert.assertEquals("Passwords not match", userService.checkPasswordUpdateIsPossible(map, legalUser));
        map.put("newPassword", new ArrayList<>(Collections.singletonList("")));
        Assert.assertEquals("Passwords not match", userService.checkPasswordUpdateIsPossible(map, legalUser));
        map.put("newPassword", new ArrayList<>(Collections.singletonList("1")));
        Assert.assertEquals("Passwords not match", userService.checkPasswordUpdateIsPossible(map, legalUser));
    }

    @Test
    public void editUserCorrectDataTest() {
        MultiValueMap<String, String> map = createLegalMap(false);
        map.put(UserMapper.HASH, new ArrayList<>(Collections.singletonList("password1")));
        map.put("newPassword", new ArrayList<>(Collections.singletonList("1")));
        map.put("repeatNewPassword", new ArrayList<>(Collections.singletonList("1")));
        PowerMockito.when(userDao.findUserByUserId(1)).thenReturn(legalUser);
        PowerMockito.when(mapper.getByParam(Mockito.anyObject())).thenReturn(legalUser);
        PowerMockito.when(groupDao.findGroupById(1)).thenReturn(new Group(1, "1"));
        PowerMockito.mockStatic(CryptService.class);
        PowerMockito.when(CryptService.isMatched("password1", "password1")).thenReturn(true);
        PowerMockito.when(userDao.updateUserByFIOL(legalUser)).thenReturn(true);
        PowerMockito.when(userDao.updateUserPassword(legalUser)).thenReturn(true);
        Object[] result = userService.editUser(map);
        List<String> success = new ArrayList<>(Arrays.asList("Updating profile FIO success successfully",
                "Updating profile password successfully"));
        Assert.assertEquals(new ArrayList<>(), result[0]);
        Assert.assertEquals(success, result[1]);
        Assert.assertTrue((Boolean) result[2]);
    }

    @Test
    public void editUserCorrectDataWithCheckPassSuccessWithErrorsTest() {
        MultiValueMap<String, String> map = createLegalMap(false);
        map.put(UserMapper.HASH, new ArrayList<>(Collections.singletonList("password1")));
        map.put("newPassword", new ArrayList<>(Collections.singletonList("1")));
        map.put("repeatNewPassword", new ArrayList<>(Collections.singletonList("1")));
        PowerMockito.when(userDao.findUserByUserId(1)).thenReturn(legalUser);
        PowerMockito.when(mapper.getByParam(Mockito.anyObject())).thenReturn(legalUser);
        PowerMockito.when(groupDao.findGroupById(1)).thenReturn(new Group(1, "1"));
        PowerMockito.mockStatic(CryptService.class);
        PowerMockito.when(CryptService.isMatched("password1", "password1")).thenReturn(true);
        PowerMockito.when(userDao.updateUserByFIOL(legalUser)).thenReturn(false);
        PowerMockito.when(userDao.updateUserPassword(legalUser)).thenReturn(false);
        Object[] result = userService.editUser(map);
        List<String> errors = new ArrayList<>(Arrays.asList("Invalid/Exist Login",
                "Updating profile password error"));
        Assert.assertEquals(errors, result[0]);
        Assert.assertEquals(new ArrayList<>(), result[1]);
        Assert.assertTrue(!(Boolean) result[2]);
    }

    @Test
    public void editUserCorrectDataWithoutCheckPassSuccessWithErrorsTest() {
        MultiValueMap<String, String> map = createLegalMap(false);
        map.put(UserMapper.HASH, new ArrayList<>(Collections.singletonList("password1")));
        map.put("newPassword", new ArrayList<>(Collections.singletonList("1")));
        map.put("repeatNewPassword", new ArrayList<>(Collections.singletonList("2")));
        PowerMockito.when(userDao.findUserByUserId(1)).thenReturn(legalUser);
        PowerMockito.when(mapper.getByParam(Mockito.anyObject())).thenReturn(legalUser);
        PowerMockito.when(groupDao.findGroupById(1)).thenReturn(new Group(1, "1"));
        PowerMockito.mockStatic(CryptService.class);
        PowerMockito.when(CryptService.isMatched("password1", "password1")).thenReturn(true);
        PowerMockito.when(userDao.updateUserByFIOL(legalUser)).thenReturn(false);
        Object[] result = userService.editUser(map);
        List<String> errors = new ArrayList<>(Arrays.asList("Invalid/Exist Login", "Passwords not match"));
        Assert.assertEquals(errors, result[0]);
        Assert.assertEquals(new ArrayList<>(), result[1]);
        Assert.assertTrue(!(Boolean) result[2]);
    }

    @Test
    public void editUserIncorrectDataTest() {
        Object[] result1 = userService.editUser(null);
        Object[] result2 = userService.editUser(new HttpHeaders());
        Object[] emtpyObj = new Object[3];
        Assert.assertEquals(emtpyObj[0], result1[0]);
        Assert.assertEquals(emtpyObj[1], result1[1]);
        Assert.assertEquals(emtpyObj[2], result1[2]);
        Assert.assertEquals(emtpyObj[0], result2[0]);
        Assert.assertEquals(emtpyObj[1], result2[1]);
        Assert.assertEquals(emtpyObj[2], result2[2]);
    }
}
