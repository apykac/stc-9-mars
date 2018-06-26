package ru.innopolis.stc9.service;

import org.junit.Before;
import org.junit.Test;
import ru.innopolis.stc9.dao.implementation.ProgressDaoImpl;
import ru.innopolis.stc9.dao.interfaces.ProgressDao;
import ru.innopolis.stc9.pojo.Lessons;
import ru.innopolis.stc9.pojo.Mark;
import ru.innopolis.stc9.pojo.User;
import ru.innopolis.stc9.service.implementation.AttendanceServiceImpl;
import ru.innopolis.stc9.service.implementation.LessonsServiceImpl;
import ru.innopolis.stc9.service.implementation.ProgressServiceImpl;
import ru.innopolis.stc9.service.implementation.UserServiceImpl;
import ru.innopolis.stc9.service.interfaces.AttendanceService;
import ru.innopolis.stc9.service.interfaces.LessonsService;
import ru.innopolis.stc9.service.interfaces.UserService;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ProgressServiceImplTest {
    private ProgressServiceImpl progressService;
    private List<Mark> markList;
    private LessonsService lessonsServiceMock;
    private AttendanceService attendanceServiceMock;
    private UserService userServiceMock;
    private ProgressDao progressDaoMock;
    private User user;

    @Before
    public void setUp() throws Exception {
        Mark mark = new Mark();//(1, 4, "some", "some", "some", null, "some", "some", "some");
        markList = new ArrayList<>();
        markList.add(mark);
        user = new User("some", "some", "some", "some", "some");

        progressDaoMock = mock(ProgressDaoImpl.class);
        userServiceMock = mock(UserServiceImpl.class);
        lessonsServiceMock = mock(LessonsServiceImpl.class);
        attendanceServiceMock = mock(AttendanceServiceImpl.class);

        when(userServiceMock.findUserByLogin("some")).thenReturn(user);

        progressService = new ProgressServiceImpl(progressDaoMock, userServiceMock, lessonsServiceMock, attendanceServiceMock);
    }


    @Test
    public void getAmountMarksTest() {
        assertEquals(progressService.getAmountMarks("some").size(), 6);
    }

    @Test
    public void getProgressTest() {
        when(progressDaoMock.getMark()).thenReturn(markList);
        assertEquals(progressService.getProgress(0, 5, "some"), markList);
    }

    @Test
    public void getLessonsTestWithNotStudent() {
        user.setPermissionGroup("ROLE_ADMIN");
        List<Mark> beforeMethodList = new ArrayList<>(markList);
        when(progressDaoMock.getMark()).thenReturn(markList);
        assertEquals(beforeMethodList, progressService.getProgress(0, 5, "some"));
    }

    @Test
    public void getLessonsTest() {
        Lessons lesson = new Lessons(); //new Lessons(1, null, "some");
        List<Lessons> lessonsList = new ArrayList<>();
        lessonsList.add(lesson);
        when(lessonsServiceMock.findAllLessonsWithSubjects()).thenReturn(lessonsList);
        assertEquals(progressService.getLessons(), lessonsList);
    }

    @Test
    public void getNumberOfMissedLessonsTest() {
        when(attendanceServiceMock.getNumberOfMissedLessons(1)).thenReturn(0);
        assertEquals(progressService.getNumberOfMissedLessons("some"), 0);
    }
}
