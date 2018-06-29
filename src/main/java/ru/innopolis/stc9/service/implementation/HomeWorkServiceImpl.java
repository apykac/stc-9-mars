package ru.innopolis.stc9.service.implementation;

import org.apache.commons.validator.UrlValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.innopolis.stc9.dao.interfaces.HomeWorkDao;
import ru.innopolis.stc9.dao.interfaces.LessonsDao;
import ru.innopolis.stc9.dao.interfaces.MarkDao;
import ru.innopolis.stc9.pojo.HomeWork;
import ru.innopolis.stc9.pojo.Lessons;
import ru.innopolis.stc9.pojo.Mark;
import ru.innopolis.stc9.pojo.User;
import ru.innopolis.stc9.service.interfaces.HomeWorkService;

/**
 * Created by Сергей on 31.05.2018.
 */
@Service
@Transactional
public class HomeWorkServiceImpl implements HomeWorkService {
    private HomeWorkDao homeWorkDao;
    private MarkDao markDao;
    private LessonsDao lessonDao;

    @Autowired
    public HomeWorkServiceImpl(HomeWorkDao homeWorkDao, MarkDao markDao, LessonsDao lessonDao) {
        this.homeWorkDao = homeWorkDao;
        this.markDao = markDao;
        this.lessonDao = lessonDao;
    }

    private static UrlValidator urlValidator = new UrlValidator();

    @Override
    public boolean addHomeWork(String url, User student, int lessonId) {
        Lessons lesson = lessonDao.getLessonById(lessonId);
        HomeWork homeWork = new HomeWork(url, student, lesson);
        boolean addHomeWorkSuccess = homeWorkDao.addHomeWork(homeWork);
        if (addHomeWorkSuccess) {
            Mark mark = new Mark();
            mark.setLesson(lesson);
            mark.setStudent(student);
            markDao.addMark(mark);
        }
        return addHomeWorkSuccess;
    }

    @Override
    public HomeWork findById(int id) {
        return homeWorkDao.findById(id);
    }


    @Override
    public HomeWork findHomeWorkByMarkId(int markId) {
        if (markId < 0) return null;
        return homeWorkDao.findHomeWorkByMarkId(markId);
    }

    @Override
    public boolean homeWorkIsURL(String homeWork) {
        return urlValidator.isValid(homeWork);
    }
}
