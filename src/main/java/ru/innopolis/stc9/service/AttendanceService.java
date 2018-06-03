package ru.innopolis.stc9.service;

public interface AttendanceService {
    public boolean addLessonAttendance(int lessonId, int[] studentsList);
}
