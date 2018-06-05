package ru.innopolis.stc9.dao;

import ru.innopolis.stc9.pojo.Mark;

import java.util.List;

public interface MarkDao {
    List<Mark> getMarksByLessonId(int id);

    Mark getMarkById(int id);

    boolean addMark(Mark mark);

    boolean updateMark(Mark mark);
}
