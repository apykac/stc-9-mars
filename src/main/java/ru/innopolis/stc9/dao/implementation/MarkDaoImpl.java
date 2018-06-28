package ru.innopolis.stc9.dao.implementation;

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
        resultList = session.createQuery("FROM Mark WHERE lesson.id = :lessonId").setParameter("lessonId", lessonId).list();
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
        Session session = factory.getCurrentSession();
        session.update(mark);
        return true;
    }

}
