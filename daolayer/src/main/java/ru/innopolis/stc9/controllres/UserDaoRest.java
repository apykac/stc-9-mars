package ru.innopolis.stc9.controllres;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ru.innopolis.stc9.dao.interfaces.UserDao;
import ru.innopolis.stc9.pojo.Subject;
import ru.innopolis.stc9.pojo.User;

import java.lang.reflect.Type;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserDaoRest {
    private Gson gson;
    private UserDao userDao;

    @Autowired
    public UserDaoRest(UserDao userDao) {
        this.userDao = userDao;
        GsonBuilder builder = new GsonBuilder();
        this.gson = builder.create();
    }

    private void nullSetter(User user, boolean withGroup) {
        if (withGroup && (user.getGroup() != null)) {
            user.getGroup().setUsers(null);
            user.getGroup().setSubjects(null);
        }
        user.setAttendances(null);
        user.setHomeWorks(null);
        user.setIncomingMessages(null);
        user.setMarks(null);
        user.setUpcomingMessages(null);
    }

    @RequestMapping(
            value = "/findLoginByName",
            method = RequestMethod.POST,
            produces = "application/json; charset=utf-8",
            consumes = "application/json; charset=utf-8")
    public String findLoginByName(@RequestBody String login) {
        User user = userDao.findLoginByName(gson.fromJson(login, String.class));
        if (user != null) nullSetter(user, true);
        return gson.toJson(user);
    }

    @RequestMapping(
            value = "/addUser",
            method = RequestMethod.POST,
            produces = "application/json; charset=utf-8",
            consumes = "application/json; charset=utf-8")
    public String addUser(@RequestBody String user) {
        return gson.toJson(userDao.addUser(gson.fromJson(user, User.class)));
    }

    @RequestMapping(
            value = "/delUserById",
            method = RequestMethod.POST,
            produces = "application/json; charset=utf-8",
            consumes = "application/json; charset=utf-8")
    public String delUserById(@RequestBody String userId) {
        return gson.toJson(userDao.delUserById(gson.fromJson(userId, Integer.class)));
    }

    @RequestMapping(
            value = "/deactivateUser",
            method = RequestMethod.POST,
            produces = "application/json; charset=utf-8",
            consumes = "application/json; charset=utf-8")
    public String deactivateUser(@RequestBody String userId) {
        return gson.toJson(userDao.deactivateUser(gson.fromJson(userId, Integer.class)));
    }

    @RequestMapping(
            value = "/updateUserByFIOL",
            method = RequestMethod.POST,
            produces = "application/json; charset=utf-8",
            consumes = "application/json; charset=utf-8")
    public String updateUserByFIOL(@RequestBody String user) {
        return gson.toJson(userDao.updateUserByFIOL(gson.fromJson(user, User.class)));
    }

    @RequestMapping(
            value = "/updateUserPassword",
            method = RequestMethod.POST,
            produces = "application/json; charset=utf-8",
            consumes = "application/json; charset=utf-8")
    public String updateUserPassword(@RequestBody String user) {
        return gson.toJson(userDao.updateUserPassword(gson.fromJson(user, User.class)));
    }

    @RequestMapping(
            value = "/findUserByUserId",
            method = RequestMethod.POST,
            produces = "application/json; charset=utf-8",
            consumes = "application/json; charset=utf-8")
    public String findUserByUserId(@RequestBody String userId) {
        User user = userDao.findUserByUserId(gson.fromJson(userId, Integer.class));
        if (user != null) nullSetter(user, true);
        return gson.toJson(user);
    }

    @RequestMapping(
            value = "/findUserByIdWithSubjectList",
            method = RequestMethod.POST,
            produces = "application/json; charset=utf-8",
            consumes = "application/json; charset=utf-8")
    public String findUserByIdWithSubjectList(@RequestBody String userId) {
        User user = userDao.findUserByIdWithSubjectList(gson.fromJson(userId, Integer.class));
        if (user != null) {
            nullSetter(user, false);
            if (user.getGroup() != null) {
                user.getGroup().setUsers(null);
                for (Subject subject : user.getGroup().getSubjects()) {
                    subject.setGroups(null);
                    subject.setLessons(null);
                }
            }
        }
        return gson.toJson(user);
    }

    @RequestMapping(
            value = "/getUsersList",
            method = RequestMethod.POST,
            produces = "application/json; charset=utf-8")
    public String getUsersList() {
        List<User> users = userDao.getUsersList();
        for (User user : users) nullSetter(user, true);
        return gson.toJson(users);
    }

    @RequestMapping(
            value = "/updateGroupId",
            method = RequestMethod.POST,
            produces = "application/json; charset=utf-8",
            consumes = "application/json; charset=utf-8")
    public String updateGroupId(@RequestBody String incParam) {
        Type type = new TypeToken<List<Integer>>() {
        }.getType();
        List<Integer> param = gson.fromJson(incParam, type);
        return gson.toJson(userDao.updateGroupId(param.get(0), param.get(1)));
    }

    @RequestMapping(
            value = "/getAllStudents",
            method = RequestMethod.POST,
            produces = "application/json; charset=utf-8")
    public String getAllStudents() {
        List<User> users = userDao.getAllStudents();
        for (User user : users) nullSetter(user, true);
        return gson.toJson(users);
    }

    @RequestMapping(
            value = "/getStudentsByGroupId",
            method = RequestMethod.POST,
            produces = "application/json; charset=utf-8",
            consumes = "application/json; charset=utf-8")
    public String getStudentsByGroupId(@RequestBody String groupId) {
        List<User> users = userDao.getStudentsByGroupId(gson.fromJson(groupId, Integer.class));
        for (User user : users) nullSetter(user, true);
        return gson.toJson(users);
    }
}
