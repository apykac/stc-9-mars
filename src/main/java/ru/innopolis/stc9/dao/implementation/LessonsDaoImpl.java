package ru.innopolis.stc9.dao.implementation;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.innopolis.stc9.dao.interfaces.LessonsDao;
import ru.innopolis.stc9.dao.mappers.LessonMapper;
import ru.innopolis.stc9.pojo.Lessons;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.Root;
import java.util.List;

@Repository
public class LessonsDaoImpl implements LessonsDao {
    private final SessionFactory factory;

    @Autowired
    public LessonsDaoImpl(SessionFactory factory) {
        this.factory = factory;
    }

    @Override
    public boolean addLesson(Lessons lesson) {
        if (lesson == null) return false;
        Session session = factory.getCurrentSession();
        session.save(lesson);
        return true;
    }

    @Override
    public boolean deleteLesson(int lessonId) {
        if (lessonId < 0) return false;
        int result;
        Session session = factory.getCurrentSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaDelete<Lessons> criteria = builder.createCriteriaDelete(Lessons.class);
        Root<Lessons> root = criteria.from(Lessons.class);
        criteria.where(builder.equal(root.get(LessonMapper.ID), lessonId));
        result = session.createQuery(criteria).executeUpdate();
        return result != 0;
    }

    @Override
    public List<Lessons> findAllLessonsWithSubjects() {
        Session session = factory.getCurrentSession();
        Query query = session.createQuery("FROM Lessons");
        List<Lessons> lessons = query.getResultList();
        for (Lessons lesson : lessons)
            Hibernate.initialize(lesson.getSubject());
        return lessons;
    }

    @Override
    public Lessons getLessonById(int id) {
        if (id < 0) return null;
        Lessons lesson;
        Session session = factory.getCurrentSession();
        lesson = session.get(Lessons.class, id);
        return lesson;
    }
}
