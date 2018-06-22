package ru.innopolis.stc9.dao.implementation;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.innopolis.stc9.dao.interfaces.GroupDao;
import ru.innopolis.stc9.dao.mappers.GroupMapper;
import ru.innopolis.stc9.pojo.Group;

import javax.persistence.criteria.*;
import java.util.List;


@Repository
public class GroupDaoImpl implements GroupDao {
    @Autowired
    private SessionFactory factory;

    @Override
    public boolean addGroup(Group group) {
        if (group == null) return false;
        try (Session session = factory.openSession()) {
            session.beginTransaction();
            session.save(group);
            session.getTransaction().commit();
        }
        return true;
    }

    @Override
    public boolean updateGroup(Group group) {
        if (group == null) return false;
        int result;
        try (Session session = factory.openSession()) {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaUpdate<Group> criteria = builder.createCriteriaUpdate(Group.class);
            Root<Group> root = criteria.from(Group.class);
            criteria.set(root.get(GroupMapper.NAME), group.getName()).
                    where(builder.equal(root.get(GroupMapper.ID), group.getId()));
            Transaction transaction = session.beginTransaction();
            result = session.createQuery(criteria).executeUpdate();
            transaction.commit();
        }
        return result != 0;
    }

    @Override
    public boolean deleteGroup(long groupId) {
        if (groupId < 0) return false;
        int result;
        try (Session session = factory.openSession()) {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaDelete<Group> criteria = builder.createCriteriaDelete(Group.class);
            Root<Group> root = criteria.from(Group.class);
            criteria.where(builder.equal(root.get(GroupMapper.ID), groupId));
            Transaction transaction = session.beginTransaction();
            result = session.createQuery(criteria).executeUpdate();
            transaction.commit();
        }
        return result != 0;
    }

    @Override
    public Group findGroupById(Long id) {
        if ((id == null) || (id < 0)) return null;
        Group group;
        try (Session session = factory.openSession()) {
            group = session.get(Group.class, id);
        }
        return group;
    }

    @Override
    public Group findGroupByName(String name) {
        if ((name == null) || name.isEmpty()) return null;
        List<Group> resultList;
        try (Session session = factory.openSession()) {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Group> criteria = builder.createQuery(Group.class);
            Root<Group> root = criteria.from(Group.class);
            criteria.select(root);
            criteria.where(builder.equal(root.get(GroupMapper.NAME), name));
            resultList = session.createQuery(criteria).getResultList();
        }
        if (!resultList.isEmpty()) return resultList.get(0);
        else return null;
    }

    @Override
    public List<Group> findAllGroups() {
        List<Group> resultList;
        try (Session session = factory.openSession()) {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Group> criteria = builder.createQuery(Group.class);
            Root<Group> root = criteria.from(Group.class);
            criteria.select(root);
            resultList = session.createQuery(criteria).getResultList();
        }
        return resultList;
    }
}
