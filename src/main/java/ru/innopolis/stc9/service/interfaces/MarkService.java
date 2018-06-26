package ru.innopolis.stc9.service.interfaces;

import ru.innopolis.stc9.pojo.Mark;
import ru.innopolis.stc9.pojo.User;

import java.util.List;

public interface MarkService {
    List<Mark> getMarksByLessonId(int lessonId);

    Mark getMarkById(int id);

    boolean updateMark(Mark mark);

    Mark getMarkByIdWithFullInfo(int id);

    String getFullStudentNameInOneString(int markId);

    String getLessonNameByMarkId(int lessonId);

    String getLessonNameByLessonId(int markId);

    String getFullStudentName(User student);
}
