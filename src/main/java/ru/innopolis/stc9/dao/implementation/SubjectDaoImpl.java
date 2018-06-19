package ru.innopolis.stc9.dao.implementation;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.innopolis.stc9.dao.interfaces.SubjectDao;
import ru.innopolis.stc9.dao.mappers.SubjectMapper;
import ru.innopolis.stc9.pojo.Subject;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

/**
 * Класс реализует интерфейс SubjectDao
 */
@Component
public class SubjectDaoImpl implements SubjectDao {
    @Autowired
    private SessionFactory factory;

    @Override
    public boolean addSubject(Subject subject) {

        if (subject == null) return false;
        try (Session session = factory.openSession()) {
            session.beginTransaction();
            session.save(subject);
            session.getTransaction().commit();
        }
        return true;
    }

    @Override
    public boolean deleteSubject(int subjectId) {
        if (subjectId < 0) return false;
        int result;
        try (Session session = factory.openSession()) {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaDelete<Subject> criteria = builder.createCriteriaDelete(Subject.class);
            Root<Subject> root = criteria.from(Subject.class);
            criteria.where(builder.equal(root.get(SubjectMapper.ID), subjectId));
            Transaction transaction = session.beginTransaction();
            result = session.createQuery(criteria).executeUpdate();
            transaction.commit();
        }
        return result != 0;
    }

    @Override
    public List<Subject> findAllSubject() {
        List<Subject> resultList;
        try (Session session = factory.openSession()) {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Subject> criteria = builder.createQuery(Subject.class);
            Root<Subject> root = criteria.from(Subject.class);
            criteria.select(root);
            resultList = session.createQuery(criteria).getResultList();
        }
        return resultList;
    }

    @Override
    public Subject findById(int id) {
        if (id < 0) return null;
        Subject subject;
        try (Session session = factory.openSession()) {
            subject = session.get(Subject.class, id);
        }
        return subject;
    }
}
