package ru.innopolis.stc9.service;

import java.util.List;

public interface AttendanceService {
    List<Integer> getLessonAttendance(int lessonId, int groupId);

    boolean addLessonAttendance(int lessonId, int[] studentsList);

    int getNumberOfMissedLessons(int id);
}
