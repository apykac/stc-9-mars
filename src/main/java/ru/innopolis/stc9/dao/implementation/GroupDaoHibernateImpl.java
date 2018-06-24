package ru.innopolis.stc9.dao.implementation;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.innopolis.stc9.dao.interfaces.GroupDao;
import ru.innopolis.stc9.pojo.Group;

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
        Session session = factory.getCurrentSession();
        Group group = findGroupById(groupId);
        session.delete(group);
        return true;
    }

    @Override
    public Group findGroupById(Integer id) {
        Group group;
        try (Session session = factory.openSession()) {
            session.beginTransaction();
            group = session.get(Group.class, id);
            session.getTransaction().commit();
        }
        return group;
    }

    @Override
    public Group findGroupByName(String name) {
        Query query;
        try (Session session = factory.openSession()) {
            session.beginTransaction();
            query = session.createQuery("from Group where group.name = name");
            query.setParameter("name", name);
            session.getTransaction().commit();
        }
        return (Group) query.iterate().next();
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
