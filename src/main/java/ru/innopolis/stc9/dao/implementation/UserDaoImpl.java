package ru.innopolis.stc9.dao.implementation;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.innopolis.stc9.dao.interfaces.UserDao;
import ru.innopolis.stc9.dao.mappers.UserMapper;
import ru.innopolis.stc9.pojo.Group;
import ru.innopolis.stc9.pojo.User;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.criteria.Root;
import java.util.List;

@Repository
public class UserDaoImpl implements UserDao {
    private SessionFactory factory;

    @Autowired
    public UserDaoImpl(SessionFactory factory) {
        this.factory = factory;
    }

    @Override
    public boolean addUser(User user) {
        if (user == null) return false;
        Session session = factory.getCurrentSession();
        session.save(user);
        return true;
    }

    @Override
    public User findUserByUserId(int id) {
        if (id < 0) return null;
        Session session = factory.getCurrentSession();
        return session.get(User.class, id);
    }

    @Override
    public User findUserByIdWithSubjectList(int id) {
        if (id < 0) return null;
        User user;
        Session session = factory.getCurrentSession();
        user = session.get(User.class, id);
        if (user.getGroup() != null)
            Hibernate.initialize(user.getGroup().getSubjects());
        return user;
    }

    @Override
    public User findLoginByName(String login) {
        if (login.equals("anonymousUser") || login.isEmpty()) return null;
        Session session = factory.getCurrentSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<User> criteria = builder.createQuery(User.class);
        Root<User> root = criteria.from(User.class);
        criteria.select(root).
                where(builder.equal(root.get(UserMapper.LOGIN), login));
        return session.createQuery(criteria).uniqueResult();
    }

    @Override
    public boolean delUserById(int id) {
        if (id < 0) return false;
        Session session = factory.getCurrentSession();
        User user = findUserByUserId(id);
        session.delete(user);
        return true;
    }


    @Override
    public List<User> getUsersList() {
        Session session = factory.getCurrentSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<User> criteria = builder.createQuery(User.class);
        Root<User> root = criteria.from(User.class);
        criteria.select(root);
        return session.createQuery(criteria).getResultList();
    }

    @Override
    public boolean deactivateUser(int id) {
        if (id < 0) return false;
        Session session = factory.getCurrentSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaUpdate<User> criteria = builder.createCriteriaUpdate(User.class);
        Root<User> root = criteria.from(User.class);
        criteria.set(root.get(UserMapper.ENABLED), 0).
                where(builder.equal(root.get(UserMapper.ID), id));
        return session.createQuery(criteria).executeUpdate() != 0;
    }

    @Override
    public boolean updateUserByFIOL(User newUser) {
        if (newUser == null) return false;
        Session session = factory.getCurrentSession();
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
        return session.createQuery(criteria).executeUpdate() != 0;
    }

    @Override
    public boolean updateUserPassword(User newUser) {
        if (newUser == null) return false;
        Session session = factory.getCurrentSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaUpdate<User> criteria = builder.createCriteriaUpdate(User.class);
        Root<User> root = criteria.from(User.class);
        criteria.set(root.get(UserMapper.HASH), newUser.getHashPassword()).
                where(builder.equal(root.get(UserMapper.ID), newUser.getId()));
        return session.createQuery(criteria).executeUpdate() != 0;
    }

    @Override
    public boolean updateGroupId(int userId, Integer groupId) {
        if ((userId < 0)) return false;
        Session session = factory.getCurrentSession();
        User user = session.get(User.class, userId);
        if (groupId == null)
            user.setGroup(null);
        else
            user.setGroup(session.get(Group.class, groupId));
        session.update(user);
        return true;
    }

    @Override
    public List<User> getAllStudents() {
        Session session = factory.getCurrentSession();
        Query query = session.createQuery(
                "FROM User u where u.permissionGroup = :role");
        query.setParameter("role", "ROLE_STUDENT");
        return query.getResultList();
    }
}
