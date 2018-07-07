package ru.innopolis.stc9.service.implementation;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import ru.innopolis.stc9.pojo.User;
import ru.innopolis.stc9.pojo.mappers.UserMapper;
import ru.innopolis.stc9.service.CryptService;
import ru.innopolis.stc9.service.interfaces.UserService;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

@Service("userServiceImpl")
public class UserServiceImpl implements UserService {
    private Gson gson;

    public UserServiceImpl() {
        GsonBuilder builder = new GsonBuilder();
        this.gson = builder.create();
    }

    @Override
    public List<String> isCorrectData(MultiValueMap<String, String> incParam) {
        List<String> result = new ArrayList<>();
        if ((incParam == null) || incParam.isEmpty()) return result;
        nameCheck(result, incParam);
        return result;
    }

    private void nameCheck(List<String> result, MultiValueMap<String, String> incParam) {
        String pattern = "^\\D*$";
        if (nameCheckSingle(incParam, "firstName", pattern)) result.add("Invalid first name");
        if (nameCheckSingle(incParam, "secondName", pattern)) result.add("Invalid second name");
        if (nameCheckSingle(incParam, "middleName", pattern)) result.add("Invalid middle name");
    }

    private boolean nameCheckSingle(MultiValueMap<String, String> incParam, String name, String pattern) {
        return (incParam.get(name) != null) && !incParam.get(name).get(0).matches(pattern);
    }

    @Override
    public boolean addUserByParam(MultiValueMap<String, String> incParam) {
        if ((incParam == null) || incParam.isEmpty()) return false;
        User user = new User(
                incParam.get("login").get(0),
                CryptService.crypting(incParam.get("hashPassword").get(0)),
                incParam.get("firstName").get(0),
                incParam.get("secondName").get(0),
                incParam.get("middleName").get(0));
        Boolean isSuccess = gson.fromJson(
                RestBridge.doConnectAction("http://localhost:8181/user/addUser",
                        user, gson, true), Boolean.class);
        return isSuccess;
    }

    @Override
    public boolean delUserById(int id) {
        if (id < 0) return false;
        Boolean isSuccess = gson.fromJson(
                RestBridge.doConnectAction("http://localhost:8181/user/addUser",
                        new Integer(id), gson, true), Boolean.class);
        return isSuccess;
    }

    @Override
    public boolean isExist(String login) {
        if ((login == null) || login.equals("")) return false;
        User user = gson.fromJson(
                RestBridge.doConnectAction("http://localhost:8181/user/findLoginByName",
                        login, gson, true), User.class);
        return user != null;
    }

    @Override
    public List<User> getUserList() {
        Type type = new TypeToken<List<User>>() {
        }.getType();
        List<User> users = gson.fromJson(
                RestBridge.doConnectAction("http://localhost:8181/user/getUsersList",
                        null, gson, false), type);
        return users;
    }

    @Override
    public List<User> getStudentsByGroupId(Integer groupId) {
        if ((groupId == null) || (groupId < 0))
            return new ArrayList<>();
        Type type = new TypeToken<List<User>>() {
        }.getType();
        List<User> users = gson.fromJson(
                RestBridge.doConnectAction("http://localhost:8181/user/getStudentsByGroupId",
                        groupId, gson, true), type);
        return users;
    }

    @Override
    public List<User> getAllStudents() {
        Type type = new TypeToken<List<User>>() {
        }.getType();
        List<User> users = gson.fromJson(
                RestBridge.doConnectAction("http://localhost:8181/user/getAllStudents",
                        null, gson, false), type);
        return users;
    }

    @Override
    public User findUserById(int userId) {
        if (userId < 0) return null;
        User user = gson.fromJson(
                RestBridge.doConnectAction("http://localhost:8181/user/findUserByUserId",
                        new Integer(userId), gson, true), User.class);
        return user;
    }

    @Override
    public User findUserByIdWithSubjectList(int userId) {
        if (userId < 0) return null;
        User user = gson.fromJson(
                RestBridge.doConnectAction("http://localhost:8181/user/findUserByIdWithSubjectList",
                        new Integer(userId), gson, true), User.class);
        return user;
    }

    @Override
    public User findUserByLogin(String login) {
        if ((login == null) || login.equals("")) return null;
        User user = gson.fromJson(
                RestBridge.doConnectAction("http://localhost:8181/user/findLoginByName",
                        login, gson, true), User.class);
        return user;
    }

    @Override
    public boolean deactivationCurrentAccount(int id) {
        if (id < 0) return false;
        Boolean isSuccess = gson.fromJson(
                RestBridge.doConnectAction("http://localhost:8181/user/deactivateUser",
                        new Integer(id), gson, true), Boolean.class);
        return isSuccess;
    }

    @Override
    public boolean checkPasswordOfCurrentAccount(int id, String candidate) {
        if ((id < 0) || (candidate == null) || candidate.equals("")) return false;
        User user = findUserById(id);
        if (user == null) return false;
        return CryptService.isMatched(candidate, user.getHashPassword());
    }

    @Override
    public Object[] editUser(MultiValueMap<String, String> incParam) {
        Object[] result = new Object[3];
        if ((incParam == null) || incParam.isEmpty()) return result;
        List<String> success = new ArrayList<>();
        List<String> errors = isCorrectData(incParam);
        result[0] = errors;
        result[1] = success;
        result[2] = false;
        if (!errors.isEmpty()) return result;
        User user = UserMapper.getByParam(incParam);
        boolean isSuccess = gson.fromJson(
                RestBridge.doConnectAction("http://localhost:8181/user/updateUserByFIOL",
                        user, gson, true), Boolean.class);
        if (!isSuccess) errors.add("Invalid/Exist Login");
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
            isSuccess = gson.fromJson(
                    RestBridge.doConnectAction("http://localhost:8181/user/updateUserPassword",
                            user, gson, true), Boolean.class);
            if (!isSuccess) errors.add("Updating profile password error");
            else success.add("Updating profile password successfully");
        }
        return result;
    }

    @Override
    public String checkPasswordUpdateIsPossible(MultiValueMap<String, String> incParam, User user) {
        if ((incParam == null) || incParam.isEmpty() || (user == null)) return "Wrong old password";
        String result = "";
        if (CryptService.isMatched(user.getHashPassword(), findUserById(user.getId()).getHashPassword())) {
            if ((incParam.get("newPassword") == null) ||
                    (incParam.get("repeatNewPassword") == null) ||
                    !checkNewPassword(incParam.get("newPassword").get(0), incParam.get("repeatNewPassword").get(0)))
                result = "Passwords not match";
        } else result = "Wrong old password";
        return result;
    }

    private boolean checkNewPassword(String newPassword, String repeatNewPassword) {
        return (newPassword != null) && !newPassword.isEmpty() && newPassword.equals(repeatNewPassword);
    }

    @Override
    public boolean updateGroupId(int userId, Integer groupId) {
        if ((userId < 0) || ((groupId != null) && (groupId < 0))) return false;
        List<Integer> param = new ArrayList<>();
        param.add(userId);
        param.add(groupId);
        Boolean isSuccess = gson.fromJson(
                RestBridge.doConnectAction("http://localhost:8181/user/updateGroupId",
                        param, gson, true), Boolean.class);
        return isSuccess;
    }
}
