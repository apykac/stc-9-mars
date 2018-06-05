package ru.innopolis.stc9.service;

import java.util.Map;

public interface AttendanceService {
    void addLessonAttendance(int groupId, int lessonId, int[] studentsList);

    void clearLessonAttendance(int lessonId, int groupId);

    Map<Integer, Boolean> getLessonAttendance(int lessonId, int groupId);
}
