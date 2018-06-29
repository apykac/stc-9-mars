package ru.innopolis.stc9.service.interfaces;

import ru.innopolis.stc9.pojo.Lessons;

import java.sql.Date;
import java.util.List;

public interface LessonsService {
    boolean addLesson(Lessons lesson);

    boolean deleteLesson(int lessonId);

    List<Lessons> findAllLessonsWithSubjects();

    List<Lessons> findAllLessonsByWithSubject(int subjectId);

    Date stringToDate(String strDate);
}
