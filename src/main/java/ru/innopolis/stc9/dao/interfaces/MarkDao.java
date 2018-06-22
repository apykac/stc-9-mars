package ru.innopolis.stc9.dao.interfaces;

import ru.innopolis.stc9.pojo.Mark;

import java.util.List;

public interface MarkDao {
    List<Mark> getMarksByLessonId(long id);

    Mark getMarkById(long id);

    boolean addMark(Mark mark);

    boolean updateMark(Mark mark);
}
