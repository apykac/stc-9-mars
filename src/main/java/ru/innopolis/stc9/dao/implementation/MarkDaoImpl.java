package ru.innopolis.stc9.dao.implementation;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.innopolis.stc9.dao.interfaces.MarkDao;
import ru.innopolis.stc9.pojo.Mark;

import java.util.ArrayList;
import java.util.List;

@Repository
public class MarkDaoImpl implements MarkDao {
    @Autowired
    private SessionFactory factory;


    @Override
    public List<Mark> getMarksByLessonId(int lessonId) {
        if (lessonId < 0) return new ArrayList<>();
        List<Mark> resultList;
        Session session = factory.getCurrentSession();
        resultList = session.
                createQuery("FROM Mark WHERE lesson.id = :lessonId").
                setParameter("lessonId", lessonId).
                list();
        boolean isbegin = true;
        for (Mark mark : resultList) {
            if (isbegin) {
                Hibernate.initialize(mark.getLesson());
                isbegin = false;
            }
            Hibernate.initialize(mark.getStudent());
        }
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
        Session session = factory.getCurrentSession();
        session.save(mark);
        return true;
    }

    @Override
    public boolean updateMark(Mark mark) {
        if (mark == null) return false;
        Session session = factory.getCurrentSession();
        session.update(mark);
        return true;
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
