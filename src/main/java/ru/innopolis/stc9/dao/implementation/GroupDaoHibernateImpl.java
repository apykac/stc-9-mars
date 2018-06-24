package ru.innopolis.stc9.dao.implementation;

import org.hibernate.Hibernate;
import org.hibernate.ObjectNotFoundException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.exception.ConstraintViolationException;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.innopolis.stc9.dao.interfaces.GroupDao;
import ru.innopolis.stc9.pojo.Group;
import ru.innopolis.stc9.pojo.User;

import java.util.List;

/**
 * Created by Patrushev Sergey on 19.06.2018.
 */
@Repository
public class GroupDaoHibernateImpl implements GroupDao {

    private final SessionFactory factory;

    @Autowired
    public GroupDaoHibernateImpl(SessionFactory factory) {
        this.factory = factory;
    }


    @Override
    public boolean addGroup(Group group) {
        if (group == null) return false;
        try (Session session = factory.openSession()) {
            session.beginTransaction();
            session.save(group);
            session.getTransaction().commit();
            return true;
        }
    }

    @Override
    public boolean updateGroup(Group group) {
        if (group == null) return false;
        try (Session session = factory.openSession()) {
            session.beginTransaction();
            session.update(group);
            session.getTransaction().commit();
            return true;
        }
    }

    @Override
    public boolean deleteGroup(int groupId) {
        if (groupId < 0) return false;
        Group group;
        try (Session session = factory.openSession()) {
            session.beginTransaction();
            group = findGroupById(groupId);
            for (User u: group.getUsers()) {
                u.setGroup(null);
                session.update(u);
            }
            session.delete(group);
            session.getTransaction().commit();
            return true;
        }
    }

    @Override
    public Group findGroupById(Integer id) {
        Group group;
        try (Session session = factory.openSession()) {
            session.beginTransaction();
            group = session.get(Group.class, id);
            Hibernate.initialize(group.getUsers());
            session.getTransaction().commit();
        }
        return group;
    }

    @Override
    public Group findGroupByName(String name) {
        Query query;
        Group group;
        try (Session session = factory.openSession()) {
            session.beginTransaction();
            query = session.createQuery("from Group where group.name = name");
            query.setParameter("name", name);
            group = (Group) query.iterate().next();
            Hibernate.initialize(group.getUsers());
            session.getTransaction().commit();
        }
        return group;
    }

    @Override
    public List<Group> findAllGroups() {
        List<Group> list;
        try (Session session = factory.openSession()) {
            session.beginTransaction();
            Query query = session.createQuery("from Group");
            list = query.list();
            session.getTransaction().commit();
        }
        return list;
    }
}
