package ru.innopolis.stc9.dao.implementation;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.innopolis.stc9.dao.interfaces.MessageDao;
import ru.innopolis.stc9.pojo.Message;
import ru.innopolis.stc9.pojo.User;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@Repository
public class MessageDaoImpl implements MessageDao {
    private SessionFactory factory;

    @Autowired
    public MessageDaoImpl(SessionFactory factory) {
        this.factory = factory;
    }

    @Override
    public boolean addMessage(Message message, Integer toUserId, Integer fromUserId) {
        if (message == null) return false;
        Session session = factory.getCurrentSession();
        message.setToUser(toUserId == null ? null : session.get(User.class, toUserId));
        message.setFromUser(fromUserId == null ? null : session.get(User.class, fromUserId));
        session.save(message);
        return true;
    }

    @Override
    public List<Message> getAllMessagesByRole(User user) {
        if ((user == null) || (user.getId() < 0)) new ArrayList<>();
        Session session = factory.getCurrentSession();
        Query query = session.createQuery("FROM Message m WHERE m.toUserGroup =:role");
        query.setParameter("role", user.getPermissionGroup());
        return query.getResultList();
    }

    @Override
    public List<Message> getAllMessagesByToUserId(User user) {
        if (user.getId() < 0) return new ArrayList<>();
        Session session = factory.getCurrentSession();
        user = session.get(User.class, user.getId());
        Hibernate.initialize(user.getIncomingMessages());
        return user.getIncomingMessages();
    }

    @Override
    public boolean deleteMessageById(int id) {
        if (id < 0) return false;
        Session session = factory.getCurrentSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaDelete<Message> criteria = builder.createCriteriaDelete(Message.class);
        Root<Message> root = criteria.from(Message.class);
        criteria.where(builder.equal(root.get("id"), id));
        return session.createQuery(criteria).executeUpdate() != 0;
    }

    @Override
    public Message getMessageByIdWithFromUser(int id) {
        if (id < 0) return null;
        Session session = factory.getCurrentSession();
        Message message = session.get(Message.class, id);
        Hibernate.initialize(message.getFromUser());
        return message;
    }

    @Override
    public int getNumberOfMessage(User user) {
        if ((user == null) || (user.getId() < 0) ||
                (user.getPermissionGroup() == null) || user.getPermissionGroup().equals(""))
            return 0;
        Session session = factory.getCurrentSession();
        Query query = session.createQuery(
                "SELECT count(*) FROM Message m where (m.toUser.id is null and m.toUserGroup = :role) or m.toUser.id = :toUserId");
        query.setParameter("toUserId", user.getId());
        query.setParameter("role", user.getPermissionGroup());
        return Math.toIntExact((Long) query.uniqueResult());
    }
}
