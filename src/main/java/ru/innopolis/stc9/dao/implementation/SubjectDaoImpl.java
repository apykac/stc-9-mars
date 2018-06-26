package ru.innopolis.stc9.dao.implementation;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.innopolis.stc9.dao.interfaces.SubjectDao;
import ru.innopolis.stc9.pojo.Subject;

import java.util.List;

/**
 * Класс реализует интерфейс SubjectDao
 */
@Repository
public class SubjectDaoImpl implements SubjectDao {
    private final SessionFactory factory;

    @Autowired
    public SubjectDaoImpl(SessionFactory factory) {
        this.factory = factory;
    }

    @Override
    public boolean addSubject(Subject subject) {
        if (subject == null) return false;
        Session session = factory.getCurrentSession();
        session.save(subject);
        return true;
    }

    @Override
    public boolean deleteSubject(int subjectId) {
        if (subjectId < 0) return false;
        Session session = factory.getCurrentSession();
        Subject subject = findById(subjectId);
        session.delete(subject);
        return true;
    }

    @Override
    public List<Subject> findAllSubject() {
        Session session = factory.getCurrentSession();
        Query query = session.createQuery("FROM Subject");
        return query.getResultList();
    }

    @Override
    public Subject findById(int id) {
        if (id < 0) return null;
        Subject subject;
        Session session = factory.getCurrentSession();
        subject = session.get(Subject.class, id);
        return subject;
    }
}
