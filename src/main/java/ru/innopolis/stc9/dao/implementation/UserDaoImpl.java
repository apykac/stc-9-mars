package ru.innopolis.stc9.dao.implementation;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.innopolis.stc9.dao.interfaces.UserDao;
import ru.innopolis.stc9.dao.mappers.UserMapper;
import ru.innopolis.stc9.pojo.User;

import javax.persistence.criteria.*;
import java.util.List;

@Repository
public class UserDaoImpl implements UserDao {
    @Autowired
    private SessionFactory factory;

    @Override
    public boolean addUser(User user) {
        if (user == null) return false;
        try (Session session = factory.openSession()) {
            session.beginTransaction();
            session.save(user);
            session.getTransaction().commit();
        }
        return true;
    }

    @Override
    public User findUserByUserId(int id) {
        if (id < 0) return null;
        User user;
        try (Session session = factory.openSession()) {
            user = session.get(User.class, id);
        }
        return user;
    }

    @Override
    public User findUserByIdWithSubjectList(int id) {
        if (id < 0) return null;
        User user;
        try (Session session = factory.openSession()) {
            user = session.get(User.class, id);
            if (user.getGroup() != null)
                Hibernate.initialize(user.getGroup().getSubjects());
        }
        return user;
    }

    @Override
    public User findLoginByName(String login) {
        if (login.equals("anonymousUser") || login.isEmpty()) return null;
        List<User> resultList;
        try (Session session = factory.openSession()) {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<User> criteria = builder.createQuery(User.class);
            Root<User> root = criteria.from(User.class);
            criteria.select(root);
            criteria.where(builder.equal(root.get(UserMapper.LOGIN), login));
            resultList = session.createQuery(criteria).getResultList();
        }
        if (!resultList.isEmpty()) return resultList.get(0);
        else return null;
    }

    @Override
    public boolean delUserById(int id) {
        if (id < 0) return false;
        int result;
        try (Session session = factory.openSession()) {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaDelete<User> criteria = builder.createCriteriaDelete(User.class);
            Root<User> root = criteria.from(User.class);
            criteria.where(builder.equal(root.get(UserMapper.ID), id));
            Transaction transaction = session.beginTransaction();
            result = session.createQuery(criteria).executeUpdate();
            transaction.commit();
        }
        return result != 0;
    }


    @Override
    public List<User> getUsersList() {
        List<User> resultList;
        try (Session session = factory.openSession()) {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<User> criteria = builder.createQuery(User.class);
            Root<User> root = criteria.from(User.class);
            criteria.select(root);
            resultList = session.createQuery(criteria).getResultList();
        }
        return resultList;
    }

    @Override
    public boolean deactivateUser(int id) {
        if (id < 0) return false;
        int result;
        try (Session session = factory.openSession()) {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaUpdate<User> criteria = builder.createCriteriaUpdate(User.class);
            Root<User> root = criteria.from(User.class);
            criteria.set(root.get(UserMapper.ENABLED), 0).
                    where(builder.equal(root.get(UserMapper.ID), id));
            Transaction transaction = session.beginTransaction();
            result = session.createQuery(criteria).executeUpdate();
            transaction.commit();
        }
        return result != 0;
    }

    @Override
    public boolean updateUserByFIOL(User newUser) {
        if (newUser == null) return false;
        int result;
        try (Session session = factory.openSession()) {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaUpdate<User> criteria = builder.createCriteriaUpdate(User.class);
            Root<User> root = criteria.from(User.class);
            criteria.set(root.get(UserMapper.LOGIN), newUser.getLogin()).
                    set(root.get(UserMapper.FNAME), newUser.getFirstName()).
                    set(root.get(UserMapper.SNAME), newUser.getSecondName()).
                    set(root.get(UserMapper.MNAME), newUser.getMiddleName()).
                    set(root.get(UserMapper.PERMGORUP), newUser.getPermissionGroup()).
                    set(root.get(UserMapper.ENABLED), newUser.getEnabled()).
                    where(builder.equal(root.get(UserMapper.ID), newUser.getId()));
            Transaction transaction = session.beginTransaction();
            result = session.createQuery(criteria).executeUpdate();
            transaction.commit();
        }
        return result != 0;
    }

    @Override
    public boolean updateUserPassword(User newUser) {
        if (newUser == null) return false;
        int result;
        try (Session session = factory.openSession()) {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaUpdate<User> criteria = builder.createCriteriaUpdate(User.class);
            Root<User> root = criteria.from(User.class);
            criteria.set(root.get(UserMapper.HASH), newUser.getHashPassword()).
                    where(builder.equal(root.get(UserMapper.ID), newUser.getId()));
            Transaction transaction = session.beginTransaction();
            result = session.createQuery(criteria).executeUpdate();
            transaction.commit();
        }
        return result != 0;
    }

    @Override
    public boolean updateGroupId(int userId, Integer groupId) {
        if ((userId < 0)) return false;
        int result;
        try (Session session = factory.openSession()) {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaUpdate<User> criteria = builder.createCriteriaUpdate(User.class);
            Root<User> root = criteria.from(User.class);
            criteria.set(root.get(UserMapper.GROUPID), groupId).
                    where(builder.equal(root.get(UserMapper.ID), userId));
            Transaction transaction = session.beginTransaction();
            result = session.createQuery(criteria).executeUpdate();
            transaction.commit();
        }
        return result != 0;
    }

    @Override
    public List<User> getAllStudents() {
        List<User> resultList;
        try (Session session = factory.openSession()) {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<User> criteria = builder.createQuery(User.class);
            Root<User> root = criteria.from(User.class);
            criteria.select(root).
                    where(builder.equal(root.get(UserMapper.PERMGORUP), "ROLE_STUDENT"));
            resultList = session.createQuery(criteria).getResultList();
        }
        return resultList;
    }
}
