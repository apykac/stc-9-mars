package ru.innopolis.stc9.dao;

import java.util.List;

public interface AttendanceDao {
    public boolean addLessonAttendance(int lessonId, int[] students);

    public List<Integer> getLessonAttendance(int lessonId, int groupId);
}
