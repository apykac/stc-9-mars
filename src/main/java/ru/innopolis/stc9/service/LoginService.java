package ru.innopolis.stc9.service;

/**
 * Created by aldar on 24.05.2018.
 * Интерфейс сервисного слоя для аутентификации
 */
public interface LoginService {
    boolean checkAuth(String login, String password);

    Integer getRole(String login);
}
