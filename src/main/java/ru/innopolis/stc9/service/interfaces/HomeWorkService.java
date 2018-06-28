package ru.innopolis.stc9.service.interfaces;

import org.springframework.stereotype.Service;
import ru.innopolis.stc9.pojo.HomeWork;
import ru.innopolis.stc9.pojo.User;

import java.util.List;

/**
 * Created by Сергей on 31.05.2018.
 */
@Service
public interface HomeWorkService {
    public boolean addHomeWork(String url, User student, int lessonId);

    boolean updateHomeWork(HomeWork homeWork);

    HomeWork findById(int id);

    List<HomeWork> getHomeWorkListByLessonId(int lessonId);

    String findHomeWorkByMarkId(int markId);

    boolean homeWorkIsURL(String homeWork);

    List<HomeWork> findAllHomeWork();

    boolean deleteHomeWork(int id);
}
