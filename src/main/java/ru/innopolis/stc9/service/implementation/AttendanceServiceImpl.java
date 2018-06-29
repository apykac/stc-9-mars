package ru.innopolis.stc9.service.implementation;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.innopolis.stc9.dao.interfaces.AttendanceDao;
import ru.innopolis.stc9.pojo.Attendance;
import ru.innopolis.stc9.service.interfaces.AttendanceService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class AttendanceServiceImpl implements AttendanceService {
    private AttendanceDao attendanceDao;

    @Autowired
    public AttendanceServiceImpl(AttendanceDao attendanceDao) {
        this.attendanceDao = attendanceDao;
    }

    @Override
    public void addLessonAttendance(int groupId, int lessonId, int[] studentsList) {
        deleteLessonAttendance(lessonId, groupId);
        attendanceDao.addLessonAttendance(lessonId, studentsList);
    }


    @Override
    public void deleteLessonAttendance(int lessonId, int groupId) {
        if (lessonId < 1 || groupId < 1) {
            return;
        }
        List<Attendance> lessonAttendance = attendanceDao.getLessonAttendance(lessonId, groupId);
        for (Attendance attendance : lessonAttendance) {
            attendanceDao.deleteAttendance(attendance);
        }
    }

    @Override
    public Map<Integer, Boolean> getLessonAttendance(int lessonId, int groupId) {
        Map<Integer, Boolean> result = new HashMap<>();
        if (lessonId < 1 || groupId < 1) {
            return result;
        }
        List<Attendance> attendances = attendanceDao.getLessonAttendance(lessonId, groupId);
        for (Attendance attendance : attendances) {
            result.put(attendance.getUser().getId(), attendance.isAttended());
        }
        return result;
    }
}
