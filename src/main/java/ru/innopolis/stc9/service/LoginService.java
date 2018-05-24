package ru.innopolis.stc9.service;

import java.util.List;
import java.util.Map;

/**
 * Created by aldar on 24.05.2018.
 * Интерфейс сервисного слоя для аутентификации
 */
public interface LoginService {
    List<String> isCorrectData(Map<String, String[]> incParam);

    boolean checkAuth(String login, String password);

    boolean isExist(String login);

    Integer getRole(String login);

    boolean addLogin(Map<String, String[]> incParam, Integer user_id);
}
