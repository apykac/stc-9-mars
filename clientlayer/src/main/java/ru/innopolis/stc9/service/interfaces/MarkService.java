package ru.innopolis.stc9.service.interfaces;

import ru.innopolis.stc9.pojo.Mark;

import java.util.List;

public interface MarkService {
    List<Mark> getMarksByLessonId(int lessonId);

    Mark getMarkById(int id);

    boolean updateMark(Mark mark);

    Mark getMarkByIdWithFullInfo(int id);
}
