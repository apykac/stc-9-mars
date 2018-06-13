package ru.innopolis.stc9.service.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import ru.innopolis.stc9.dao.interfaces.GroupDao;
import ru.innopolis.stc9.dao.interfaces.UserDao;
import ru.innopolis.stc9.dao.mappers.Mapper;
import ru.innopolis.stc9.dao.mappers.UserMapper;
import ru.innopolis.stc9.pojo.User;
import ru.innopolis.stc9.service.CryptService;
import ru.innopolis.stc9.service.interfaces.GroupService;
import ru.innopolis.stc9.service.interfaces.UserService;

import java.util.ArrayList;
import java.util.List;

@Service("userServiceImpl")
public class UserServiceImpl implements UserService {
    private Mapper mapper = new UserMapper();

    @Autowired
    private UserDao userDao;
    @Autowired
    private GroupDao groupDao;

    @Autowired
    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public List<String> isCorrectData(MultiValueMap<String, String> incParam) {
        List<String> result = new ArrayList<>();
        if ((incParam == null) || incParam.isEmpty()) return result;
        nameCheck(result, incParam);
        if ((incParam.get(UserMapper.GROUPID) != null)) {
            try {
                int groupId = Integer.parseInt(incParam.get(UserMapper.GROUPID).get(0));
                if (groupDao.findGroupById(groupId) == null) result.add("Group id is not Exist");
            } catch (NumberFormatException e) {
                result.add("Invalid group id");
            }
        }
        if ((incParam.get(UserMapper.ENABLED) != null)) {
            try {
                int enabled = Integer.parseInt(incParam.get(UserMapper.ENABLED).get(0));
                if ((enabled != 1) && (enabled != 0)) result.add("Enabled must be 0 or 1");
            } catch (NumberFormatException e) {
                result.add("Invalid enabled value");
            }
        }
        return result;
    }

    private void nameCheck(List<String> result, MultiValueMap<String, String> incParam) {
        String pattern = "^\\D*$";
        if (nameCheckSingle(incParam, UserMapper.FNAME, pattern)) result.add("Invalid first name");
        if (nameCheckSingle(incParam, UserMapper.SNAME, pattern)) result.add("Invalid second name");
        if (nameCheckSingle(incParam, UserMapper.MNAME, pattern)) result.add("Invalid middle name");
    }

    private boolean nameCheckSingle(MultiValueMap<String, String> incParam, String name, String pattern) {
        return (incParam.get(name) != null) && !incParam.get(name).get(0).matches(pattern);
    }

    @Override
    public boolean addUserByParam(MultiValueMap<String, String> incParam) {
        if ((incParam == null) || incParam.isEmpty()) return false;
        User user = new User(
                incParam.get(UserMapper.LOGIN).get(0),
                CryptService.crypting(incParam.get(UserMapper.HASH).get(0)),
                incParam.get(UserMapper.FNAME).get(0),
                incParam.get(UserMapper.SNAME).get(0),
                incParam.get(UserMapper.MNAME).get(0));
        return userDao.addUser(user);
    }

    @Override
    public boolean delUserById(int id) {
        if (id < 0) return false;
        return userDao.delUserById(id);
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
    public User findUserById(int userId) {
        if (userId < 0) return null;
        return userDao.findUserByUserId(userId);
    }

    @Override
    public User findUserByLogin(String login) {
        if ((login == null) || login.equals("")) return null;
        return userDao.findLoginByName(login);
    }

    /**
     * возвращает список юзеров-студентов в конкретной группе
     *
     * @param groupId - id группы
     */
    @Override
    public List<User> getStudentsByGroupId(Integer groupId) {
        List<User> students = new ArrayList<>();
        for (User u : userDao.getAllStudents())
            if (groupId.equals(u.getGroupId()))
                students.add(u);
        return students;
    }

    @Override
    public List<User> getAllStudents() {
        return userDao.getAllStudents();
    }

    @Override
    public boolean checkPasswordOfCurrentAccount(int id, String candidate) {
        if (id < 0) return false;
        User user = findUserById(id);
        return CryptService.isMatched(candidate, user.getHashPassword());
    }

    @Override
    public boolean deactivationCurrentAccount(int id) {
        if (id < 0) return false;
        return userDao.deactivateUser(id);
    }

    @Override
    public Object[] editUser(MultiValueMap<String, String> incParam) {
        Object[] result = new Object[3];
        List<String> success = new ArrayList<>();
        List<String> errors = isCorrectData(incParam);
        result[0] = errors;
        result[1] = success;
        result[2] = false;
        if (!errors.isEmpty()) return result;
        User user = (User) mapper.getByParam(incParam);
        if (!userDao.updateUserByFIOL(user)) errors.add("Invalid/Exist Login");
        else {
            result[2] = true;
            success.add("Updating profile FIO success successfully");
        }
        if ((user.getHashPassword() != null) && !user.getHashPassword().isEmpty()) {
            String incPass = checkPasswordUpdateIsPossible(incParam, user);
            if (!incPass.isEmpty()) {
                errors.add(incPass);
                return result;
            }
            user.setHashPassword(CryptService.crypting(incParam.get("newPassword").get(0)));
            if (!userDao.updateUserPassword(user)) errors.add("Updating profile password");
            else success.add("Updating profile password successfully");
        }
        return result;
    }

    public String checkPasswordUpdateIsPossible(MultiValueMap<String, String> incParam, User user) {
        String result = "";
        if (CryptService.isMatched(user.getHashPassword(), userDao.findUserByUserId(user.getId()).getHashPassword())) {
            if (!checkNewPassword(incParam.get("newPassword").get(0), incParam.get("repeatNewPassword").get(0)))
                result = "Passwords not match";
        } else result = "Wrong old password";
        return result;
    }

    public boolean checkNewPassword(String newPassword, String repeatNewPassword) {
        return (newPassword != null) && !newPassword.isEmpty() && newPassword.equals(repeatNewPassword);
    }

    @Override
    public boolean updateGroupId(int userId, Integer groupId) {
        return userDao.updateGroupId(userId, groupId);
    }

}
