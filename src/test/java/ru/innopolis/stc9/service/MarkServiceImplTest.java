package ru.innopolis.stc9.service;

import org.junit.Before;
import org.junit.Test;
import org.powermock.api.mockito.PowerMockito;
import ru.innopolis.stc9.dao.implementation.LessonsDaoImpl;
import ru.innopolis.stc9.dao.implementation.MarkDaoImpl;
import ru.innopolis.stc9.dao.implementation.UserDaoImpl;
import ru.innopolis.stc9.dao.interfaces.LessonsDao;
import ru.innopolis.stc9.dao.interfaces.MarkDao;
import ru.innopolis.stc9.dao.interfaces.UserDao;
import ru.innopolis.stc9.pojo.Lessons;
import ru.innopolis.stc9.pojo.Mark;
import ru.innopolis.stc9.pojo.User;
import ru.innopolis.stc9.service.implementation.MarkServiceImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.powermock.api.mockito.PowerMockito.mock;

public class MarkServiceImplTest {
    String fullName;
    private MarkServiceImpl markService;
    private MarkServiceImpl markServiceMock;
    private MarkDao markDao;
    private UserDao userDao;
    private LessonsDao lessonDao;
    private Mark mark;
    private User user;
    private Lessons lesson;
    private List<Mark> marksList;
    private Map<String, Mark> getMarksByLessonIdResult;

    @Before
    public void setUp() throws IllegalAccessException {
        mark = new Mark(1, 5, 1, 1, "Acceptable");
        user = new User("admin", "123", "Admin", "Adminov", "Adminovich");
        lesson = new Lessons(1, 1, "Subject", null, "Lesson");
        fullName = "Adminov Admin Adminovich";
        getMarksByLessonIdResult = new HashMap<String, Mark>();
        getMarksByLessonIdResult.put(fullName, mark);
        markDao = mock(MarkDaoImpl.class);
        userDao = mock(UserDaoImpl.class);
        lessonDao = mock(LessonsDaoImpl.class);
        marksList = new ArrayList<>();
        marksList.add(mark);
        markService = new MarkServiceImpl(markDao, userDao, lessonDao);


        PowerMockito.when(markDao.getMarkById(1)).thenReturn(mark);
        PowerMockito.when(lessonDao.getLessonById(1)).thenReturn(lesson);
        PowerMockito.when(markDao.getMarksByLessonId(1)).thenReturn(marksList);
        PowerMockito.when(userDao.findUserByUserId(1)).thenReturn(user);
    }

    @Test
    public void GetMarksByLessonIdLessThanOneTest() {
        Map<String, Mark> result = markService.getMarksByLessonId(0);
        assertTrue(result.size() == 0);
    }

    @Test
    public void getMarksByLessonId() throws Exception {
        Map<String, Mark> result = markService.getMarksByLessonId(1);
        assertEquals(result, getMarksByLessonIdResult);
    }

    @Test
    public void getMarkByIdTest() {
        assertEquals(markService.getMarkById(1), mark);
    }

    @Test
    public void updateMarkTest() {
        PowerMockito.when(markDao.updateMark(mark)).thenReturn(true);
        assertTrue(markService.updateMark(mark));
    }

    @Test
    public void getFullStudentNameInOneStringTest() {
        String result = markService.getFullStudentNameInOneString(1);
        assertEquals(result, fullName);
    }

    @Test
    public void getLessonNameByMarkIdTest() {
        String result = markService.getLessonNameByMarkId(1);
        assertEquals(result, lesson.getName());
    }

    @Test
    public void getLessonNameByLessonIdTest() {
        String result = markService.getLessonNameByLessonId(1);
        assertEquals(result, lesson.getName());
    }

    @Test
    public void getFullStudentNameWhenParameterIsNullTest() {
        String result = markService.getFullStudentName(null);
        assertEquals("", result);
    }

    @Test
    public void getFullStudentNameTest() {
        String result = markService.getFullStudentName(user);
        assertEquals(result, fullName);
    }

}
