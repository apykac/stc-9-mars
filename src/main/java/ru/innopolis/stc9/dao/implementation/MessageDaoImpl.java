package ru.innopolis.stc9.dao.implementation;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.innopolis.stc9.dao.interfaces.MessageDao;
import ru.innopolis.stc9.dao.mappers.MessageMapper;
import ru.innopolis.stc9.pojo.Message;
import ru.innopolis.stc9.pojo.User;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@Repository
public class MessageDaoImpl implements MessageDao {
    @Autowired
    private SessionFactory factory;

    @Override
    public boolean addMessage(Message message) {
        if (message == null) return false;
        try (Session session = factory.openSession()) {
            session.beginTransaction();
            session.save(message);
            session.getTransaction().commit();
        }
        return true;
    }

    @Override
    public List<Message> getAllMessagesByRole(String role) {
        if ((role == null) || role.isEmpty()) return new ArrayList<>();
        List<Message> resultList;
        try (Session session = factory.openSession()) {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Message> criteria = builder.createQuery(Message.class);
            Root<Message> root = criteria.from(Message.class);
            criteria.select(root).
                    where(builder.and(
                            builder.equal(root.get(MessageMapper.TOUSERGROUP), role),
                            builder.isNull(root.get(MessageMapper.TOUSERID))));
            resultList = session.createQuery(criteria).getResultList();
        }
        return resultList;
    }

    @Override
    public List<Message> getAllMessagesByToUserId(int toUserId) {
        if (toUserId < 0) return new ArrayList<>();
        List<Message> resultList;
        try (Session session = factory.openSession()) {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Message> criteria = builder.createQuery(Message.class);
            Root<Message> root = criteria.from(Message.class);
            criteria.select(root).
                    where(builder.equal(root.get(MessageMapper.TOUSERID), toUserId));
            resultList = session.createQuery(criteria).getResultList();
        }
        return resultList;
    }

    @Override
    public boolean deleteMessageById(int id) {
        if (id < 0) return false;
        int result;
        try (Session session = factory.openSession()) {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaDelete<Message> criteria = builder.createCriteriaDelete(Message.class);
            Root<Message> root = criteria.from(Message.class);
            criteria.where(builder.equal(root.get(MessageMapper.ID), id));
            Transaction transaction = session.beginTransaction();
            result = session.createQuery(criteria).executeUpdate();
            transaction.commit();
        }
        return result != 0;
    }

    @Override
    public Message getMessageById(int id) {
        if (id < 0) return null;
        Message message;
        try (Session session = factory.openSession()) {
            message = session.get(Message.class, id);
        }
        return message;
    }

    @Override
    public int getNumberOfMessage(User user) {
        if ((user == null) || (user.getId() < 0) ||
                (user.getPermissionGroup() == null) || user.getPermissionGroup().equals(""))
            return 0;
        Long result;
        try (Session session = factory.openSession()) {
            Query query = session.createQuery(
                    "SELECT count(*) FROM Message m where (m.user.id is null and m.toUserGroup = :role) or m.user.id = :userId");
            query.setParameter("userId", user.getId());
            query.setParameter("role", user.getPermissionGroup());
            result = (Long) query.uniqueResult();
        }
        return Math.toIntExact(result);
    }
}
