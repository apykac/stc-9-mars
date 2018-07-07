package ru.innopolis.stc9.dao.interfaces;

import ru.innopolis.stc9.pojo.HomeWork;

/**
 * Created by Сергей on 31.05.2018.
 */
public interface HomeWorkDao {
    boolean addHomeWork(HomeWork homeWork);

    HomeWork findById(int id);

    HomeWork findHomeWorkByMarkId(int markId);
}
