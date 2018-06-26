package ru.innopolis.stc9.dao.interfaces;

import ru.innopolis.stc9.pojo.Lessons;
import ru.innopolis.stc9.pojo.Mark;

import java.util.List;

public interface MarkDao {
    List<Mark> getMarksByLessonId(int id);

    List<Mark> getMarksByLesson(Lessons lesson);

    Mark getMarkById(int id);

    boolean addMark(Mark mark);

    boolean updateMark(Mark mark);
}
