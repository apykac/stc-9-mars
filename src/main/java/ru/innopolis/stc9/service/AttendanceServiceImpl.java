package ru.innopolis.stc9.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.innopolis.stc9.dao.AttendanceDao;
import ru.innopolis.stc9.pojo.Attendance;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AttendanceServiceImpl implements AttendanceService {

    @Autowired
    private AttendanceDao attendanceDao;

    //TODO поменять местами groupId и lessonId в аргументах
    @Override
    public void addLessonAttendance(int groupId, int lessonId, int[] studentsList) {
        attendanceDao.addLessonAttendance(lessonId, studentsList);
        Map<Integer, Boolean> savedAttendance = getLessonAttendance(lessonId, groupId);
        for (Map.Entry<Integer, Boolean> entry : savedAttendance.entrySet()) {
            Integer key = entry.getKey();
            Boolean value = entry.getValue();
            value = false;
            for (int i : studentsList) {
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
    public void clearLessonAttendance(int lessonId, int groupId) {
        Map<Integer, Boolean> savedAttendance = getLessonAttendance(lessonId, groupId);
        for (Map.Entry<Integer, Boolean> entry : savedAttendance.entrySet()) {
            Integer key = entry.getKey();
            Attendance attendance = new Attendance();
            attendance.setUserId(key);
            attendance.setLessonId(lessonId);
            attendance.setAttended(false);
            attendanceDao.updateAttendance(attendance);
        }
    }

    @Override
    public Map<Integer, Boolean> getLessonAttendance(int lessonId, int groupId) {
        Map<Integer, Boolean> result = new HashMap<>();
        List<Attendance> attendances = attendanceDao.getLessonAttendance(lessonId, groupId);
        for (Attendance attendance : attendances) {
            result.put(attendance.getUserId(), attendance.isAttended());
        }
        return result;
    }

    @Override
    public int getNumberOfMissedLessons(int id) {
        return attendanceDao.getNumberOfMissedLessons(id);
    }
}
