package ru.innopolis.stc9.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.innopolis.stc9.dao.AttendanceDao;

import java.util.List;

@Service
public class AttendanceServiceImpl implements AttendanceService {

    @Autowired
    private AttendanceDao attendanceDao;

    @Override
    public boolean addLessonAttendance(int lessonId, int[] studentsList) {
        return attendanceDao.addLessonAttendance(lessonId, studentsList);
    }

    @Override
    public List<Integer> getLessonAttendance(int lessonId, int groupId) {
        return attendanceDao.getLessonAttendance(lessonId, groupId);
    }
}
