package ru.innopolis.stc9.service;

import ru.innopolis.stc9.pojo.Lessons;

import java.util.List;

public interface LessonsService {
    boolean addLesson(Lessons lesson);

    void deleteLesson(int lessonId);

    List<Lessons> findAllLessons();

}
