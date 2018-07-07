package ru.innopolis.stc9.service.interfaces;

import org.springframework.stereotype.Service;
import ru.innopolis.stc9.pojo.HomeWork;
import ru.innopolis.stc9.pojo.User;

/**
 * Created by Сергей on 31.05.2018.
 */
@Service
public interface HomeWorkService {
    boolean addHomeWork(String url, User student, int lessonId);

    HomeWork findById(int id);

    HomeWork findHomeWorkByMarkId(int markId);

    boolean homeWorkIsURL(String homeWork);
}
