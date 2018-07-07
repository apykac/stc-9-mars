package ru.innopolis.stc9.dao.interfaces;

import ru.innopolis.stc9.pojo.Attendance;

import java.util.List;

public interface AttendanceDao {
    boolean addLessonAttendance(int lessonId, int[] students);

    List<Attendance> getLessonAttendance(int lessonId, int groupId);

    boolean deleteAttendance(Attendance attendance);
}
