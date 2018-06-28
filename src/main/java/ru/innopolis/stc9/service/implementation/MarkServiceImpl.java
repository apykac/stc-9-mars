package ru.innopolis.stc9.service.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.innopolis.stc9.dao.interfaces.MarkDao;
import ru.innopolis.stc9.pojo.Mark;
import ru.innopolis.stc9.service.interfaces.MarkService;

import java.util.List;

@Service
@Transactional
public class MarkServiceImpl implements MarkService {
    private MarkDao markDao;

    @Autowired
    public MarkServiceImpl(MarkDao markDao) {
        this.markDao = markDao;
    }

    public MarkServiceImpl() {
    }

    /**
     * Возвращает Map с именами студентов и оценкой ДЗ, связанного с уроком,
     * id которого передается параметром метода.
     *
     * @param lessonId
     * @return
     */
    @Override
    public List<Mark> getMarksByLessonId(int lessonId) {
        return markDao.getMarksByLessonId(lessonId);
    }

    @Override
    public Mark getMarkById(int id) {
        return markDao.getMarkById(id);
    }

    @Override
    public boolean updateMark(Mark mark) {
        return markDao.updateMark(mark);
    }

    @Override
    public Mark getMarkByIdWithFullInfo(int id) {
        if (id < 0) return null;
        return markDao.getMarkByIdWithFullInfo(id);
    }
}
