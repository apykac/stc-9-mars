package ru.innopolis.stc9.service.implementation;

import org.apache.commons.validator.UrlValidator;
import org.springframework.stereotype.Service;
import ru.innopolis.stc9.pojo.HomeWork;
import ru.innopolis.stc9.pojo.User;
import ru.innopolis.stc9.service.interfaces.HomeWorkService;

/**
 * Created by Сергей on 31.05.2018.
 */
@Service
public class HomeWorkServiceImpl implements HomeWorkService {
    private static UrlValidator urlValidator = new UrlValidator();

    @Override
    public boolean addHomeWork(String url, User student, int lessonId) {
        return false;
    }

    @Override
    public HomeWork findById(int id) {
        return null;
    }

    @Override
    public HomeWork findHomeWorkByMarkId(int markId) {
        return null;
    }

    @Override
    public boolean homeWorkIsURL(String homeWork) {
        return false;
    }

    /*@Override
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
    }*/

   /* @Override
    public HomeWork findById(int id) {
        return homeWorkDao.findById(id);
    }*/


    /*@Override
    public HomeWork findHomeWorkByMarkId(int markId) {
        if (markId < 0) return null;
        return homeWorkDao.findHomeWorkByMarkId(markId);
    }*/

    /*@Override
    public boolean homeWorkIsURL(String homeWork) {
        return urlValidator.isValid(homeWork);
    }*/
}
