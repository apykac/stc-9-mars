package ru.innopolis.stc9.service.interfaces;

import org.springframework.stereotype.Service;
import ru.innopolis.stc9.pojo.HomeWork;

import java.util.List;

/**
 * Created by Сергей on 31.05.2018.
 */
@Service
public interface HomeWorkService {
    boolean addHomeWork(HomeWork homeWork);

    boolean updateHomeWork(HomeWork homeWork);

    HomeWork findById(int id);

    List<HomeWork> getHomeWorkListByLessonId(int lessonId);

    HomeWork findByStudentId(int studentId);

    HomeWork findByLessonId(int lessonId);

    String findHomeWorkByMarkId(int markId);

    boolean homeWorkIsURL(String homeWork);

    List<HomeWork> findAllHomeWork();

    boolean deleteHomeWork(int id);
}
