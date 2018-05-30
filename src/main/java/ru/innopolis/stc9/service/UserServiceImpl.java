package ru.innopolis.stc9.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.innopolis.stc9.dao.UserDao;
import ru.innopolis.stc9.pojo.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;
    @Autowired
    private GroupService groupService;

    @Override
    public List<String> isCorrectData(Map<String, String[]> incParam) {
        List<String> result = new ArrayList<>();
        if (incParam == null || incParam.isEmpty()) return result;
        if ((incParam.get("login") != null) && incParam.get("login")[0].equals("")) {
            result.add("Invalid/Exist Login");
            return result;
        }
        if (incParam.get("hash_password") != null && incParam.get("hash_password")[0].equals("")) {
            result.add("Invalid password");
            return result;
        }
        return result;
    }

    @Override
    public boolean addUser(User user) {
        if (user == null) return false;
        return userDao.addUser(user);
    }

    @Override
    public Integer getRole(String login) {
        if ((login == null) || login.isEmpty()) return -1;
        User user = userDao.findLoginByName(login);
        if (user == null) return -1;
        return user.getPermissionGroup();
    }

    @Override
    public boolean checkAuth(String login, String password) {
        if ((login == null) || (password == null) || login.isEmpty() || password.isEmpty()) return false;
        User user = userDao.findLoginByName(login);
        return (user != null) && (CryptService.isMatched(password, user.getHashPassword()));
    }

    @Override
    public boolean addUserByParam(Map<String, String[]> incParam) {
        if ((incParam == null) || incParam.isEmpty()) return false;
        User user = new User(
                incParam.get("login")[0],
                CryptService.crypting(incParam.get("hash_password")[0]),
                incParam.get("first_name")[0],
                incParam.get("second_name")[0],
                incParam.get("middle_name")[0]);
        return userDao.addUser(user);
    }

    @Override
    public boolean isExist(String login) {
        if ((login == null) || login.equals("")) return false;
        return userDao.findLoginByName(login) != null;
    }

    @Override
    public List<User> getUserList() {
        return userDao.getUsersList();
    }

    @Override
    public boolean updateUser(User user) {
        return userDao.updateUserByFIOL(user);
    }

    @Override
    public User findUserById(int userId) {
        return userDao.findUserByUserId(userId);
    }

    @Override
    public User findUserByLogin(String login) {
        return userDao.findLoginByName(login);
    }

    /**
     * возвращает список юзеров-студентов в конкретной группе
     *
     * @param groupId - id группы
     */
    @Override
    public List<User> getStudentsByGroupId(int groupId) {
        List<User> students = new ArrayList<>();
        for (User u : userDao.getUsersList()) {
            if (u.getPermissionGroup() == 2 && u.getGroupId() == groupId) {
                students.add(u);
            }
        }
        return students;
    }

    /**
     * возвращает список юзеров-студентов, не состоящих в данной группе
     *
     * @param groupId - id группы
     */
    @Override
    public List<User> getStudentsWithoutGroup(int groupId) {
        List<User> students = new ArrayList<>();
        for (User u : userDao.getUsersList()) {
            if (u.getPermissionGroup() == 2 && u.getGroupId() != groupId) {
                u.setGroup(groupService.findGroupById(u.getGroupId()));
                students.add(u);
            }
        }
        return students;
    }
}
