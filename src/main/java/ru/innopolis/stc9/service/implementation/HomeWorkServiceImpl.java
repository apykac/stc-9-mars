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

import java.util.List;

/**
 * Created by Сергей on 31.05.2018.
 */
@Service
@Transactional
public class HomeWorkServiceImpl implements HomeWorkService {
    @Autowired
    private HomeWorkDao homeWorkDao;
    @Autowired
    private MarkDao markDao;
    @Autowired
    private LessonsDao lessonDao;

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
    public boolean updateHomeWork(HomeWork homeWork) {
        return homeWorkDao.updateHomeWork(homeWork);
    }

    @Override
    public HomeWork findById(int id) {
        return homeWorkDao.findById(id);
    }

    @Override
    public List<HomeWork> getHomeWorkListByLessonId(int lessonId) {
        return homeWorkDao.getHomeWorkListByLessonId(lessonId);
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

    @Override
    public List<HomeWork> findAllHomeWork() {
        return homeWorkDao.findAllHomeWork();
    }

    @Override
    public boolean deleteHomeWork(int id) {
        return homeWorkDao.deleteHomeWork(id);
    }
}
