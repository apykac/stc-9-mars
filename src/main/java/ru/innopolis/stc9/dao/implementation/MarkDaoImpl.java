package ru.innopolis.stc9.dao.implementation;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.innopolis.stc9.dao.interfaces.MarkDao;
import ru.innopolis.stc9.dao.mappers.MarkMapper;
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

    @Override
    public List<Mark> getMarksByLessonId(int lessonId) {
        if (lessonId < 0) return new ArrayList<>();
        Session session = factory.getCurrentSession();
        Query query = session.createQuery("FROM Mark m WHERE m.lesson.id = :lessonId");
        query.setParameter("lessonId", lessonId);
        List<Mark> marks = query.getResultList();
        for (Mark mark : marks) {
            Hibernate.initialize(mark.getStudent());
            Hibernate.initialize(mark.getLesson());
        }
        return marks;
    }

    @Override
    public Mark getMarkById(int id) {
        if (id < 0) return null;
        Session session = factory.getCurrentSession();
        return session.get(Mark.class, id);
    }

    @Override
    public boolean addMark(Mark mark) {
        if (mark == null) return false;
        mark.setComment("");
        mark.setValue(0);
        Session session = factory.getCurrentSession();
        session.save(mark);
        return true;
    }

    @Override
    public boolean updateMark(Mark mark) {
        if (mark == null) return false;
        int result;
        Session session = factory.getCurrentSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaUpdate<Mark> criteria = builder.createCriteriaUpdate(Mark.class);
        Root<Mark> root = criteria.from(Mark.class);
        criteria.set(root.get(MarkMapper.VALUE), mark.getValue()).
                set(root.get(MarkMapper.COMMENT), mark.getComment()).
                where(builder.equal(root.get(MarkMapper.ID), mark.getId()));
        result = session.createQuery(criteria).executeUpdate();
        return result != 0;
    }

    @Override
    public Mark getMarkByIdWithFullInfo(int id) {
        if (id < 0) return null;
        Session session = factory.getCurrentSession();
        Mark mark = session.get(Mark.class, id);
        Hibernate.initialize(mark.getLesson());
        Hibernate.initialize(mark.getStudent());
        return mark;
    }


}
