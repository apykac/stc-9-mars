package ru.innopolis.stc9.service.interfaces;

import java.util.Map;

public interface AttendanceService {
    void addLessonAttendance(int groupId, int lessonId, int[] studentsList);

    void clearLessonAttendance(int lessonId, int groupId);

    void deleteLessonAttendance(int lessonId, int groupId);

    Map<Integer, Boolean> getLessonAttendance(int lessonId, int groupId);

    int getNumberOfMissedLessons(int id);
}
