package ru.innopolis.stc9.dao;

import ru.innopolis.stc9.pojo.HomeWork;

import java.util.List;

/**
 * Created by Сергей on 31.05.2018.
 */
public interface HomeWorkDao {
    boolean addHomeWork(HomeWork homeWork);
    boolean updateHomeWork(HomeWork homeWork);
    HomeWork findById(int id);
    HomeWork findByStudentId(int studentId);
    HomeWork findByLessonId(int lessonId);
    List<HomeWork> findAllHomeWork();
    boolean deleteHomeWork(int id);
}
