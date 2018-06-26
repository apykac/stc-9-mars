package ru.innopolis.stc9.dao.interfaces;

import ru.innopolis.stc9.pojo.Lessons;

import java.util.List;

public interface LessonsDao {
    boolean addLesson(Lessons lesson);

    boolean deleteLesson(int lessonId);

    List<Lessons> findAllLessonsWithSubjects();

    Lessons getLessonById(int id);
}
