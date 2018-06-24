package ru.innopolis.stc9.dao.implementation;

import org.hibernate.Hibernate;
import org.hibernate.ObjectNotFoundException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.exception.ConstraintViolationException;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.EnableTransactionManagement;
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
    @Transactional
    public boolean addGroup(Group group) {
        if (group == null) return false;
        Session session = factory.getCurrentSession();
            session.save(group);
            return true;
    }

    @Override
    @Transactional
    public boolean updateGroup(Group group) {
        if (group == null) return false;
        Session session = factory.getCurrentSession();
            session.update(group);
            return true;
    }

    @Override
    @Transactional
    public boolean deleteGroup(int groupId) {
        if (groupId < 0) return false;
        Group group;
        Session session = factory.getCurrentSession();
            group = findGroupById(groupId);
            for (User u: group.getUsers()) {
                u.setGroup(null);
                session.update(u);
            }
            session.delete(group);
            return true;
    }

    @Override
    @Transactional
    public Group findGroupById(Integer id) {
        Group group;
        Session session = factory.getCurrentSession();
            group = session.get(Group.class, id);
            Hibernate.initialize(group.getUsers());
        return group;
    }

    @Override
    @Transactional
    public Group findGroupByName(String name) {
        Query query;
        Group group;
        Session session = factory.getCurrentSession();
            query = session.createQuery("from Group where group.name = name");
            query.setParameter("name", name);
            group = (Group) query.iterate().next();
            Hibernate.initialize(group.getUsers());
        return group;
    }

    @Override
    @Transactional
    public List<Group> findAllGroups() {
        List<Group> list;
        Session session = factory.getCurrentSession();
            Query query = session.createQuery("from Group");
            list = query.list();
        return list;
    }
}
