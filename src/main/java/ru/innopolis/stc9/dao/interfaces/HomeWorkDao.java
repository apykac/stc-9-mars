package ru.innopolis.stc9.dao.interfaces;

import ru.innopolis.stc9.pojo.HomeWork;
import ru.innopolis.stc9.pojo.Lessons;
import ru.innopolis.stc9.pojo.User;

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

    List<HomeWork> getHomeWorkListByLessonId(int lessonId);

    HomeWork findHomeWorkByStudentIdAndLessonId(int studentId, int lessonId);

    HomeWork findHomeWorkByStudentAndLesson(User student, Lessons lesson);

    List<HomeWork> findAllHomeWork();

    boolean deleteHomeWork(int id);
}
