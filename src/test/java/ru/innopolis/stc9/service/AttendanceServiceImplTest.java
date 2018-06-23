package ru.innopolis.stc9.service;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.powermock.api.mockito.PowerMockito;
import ru.innopolis.stc9.dao.implementation.AttendanceDaoImpl;
import ru.innopolis.stc9.dao.interfaces.AttendanceDao;
import ru.innopolis.stc9.pojo.Attendance;
import ru.innopolis.stc9.service.implementation.AttendanceServiceImpl;
import ru.innopolis.stc9.service.interfaces.AttendanceService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.when;


public class AttendanceServiceImplTest {
    Attendance attendance;
    private AttendanceServiceImpl attendanceService;
    private AttendanceDao attendanceDao;
    private List<Attendance> savedAttendance;
    private Map<Integer, Boolean> lessonAttendance;

    @Before
    public void setUp() {
        attendanceDao = PowerMockito.mock(AttendanceDaoImpl.class);
        attendanceService = new AttendanceServiceImpl(attendanceDao);

        savedAttendance = new ArrayList<>();
        attendance = new Attendance();
        attendance.setUserId(1);
        attendance.setLessonId(1);
        attendance.setAttended(true);
        savedAttendance.add(attendance);

        lessonAttendance = new HashMap<>();
        lessonAttendance.put(1, true);

        PowerMockito.when(attendanceDao.getLessonAttendance(1, 1)).thenReturn(savedAttendance);
    }

    @Test
    public void testCallConstructor() {
        AttendanceService attendanceService = new AttendanceServiceImpl();
    }

    @Test
    public void testAddLessonAttendance() {
        final Attendance[] result = new Attendance[1];
        Attendance testAttendance = attendance;
        testAttendance.setAttended(false);
        PowerMockito.when(attendanceDao.updateAttendance(anyObject())).thenAnswer(new Answer() {
            public Void answer(InvocationOnMock invocation) {
                Object[] args = invocation.getArguments();
                result[0] = (Attendance) args[0];
                return null;
            }
        });
        attendanceService.addLessonAttendance(1, 1, new int[]{1});
        assertTrue(result[0].isAttended());
    }

    @Test
    public void testClearLessonAttendance() {
        final Attendance[] result = new Attendance[1];
        PowerMockito.when(attendanceDao.updateAttendance(anyObject())).thenAnswer(new Answer() {
            public Void answer(InvocationOnMock invocation) {
                Object[] args = invocation.getArguments();
                result[0] = (Attendance) args[0];
                return null;
            }
        });
        attendanceService.clearLessonAttendance(1, 1);
        assertFalse(result[0].isAttended());
    }

    @Test
    public void testClearLessonAttendanceWithReturn() {
        attendanceService.clearLessonAttendance(1, -1);
        attendanceService.clearLessonAttendance(-1, 1);
        Mockito.verify(attendanceDao, never()).updateAttendance(Mockito.anyObject());
    }

    @Test
    public void testGetLessonAttendanceWithWrongArgs() {
        Map<Integer, Boolean> result = attendanceService.getLessonAttendance(0, 0);
        assertTrue(result.size() == 0);
    }

    @Test
    public void testGetLessonAttendance() {
        Map<Integer, Boolean> result = attendanceService.getLessonAttendance(1, 1);
        assertEquals(result, lessonAttendance);
    }

    @Test
    public void testGetNumberOfMissedLessons() {
        when(attendanceDao.getNumberOfMissedLessons(1)).thenReturn(10);
        int number = attendanceService.getNumberOfMissedLessons(1);
        assertTrue(number == 10);
    }
}
