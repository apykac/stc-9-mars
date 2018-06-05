package ru.innopolis.stc9.dao;

import ru.innopolis.stc9.pojo.Attendance;

import java.util.List;

public interface AttendanceDao {
    boolean addLessonAttendance(int lessonId, int[] students);

    boolean updateAttendance(Attendance attendance);

    List<Attendance> getLessonAttendance(int lessonId, int groupId);
}
