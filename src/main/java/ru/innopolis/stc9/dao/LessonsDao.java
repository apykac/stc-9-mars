package ru.innopolis.stc9.dao;

import ru.innopolis.stc9.pojo.Lessons;

import java.util.List;

public interface LessonsDao {
    boolean addLesson(Lessons lesson);

    boolean deleteLesson(int lessonId);

    List<Lessons> findAllLessons();
}
