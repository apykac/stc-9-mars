package ru.innopolis.stc9.service.implementation;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.innopolis.stc9.dao.interfaces.AttendanceDao;
import ru.innopolis.stc9.dao.interfaces.LessonsDao;
import ru.innopolis.stc9.dao.interfaces.UserDao;
import ru.innopolis.stc9.pojo.Attendance;
import ru.innopolis.stc9.service.interfaces.AttendanceService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AttendanceServiceImpl implements AttendanceService {

    @Autowired
    private AttendanceDao attendanceDao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private LessonsDao lessonDao;

    public AttendanceServiceImpl() {
    }


    /*public AttendanceServiceImpl(AttendanceDao attendanceDao) {
        this.attendanceDao = attendanceDao;
    }*/

    @Override
    @Transactional
    public void addLessonAttendance(int groupId, int lessonId, int[] studentsList) {
        attendanceDao.addLessonAttendance(lessonId, studentsList);
        Map<Integer, Boolean> savedAttendance = getLessonAttendance(lessonId, groupId);
        for (Map.Entry<Integer, Boolean> entry : savedAttendance.entrySet()) {
            Integer key = entry.getKey();
            Boolean value = false;
            for (int i : studentsList) {
                if (key == i) {
                    value = true;
                }
            }
            Attendance attendance = new Attendance();
            attendance.setUser(userDao.findUserByUserId(key));
            attendance.setLesson(lessonDao.getLessonById(lessonId));
            attendance.setAttended(value);
            attendanceDao.updateAttendance(attendance);
        }
    }

    @Override
    @Transactional
    public void clearLessonAttendance(int lessonId, int groupId) {
        if (lessonId < 1 || groupId < 1) {
            return;
        }
        Map<Integer, Boolean> savedAttendance = getLessonAttendance(lessonId, groupId);
        for (Map.Entry<Integer, Boolean> entry : savedAttendance.entrySet()) {
            Integer key = entry.getKey();
            Attendance attendance = new Attendance();
            attendance.setUser(userDao.findUserByUserId(key));
            attendance.setLesson(lessonDao.getLessonById(lessonId));
            attendance.setAttended(false);
            attendanceDao.updateAttendance(attendance);
        }
    }

    @Override
    @Transactional
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

    @Override
    @Transactional
    public int getNumberOfMissedLessons(int id) {
        return attendanceDao.getNumberOfMissedLessons(id);
    }
}
