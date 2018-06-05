package ru.innopolis.stc9.service;

import java.util.List;

public interface AttendanceService {
    public boolean addLessonAttendance(int lessonId, int[] studentsList);

    public List<Integer> getLessonAttendance(int lessonId, int groupId);
}
