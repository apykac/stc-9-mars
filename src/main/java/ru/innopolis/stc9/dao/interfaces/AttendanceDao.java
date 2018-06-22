package ru.innopolis.stc9.dao.interfaces;

import ru.innopolis.stc9.pojo.Attendance;

import java.util.List;

public interface AttendanceDao {
    boolean addLessonAttendance(long lessonId, long[] students);

    int getNumberOfMissedLessons(long id);

    boolean updateAttendance(Attendance attendance);

    List<Attendance> getLessonAttendance(long lessonId, long groupId);
}
