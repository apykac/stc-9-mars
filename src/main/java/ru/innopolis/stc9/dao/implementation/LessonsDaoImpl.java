package ru.innopolis.stc9.dao.implementation;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.innopolis.stc9.dao.interfaces.LessonsDao;
import ru.innopolis.stc9.dao.mappers.LessonMapper;
import ru.innopolis.stc9.dao.mappers.SubjectMapper;
import ru.innopolis.stc9.pojo.Lessons;
import ru.innopolis.stc9.pojo.Subject;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@Repository
public class LessonsDaoImpl implements LessonsDao {
    @Autowired
    private SessionFactory factory;

    @Override
    public boolean addLesson(Lessons lesson) {
        if (lesson == null) return false;
        lesson.setSname("");
        try (Session session = factory.openSession()) {
            session.beginTransaction();
            session.save(lesson);
            session.getTransaction().commit();
        }
        return true;
    }

    @Override
    public boolean deleteLesson(long lessonId) {
        if (lessonId < 0) return false;
        int result;
        try (Session session = factory.openSession()) {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaDelete<Lessons> criteria = builder.createCriteriaDelete(Lessons.class);
            Root<Lessons> root = criteria.from(Lessons.class);
            criteria.where(builder.equal(root.get(LessonMapper.ID), lessonId));
            Transaction transaction = session.beginTransaction();
            result = session.createQuery(criteria).executeUpdate();
            transaction.commit();
        }
        return result != 0;
    }

    @Override
    public List<Lessons> findAllLessons() {
        List<Lessons> resultList = new ArrayList<>();
        try (Session session = factory.openSession()) {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Object[]> criteria = builder.createQuery(Object[].class);
            Root<Lessons> lessonsRoot = criteria.from(Lessons.class);
            Root<Subject> subjectRoot = criteria.from(Subject.class);
            criteria.multiselect(lessonsRoot, subjectRoot).
                    where(builder.equal(
                            lessonsRoot.get(LessonMapper.SUBJID),
                            subjectRoot.get(SubjectMapper.ID)));
            List<Object[]> list = session.createQuery(criteria).getResultList();
            for (Object[] values : list) {
                ((Lessons) values[0]).setSname(((Subject) values[1]).getName());
                resultList.add((Lessons) values[0]);
            }
        }
        return resultList;
    }

    @Override
    public Lessons getLessonById(long id) {
        if (id < 0) return null;
        Lessons lesson;
        try (Session session = factory.openSession()) {
            lesson = session.get(Lessons.class, id);
        }
        return lesson;
    }
}
