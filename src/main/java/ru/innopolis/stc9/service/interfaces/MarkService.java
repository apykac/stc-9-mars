package ru.innopolis.stc9.service.interfaces;

import ru.innopolis.stc9.pojo.Mark;
import ru.innopolis.stc9.pojo.User;

import java.util.Map;

public interface MarkService {
    Map<String, Mark> getMarksByLessonId(long lessonId);

    Mark getMarkById(long id);

    boolean updateMark(Mark mark);

    String getFullStudentNameInOneString(long markId);

    String getLessonNameByMarkId(long lessonId);

    String getLessonNameByLessonId(long markId);

    String getFullStudentName(User student);
}
