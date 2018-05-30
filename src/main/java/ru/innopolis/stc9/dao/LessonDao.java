package ru.innopolis.stc9.dao;

import ru.innopolis.stc9.pojo.Lesson;

import java.util.List;

public interface LessonDao {
    public Lesson getLessonById(int lessonId);

    public List<Lesson> getLessonsListBySubjectId(int subjectId);
}
