package ru.innopolis.stc9.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.innopolis.stc9.dao.HomeWorkDao;
import ru.innopolis.stc9.pojo.HomeWork;

import java.util.List;

/**
 * Created by Сергей on 31.05.2018.
 */
@Service
public class HomeWorkServiceImpl implements HomeWorkService {
    @Autowired
    private HomeWorkDao homeWorkDao;
    @Override
    public boolean addHomeWork(HomeWork homeWork) {
        return homeWorkDao.addHomeWork(homeWork);
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
    public HomeWork findByStudentId(int studentId) {
        return homeWorkDao.findByStudentId(studentId);
    }

    @Override
    public HomeWork findByLessonId(int lessonId) {
        return homeWorkDao.findByLessonId(lessonId);
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
