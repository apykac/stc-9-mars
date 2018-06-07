package ru.innopolis.stc9.dao.interfaces;

import ru.innopolis.stc9.pojo.Attendance;

import java.util.List;

public interface AttendanceDao {
    boolean addLessonAttendance(int lessonId, int[] students);

    int getNumberOfMissedLessons(int id);

    boolean updateAttendance(Attendance attendance);

    List<Attendance> getLessonAttendance(int lessonId, int groupId);
}
