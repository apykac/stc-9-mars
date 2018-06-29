package ru.innopolis.stc9.service;

import org.junit.Before;
import org.junit.Test;
import org.powermock.api.mockito.PowerMockito;
import ru.innopolis.stc9.dao.implementation.AttendanceDaoImpl;
import ru.innopolis.stc9.dao.interfaces.AttendanceDao;
import ru.innopolis.stc9.pojo.Attendance;
import ru.innopolis.stc9.service.implementation.AttendanceServiceImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


public class AttendanceServiceImplTest {
    private Attendance attendance;
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
        attendance.setAttended(true);
        savedAttendance.add(attendance);

        lessonAttendance = new HashMap<>();
        lessonAttendance.put(1, true);

        PowerMockito.when(attendanceDao.getLessonAttendance(1, 1)).thenReturn(savedAttendance);
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
}
