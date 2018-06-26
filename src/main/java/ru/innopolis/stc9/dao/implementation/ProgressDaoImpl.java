package ru.innopolis.stc9.dao.implementation;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.innopolis.stc9.dao.interfaces.ProgressDao;
import ru.innopolis.stc9.pojo.Mark;

import java.util.List;

@Repository
public class ProgressDaoImpl implements ProgressDao {
    private final SessionFactory factory;

    @Autowired
    public ProgressDaoImpl(SessionFactory factory) {
        this.factory = factory;
    }

    @Override
    public List<Mark> getMark() {
        Session session = factory.getCurrentSession();
        Query query = session.createQuery("from Mark");
        return query.getResultList();
    }
}
