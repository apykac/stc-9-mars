package ru.innopolis.stc9.dao;

public interface AttendanceDao {
    boolean addLessonAttendance(int lessonId, int[] students);

    int getNumberOfMissedLessons(int id);
}
