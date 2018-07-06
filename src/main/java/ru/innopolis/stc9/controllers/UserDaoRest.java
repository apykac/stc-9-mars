package ru.innopolis.stc9.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import ru.innopolis.stc9.dao.interfaces.UserDao;
import ru.innopolis.stc9.pojo.User;

@RestController
@Transactional
public class UserDaoRest {
    @Autowired
    private UserDao userDao;

    @ResponseBody
    @RequestMapping(
            name = "/findLoginByName/{login}",
            method = RequestMethod.GET,
            headers = {"Content-type=application/json"})
    public User findLoginByName(@PathVariable String login) {
        return userDao.findLoginByName(login);
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
