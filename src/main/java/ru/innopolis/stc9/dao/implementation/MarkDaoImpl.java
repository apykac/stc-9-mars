package ru.innopolis.stc9.dao.implementation;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.innopolis.stc9.dao.interfaces.MarkDao;
import ru.innopolis.stc9.dao.mappers.MarkMapper;
import ru.innopolis.stc9.pojo.Lessons;
import ru.innopolis.stc9.pojo.Mark;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@Repository
@Transactional
public class MarkDaoImpl implements MarkDao {
    @Autowired
    private SessionFactory factory;

    @Override
    public List<Mark> getMarksByLessonId(int lessonId) {
        if (lessonId < 0) return new ArrayList<>();
        List<Mark> resultList;
        try (Session session = factory.openSession()) {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Mark> criteria = builder.createQuery(Mark.class);
            Root<Mark> root = criteria.from(Mark.class);
            criteria.select(root);
            criteria.where(builder.equal(root.get(MarkMapper.LESSONID), lessonId));
            resultList = session.createQuery(criteria).getResultList();
        }
        return resultList;
    }

    @Override
    public List<Mark> getMarksByLesson(Lessons lesson) {
        if (lesson == null) return new ArrayList<>();
        List result;
        Session session = factory.openSession();
        result = session.createQuery("FROM Mark WHERE lesson = :lesson").setParameter("lesson", lesson).list();
        session.close();
        return result;
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

    /*@Override
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
    }*/

    @Override
    public boolean updateMark(Mark mark) {
        if (mark == null) return false;
        Session session = factory.getCurrentSession();
        session.update(mark);
        return true;
    }


}
