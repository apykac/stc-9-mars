package ru.innopolis.stc9.service;

import org.junit.Before;
import org.junit.Test;
import ru.innopolis.stc9.dao.ProgressDao;
import ru.innopolis.stc9.dao.ProgressDaoImpl;
import ru.innopolis.stc9.pojo.Lessons;
import ru.innopolis.stc9.pojo.Progress;
import ru.innopolis.stc9.pojo.User;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ProgressServiceImplTest {
    private ProgressServiceImpl progressService;
    private List<Progress> progressList;
    private LessonsService lessonsServiceMock;
    private AttendanceService attendanceServiceMock;
    private UserService userServiceMock;
    private ProgressDao progressDaoMock;
    private User user;

    @Before
    public void setUp() throws Exception {
        Progress progress = new Progress(1, 4, "some", "some", "some", null, "some", "some", "some");
        progressList = new ArrayList<>();
        progressList.add(progress);
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
        when(progressDaoMock.getProgress()).thenReturn(progressList);
        assertEquals(progressService.getProgress(0, 5, "some"), progressList);
    }

    @Test
    public void getLessonsTest() {
        Lessons lesson = new Lessons(1, null, "some");
        List<Lessons> lessonsList = new ArrayList<>();
        lessonsList.add(lesson);
        when(lessonsServiceMock.findAllLessons()).thenReturn(lessonsList);
        assertEquals(progressService.getLessons(), lessonsList);
    }

    @Test
    public void getNumberOfMissedLessonsTest() {
        when(attendanceServiceMock.getNumberOfMissedLessons(1)).thenReturn(0);
        assertEquals(progressService.getNumberOfMissedLessons("some"), 0);
    }
}
