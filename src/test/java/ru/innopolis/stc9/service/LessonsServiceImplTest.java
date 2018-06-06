package ru.innopolis.stc9.service;

import org.junit.Before;
import org.junit.Test;
import ru.innopolis.stc9.dao.LessonsDao;
import ru.innopolis.stc9.dao.LessonsDaoImpl;
import ru.innopolis.stc9.pojo.Lessons;

import java.text.ParseException;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class LessonsServiceImplTest {
    private LessonsServiceImpl lessonsService;
    private Lessons lesson;

    @Before
    public void setUp() throws ParseException {
        lesson = new Lessons(1, null, "some");

        LessonsDao mockLessonDao = mock(LessonsDaoImpl.class);
        when(mockLessonDao.addLesson(lesson)).thenReturn(true);
        when(mockLessonDao.deleteLesson(1)).thenReturn(true);
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
}
