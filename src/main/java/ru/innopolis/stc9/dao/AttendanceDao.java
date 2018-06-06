package ru.innopolis.stc9.dao;

import java.util.List;

public interface AttendanceDao {
    List<Integer> getLessonAttendance(int lessonId, int groupId);

    boolean addLessonAttendance(int lessonId, int[] students);

    int getNumberOfMissedLessons(int id);
}
