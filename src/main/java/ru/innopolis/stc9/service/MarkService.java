package ru.innopolis.stc9.service;

import ru.innopolis.stc9.pojo.Mark;

import java.util.Map;

public interface MarkService {
    Map<String, Mark> getMarksByLessonId(int lessonId);

    Mark getMarkById(int id);

    boolean updateMark(Mark mark);

    String getFullStudentNameInOneString(int markId);

    String getLessonNameByMarkId(int lessonId);

    String getLessonNameByLessonId(int markId);
}
