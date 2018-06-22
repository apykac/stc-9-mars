package ru.innopolis.stc9.dao.interfaces;

import ru.innopolis.stc9.pojo.HomeWork;

import java.util.List;

/**
 * Created by Сергей on 31.05.2018.
 */
public interface HomeWorkDao {
    boolean addHomeWork(HomeWork homeWork);

    boolean updateHomeWork(HomeWork homeWork);

    HomeWork findById(long id);

    HomeWork findByStudentId(long studentId);

    HomeWork findByLessonId(long lessonId);

    List<HomeWork> getHomeWorkListByLessonId(long lessonId);

    HomeWork findHomeWorkByStudentIdAndLessonId(long studentId, long lessonId);

    List<HomeWork> findAllHomeWork();

    boolean deleteHomeWork(long id);
}
