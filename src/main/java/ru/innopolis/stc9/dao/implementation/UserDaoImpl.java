package ru.innopolis.stc9.dao.implementation;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.innopolis.stc9.dao.interfaces.UserDao;
import ru.innopolis.stc9.dao.mappers.Mapper;
import ru.innopolis.stc9.dao.mappers.UserMapper;
import ru.innopolis.stc9.pojo.DBObject;
import ru.innopolis.stc9.pojo.User;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Repository("userDaoImpl")
public class UserDaoImpl extends DBObjectDao implements UserDao {
    @Autowired
    SessionFactory factory;

    private String selectPrefix = "SELECT * FROM users ";
    private String updatePrefix = "UPDATE users SET ";
    private String wherePostfix = " WHERE ";

    @Override
    public Mapper getMapper() {
        return new UserMapper();
    }

    @Override
    public boolean addUser(User user) {
        /*if (user == null) return false;
        List<String> mainParam = new ArrayList<>(Arrays.asList(UserMapper.LOGIN, UserMapper.HASH,
                UserMapper.FNAME, UserMapper.SNAME, UserMapper.MNAME));
        return executor(user, "INSERT INTO users (", mainParam, ") VALUES (?, ?, ?, ?, ?)", null, false);*/
        Session session = factory.openSession();
        session.beginTransaction();
        session.save(user);
        session.getTransaction().commit();
        session.close();
        return true;
    }

    @Override
    public User findUserByUserId(int id) {
        /*if (id < 0) return null;
        User user = new User();
        user.setId(id);
        List<String> secondaryParam = new ArrayList<>(Collections.singletonList(UserMapper.ID));
        List<DBObject> result = getter(user, selectPrefix, null, wherePostfix, secondaryParam, true);
        return result.isEmpty() ? null : (User) result.get(0);*/
        Session session = factory.openSession();
        User user = session.get(User.class, id);
        session.close();
        return user;
    }

    @Override
    public User findLoginByName(String login) {
        if (login.equals("anonymousUser") || login.isEmpty()) return null;
        List<User> resultList;
        /*User user = new User();
        user.setLogin(login);
        List<String> secondaryParam = new ArrayList<>(Collections.singletonList(UserMapper.LOGIN));
        List<DBObject> result = getter(user, selectPrefix, null, wherePostfix, secondaryParam, true);
        return result.isEmpty() ? null : (User) result.get(0);*/
        Session session = factory.openSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<User> criteria = builder.createQuery(User.class);
        Root<User> userRoot = criteria.from(User.class);
        criteria.select(userRoot);
        criteria.where(builder.equal(userRoot.get("login"), login));
        resultList = session.createQuery(criteria).getResultList();
        session.close();
        if (resultList != null) return resultList.get(0);
        else return null;
    }

    @Override
    public boolean delUserById(int id) {
        if (id < 0) return false;
        User user = new User();
        user.setId(id);
        List<String> secondaryParam = new ArrayList<>(Collections.singletonList(UserMapper.ID));
        return executor(user, "DELETE FROM users ", null, wherePostfix, secondaryParam, true);
    }


    @Override
    public List<User> getUsersList() {
        List<DBObject> getList = getter(new User(), selectPrefix, null, null, null, true);
        List<User> result = new ArrayList<>();
        for (DBObject object : getList) result.add((User) object);
        return result;
    }

    @Override
    public boolean deactivateUser(int id) {
        if (id < 0) return false;
        User user = new User();
        user.setId(id);
        user.setEnabled(0);
        List<String> mainParam = new ArrayList<>(Collections.singletonList(UserMapper.ENABLED));
        List<String> secondaryParam = new ArrayList<>(Collections.singletonList(UserMapper.ID));
        return executor(user, updatePrefix, mainParam, wherePostfix, secondaryParam, true);
    }

    @Override
    public boolean updateUserByFIOL(User newUser) {
        if (newUser == null) return false;
        List<String> mainParam = new ArrayList<>(Arrays.asList(UserMapper.FNAME, UserMapper.SNAME, UserMapper.MNAME,
                UserMapper.LOGIN, UserMapper.ENABLED, UserMapper.PERMGORUP));
        List<String> secondaryParam = new ArrayList<>(Collections.singletonList(UserMapper.ID));
        return executor(newUser, updatePrefix, mainParam, wherePostfix, secondaryParam, true);
    }

    @Override
    public boolean updateUserPassword(User newUser) {
        if (newUser == null) return false;
        List<String> mainParam = new ArrayList<>(Collections.singletonList(UserMapper.HASH));
        List<String> secondaryParam = new ArrayList<>(Collections.singletonList(UserMapper.ID));
        return executor(newUser, updatePrefix, mainParam, wherePostfix, secondaryParam, true);
    }

    @Override
    public boolean updateGroupId(int userId, Integer groupId) {
        if ((userId < 0)) return false;
        User user = new User();
        user.setGroupId(groupId);
        user.setId(userId);
        List<String> mainParam = new ArrayList<>(Collections.singletonList(UserMapper.GROUPID));
        List<String> secondaryParam = new ArrayList<>(Collections.singletonList(UserMapper.ID));
        return executor(user, "UPDATE users SET ", mainParam, wherePostfix, secondaryParam, true);
    }

    @Override
    public List<User> getAllStudents() {
        User user = new User();
        user.setPermissionGroup("ROLE_STUDENT");
        List<String> secondaryParam = new ArrayList<>(Collections.singletonList(UserMapper.PERMGORUP));
        List<DBObject> getList = getter(user, selectPrefix, null, wherePostfix, secondaryParam, true);
        List<User> result = new ArrayList<>();
        for (DBObject object : getList) result.add((User) object);
        return result;
    }
}
