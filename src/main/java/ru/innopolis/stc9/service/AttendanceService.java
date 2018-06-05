package ru.innopolis.stc9.service;

public interface AttendanceService {
    boolean addLessonAttendance(int lessonId, int[] studentsList);
}
