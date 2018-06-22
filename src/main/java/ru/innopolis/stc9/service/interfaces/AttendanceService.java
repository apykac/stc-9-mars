package ru.innopolis.stc9.service.interfaces;

import java.util.Map;

public interface AttendanceService {
    void addLessonAttendance(long groupId, long lessonId, long[] studentsList);

    void clearLessonAttendance(long lessonId, long groupId);

    Map<Long, Boolean> getLessonAttendance(long lessonId, long groupId);

    int getNumberOfMissedLessons(long id);
}
