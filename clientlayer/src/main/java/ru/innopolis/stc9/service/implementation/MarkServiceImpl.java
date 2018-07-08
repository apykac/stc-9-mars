package ru.innopolis.stc9.service.implementation;

import org.springframework.stereotype.Service;
import ru.innopolis.stc9.pojo.Mark;
import ru.innopolis.stc9.service.interfaces.MarkService;

import java.util.List;

@Service
public class MarkServiceImpl implements MarkService {
    @Override
    public List<Mark> getMarksByLessonId(int lessonId) {
        return null;
    }

    @Override
    public Mark getMarkById(int id) {
        return null;
    }

    @Override
    public boolean updateMark(Mark mark) {
        return false;
    }

    @Override
    public Mark getMarkByIdWithFullInfo(int id) {
        return null;
    }
    /*@Override
    public List<Mark> getMarksByLessonId(int lessonId) {
        return markDao.getMarksByLessonId(lessonId);
    }*/

    /*@Override
    public Mark getMarkById(int id) {
        return markDao.getMarkById(id);
    }*/

    /*@Override
    public boolean updateMark(Mark mark) {
        return markDao.updateMark(mark);
    }*/

    /*@Override
    public Mark getMarkByIdWithFullInfo(int id) {
        if (id < 0) return null;
        return markDao.getMarkByIdWithFullInfo(id);
    }*/
}
