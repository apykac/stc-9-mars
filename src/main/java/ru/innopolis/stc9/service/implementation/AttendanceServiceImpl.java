package ru.innopolis.stc9.service.implementation;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.innopolis.stc9.dao.interfaces.AttendanceDao;
import ru.innopolis.stc9.pojo.Attendance;
import ru.innopolis.stc9.service.interfaces.AttendanceService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AttendanceServiceImpl implements AttendanceService {

    @Autowired
    private AttendanceDao attendanceDao;

    public AttendanceServiceImpl() {
    }

    public AttendanceServiceImpl(AttendanceDao attendanceDao) {
        this.attendanceDao = attendanceDao;
    }

    @Override
    public void addLessonAttendance(long groupId, long lessonId, long[] studentsList) {
        attendanceDao.addLessonAttendance(lessonId, studentsList);
        Map<Long, Boolean> savedAttendance = getLessonAttendance(lessonId, groupId);
        for (Map.Entry<Long, Boolean> entry : savedAttendance.entrySet()) {
            Long key = entry.getKey();
            Boolean value = false;
            for (long i : studentsList) {
                if (key == i) {
                    value = true;
                }
            }
            Attendance attendance = new Attendance();
            attendance.setUserId(key);
            attendance.setLessonId(lessonId);
            attendance.setAttended(value);
            attendanceDao.updateAttendance(attendance);
        }
    }

    @Override
    public void clearLessonAttendance(long lessonId, long groupId) {
        if (lessonId < 1 || groupId < 1) {
            return;
        }
        Map<Long, Boolean> savedAttendance = getLessonAttendance(lessonId, groupId);
        for (Map.Entry<Long, Boolean> entry : savedAttendance.entrySet()) {
            Long key = entry.getKey();
            Attendance attendance = new Attendance();
            attendance.setUserId(key);
            attendance.setLessonId(lessonId);
            attendance.setAttended(false);
            attendanceDao.updateAttendance(attendance);
        }
    }

    @Override
    public Map<Long, Boolean> getLessonAttendance(long lessonId, long groupId) {
        Map<Long, Boolean> result = new HashMap<>();
        if (lessonId < 1 || groupId < 1) {
            return result;
        }
        List<Attendance> attendances = attendanceDao.getLessonAttendance(lessonId, groupId);
        for (Attendance attendance : attendances) {
            result.put(attendance.getUserId(), attendance.isAttended());
        }
        return result;
    }

    @Override
    public int getNumberOfMissedLessons(long id) {
        return attendanceDao.getNumberOfMissedLessons(id);
    }
}
