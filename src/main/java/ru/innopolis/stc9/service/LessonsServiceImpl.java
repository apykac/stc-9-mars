package ru.innopolis.stc9.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.innopolis.stc9.dao.LessonsDao;
import ru.innopolis.stc9.pojo.Lessons;

import java.util.List;

@Service
public class LessonsServiceImpl implements LessonsService {
    @Autowired
    private LessonsDao lessonsDao;

    @Override
    public boolean addLesson(Lessons lesson) {
        if (lesson == null) return false;
        return lessonsDao.addLesson(lesson);
    }

    @Override
    public boolean deleteLesson(int lessonId) {
        if (lessonId < 0) return false;
        return lessonsDao.deleteLesson(lessonId);
    }

    @Override
    public List<Lessons> findAllLessons() {
        return lessonsDao.findAllLessons();
    }
}
