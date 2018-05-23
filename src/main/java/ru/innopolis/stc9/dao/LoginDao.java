package ru.innopolis.stc9.dao;

import ru.innopolis.stc9.pogo.Login;

/**
 * Created by Семушев on 24.05.2018.
 * Интерфейс для ДАО слоя логина
 */
public interface LoginDao {
    boolean addLogin(Login login);

    boolean deleteLoginByName(String login);

    Login findLoginByName(String login);

    boolean updateLogin(Login login);
}
