package ru.innopolis.stc9.service.interfaces;

import ru.innopolis.stc9.pojo.Lessons;

import java.sql.Date;
import java.util.List;

public interface LessonsService {
    boolean addLesson(Lessons lesson);

    boolean deleteLesson(long lessonId);

    List<Lessons> findAllLessons();

    Date stringToDate(String strDate);
}
