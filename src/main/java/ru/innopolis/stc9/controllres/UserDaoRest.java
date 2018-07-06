package ru.innopolis.stc9.controllres;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.innopolis.stc9.dao.interfaces.UserDao;
import ru.innopolis.stc9.pojo.User;

@RestController
public class UserDaoRest {
    @Autowired
    private UserDao userDao;

    @RequestMapping(value = "/findLoginByName/{login}", produces = "application/json; charset=utf-8")
    public String findLoginByName(@PathVariable String login) {
        User user = userDao.findLoginByName(login);
        if (user != null) {
            user.setAttendances(null);
            user.setHomeWorks(null);
            user.setIncomingMessages(null);
            user.setMarks(null);
            user.setUpcomingMessages(null);
        }
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        String s = gson.toJson(user);
        return s;
    }

    @ResponseBody
    @RequestMapping(
            name = "/addUser",
            method = RequestMethod.POST,
            headers = {"Content-type=application/json"})
    public Boolean addUser(@RequestBody User user) {
        return userDao.addUser(user);
    }
}
