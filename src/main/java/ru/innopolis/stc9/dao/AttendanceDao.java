package ru.innopolis.stc9.dao;

public interface AttendanceDao {
    public boolean addLessonAttendance(int lessonId, int[] students);
}
