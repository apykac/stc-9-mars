package ru.innopolis.stc9.service;

import org.junit.Before;
import org.junit.Test;
import ru.innopolis.stc9.dao.implementation.LessonsDaoImpl;
import ru.innopolis.stc9.dao.interfaces.LessonsDao;
import ru.innopolis.stc9.pojo.Lessons;
import ru.innopolis.stc9.service.implementation.LessonsServiceImpl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class LessonsServiceImplTest {
    private LessonsServiceImpl lessonsService;
    private java.sql.Date dateSql;
    private Lessons lesson;

    @Before
    public void setUp() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        java.util.Date date = sdf.parse("2018-01-01");
        dateSql = new java.sql.Date(date.getTime());

        lesson = new Lessons(1, dateSql, "some");
        List<Lessons> list = new ArrayList<>();
        list.add(lesson);

        LessonsDao mockLessonDao = mock(LessonsDaoImpl.class);
        when(mockLessonDao.addLesson(lesson)).thenReturn(true);
        when(mockLessonDao.deleteLesson(1)).thenReturn(true);
        when(mockLessonDao.deleteLesson(2)).thenReturn(false);
        when(mockLessonDao.findAllLessonsWithSubjects()).thenReturn(list);
        lessonsService = new LessonsServiceImpl(mockLessonDao);
    }

    @Test
    public void addLessonTestNullLesson() {
        Lessons lesson = new Lessons(0, null, null);
        boolean result = lessonsService.addLesson(lesson);
        assertFalse(result);
    }

    @Test
    public void addLessonTestLessonDaoFalse() {
        Lessons lesson = new Lessons(1, null, "not some");
        boolean result = lessonsService.addLesson(lesson);
        assertFalse(result);
    }

    @Test
    public void addLessonTestLessonDaoTrue() {
        boolean result = lessonsService.addLesson(lesson);
        assertTrue(result);
    }

    @Test
    public void deleteLessonTestIfTrue() {
        boolean result = lessonsService.deleteLesson(1);
        assertTrue(result);
    }

    @Test
    public void deleteLessonTestIfFalse() {
        boolean result = lessonsService.deleteLesson(2);
        assertFalse(result);
    }

    @Test
    public void findAllLessonsTest() {
        List<Lessons> result = lessonsService.findAllLessonsWithSubjects();
        assertEquals(result.get(0).getName(), "some");
        assertEquals(result.get(0).getDate(), dateSql);
        assertEquals(result.get(0).getSubject().getId(), 1);
    }

    @Test
    public void stringToDateTest() {
        java.sql.Date result = lessonsService.stringToDate("2018-01-01");
        assertEquals(result, dateSql);
    }

    @Test
    public void stringToDateTest2() {
        java.sql.Date result = lessonsService.stringToDate("01/01/2018");
        assertNotEquals(result, dateSql);
    }
}
