package ru.innopolis.stc9.service;

import org.springframework.util.MultiValueMap;
import ru.innopolis.stc9.pojo.User;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface AdminService {
    List<User> getUsersList();

    User getUser(int id);

    List<String> editUser(MultiValueMap<String, String> incParam);

    //String editUser(HttpServletRequest req);

    //String getUserUpdateMessage(HttpServletRequest req);

    //String getPasswordUpdateMessage(HttpServletRequest req);
}
