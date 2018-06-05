package ru.innopolis.stc9.service;


import org.springframework.stereotype.Service;
import ru.innopolis.stc9.dao.AttendanceDao;
import ru.innopolis.stc9.dao.AttendanceDaoImpl;

@Service
public class AttendanceServiceImpl implements AttendanceService {

    //@Autowired
    private AttendanceDao attendanceDao = new AttendanceDaoImpl();

    @Override
    public boolean addLessonAttendance(int lessonId, int[] studentsList) {
        return attendanceDao.addLessonAttendance(lessonId, studentsList);
    }

    @Override
    public int getNumberOfMissedLessons(int id) {
        return attendanceDao.getNumberOfMissedLessons(id);
    }
}
