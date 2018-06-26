package ru.innopolis.stc9.dao.implementation;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.innopolis.stc9.dao.interfaces.LessonsDao;
import ru.innopolis.stc9.dao.interfaces.MarkDao;
import ru.innopolis.stc9.dao.mappers.MarkMapper;
import ru.innopolis.stc9.pojo.Lessons;
import ru.innopolis.stc9.pojo.Mark;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@Repository
public class MarkDaoImpl implements MarkDao {
    @Autowired
    private SessionFactory factory;
    @Autowired
    private LessonsDao lessonsDao;

    @Override
    public List<Mark> getMarksByLessonId(int lessonId) {
        if (lessonId < 0) return new ArrayList<>();
        List<Mark> resultList;
        Session session = factory.getCurrentSession();
        Lessons lesson = lessonsDao.getLessonById(lessonId);
        resultList = session.createQuery("FROM Mark WHERE lesson = :lesson").setParameter("lesson", lesson).list();
        return resultList;
    }

    @Override
    public Mark getMarkById(int id) {
        if (id < 0) return null;
        Mark mark;
        try (Session session = factory.openSession()) {
            mark = session.get(Mark.class, id);
        }
        return mark;
    }

    @Override
    public boolean addMark(Mark mark) {
        if (mark == null) return false;
        mark.setComment("");
        mark.setValue(0);
        try (Session session = factory.openSession()) {
            session.beginTransaction();
            session.save(mark);
            session.getTransaction().commit();
        }
        return true;
    }

    @Override
    public boolean updateMark(Mark mark) {
        if (mark == null) return false;
        int result;
        try (Session session = factory.openSession()) {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaUpdate<Mark> criteria = builder.createCriteriaUpdate(Mark.class);
            Root<Mark> root = criteria.from(Mark.class);
            criteria.set(root.get(MarkMapper.VALUE), mark.getValue()).
                    set(root.get(MarkMapper.COMMENT), mark.getComment()).
                    where(builder.equal(root.get(MarkMapper.ID), mark.getId()));
            Transaction transaction = session.beginTransaction();
            result = session.createQuery(criteria).executeUpdate();
            transaction.commit();
        }
        return result != 0;
    }


}
