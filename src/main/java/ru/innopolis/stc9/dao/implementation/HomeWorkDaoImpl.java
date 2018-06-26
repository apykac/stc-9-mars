package ru.innopolis.stc9.dao.implementation;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.innopolis.stc9.dao.interfaces.HomeWorkDao;
import ru.innopolis.stc9.dao.mappers.HomeWokMapper;
import ru.innopolis.stc9.pojo.HomeWork;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class HomeWorkDaoImpl implements HomeWorkDao {
    @Autowired
    private SessionFactory factory;

    @Override
    public boolean addHomeWork(HomeWork homeWork) {
        if (homeWork == null) return false;
        Session session = factory.getCurrentSession();
        session.save(homeWork);
        return true;
    }

    @Override
    public boolean updateHomeWork(HomeWork homeWork) {
        if (homeWork == null) return false;
        int result;
        Session session = factory.getCurrentSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaUpdate<HomeWork> criteria = builder.createCriteriaUpdate(HomeWork.class);
        Root<HomeWork> root = criteria.from(HomeWork.class);
        criteria.set(root.get(HomeWokMapper.HWURL), homeWork.getHomeWorkURL()).
                set(root.get(HomeWokMapper.STID), homeWork.getStudentId()).
                set(root.get(HomeWokMapper.LESSID), homeWork.getLessonId()).
                where(builder.equal(root.get(HomeWokMapper.ID), homeWork.getId()));
        result = session.createQuery(criteria).executeUpdate();
        return result != 0;
    }

    @Override
    public HomeWork findById(int id) {
        if (id < 0) return null;
        Session session = factory.getCurrentSession();
        return session.get(HomeWork.class, id);
    }

    @Override
    public HomeWork findByStudentId(int studentId) {
        if (studentId < 0) return null;
        List<HomeWork> resultList;
        Session session = factory.getCurrentSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<HomeWork> criteria = builder.createQuery(HomeWork.class);
        Root<HomeWork> root = criteria.from(HomeWork.class);
        criteria.select(root);
        criteria.where(builder.equal(root.get(HomeWokMapper.STID), studentId));
        resultList = session.createQuery(criteria).getResultList();

        if (!resultList.isEmpty()) return resultList.get(resultList.size() - 1);
        else return null;
    }

    @Override
    public HomeWork findByLessonId(int lessonId) {
        if (lessonId < 0) return null;
        List<HomeWork> resultList;
        Session session = factory.getCurrentSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<HomeWork> criteria = builder.createQuery(HomeWork.class);
        Root<HomeWork> root = criteria.from(HomeWork.class);
        criteria.select(root);
        criteria.where(builder.equal(root.get(HomeWokMapper.LESSID), lessonId));
        resultList = session.createQuery(criteria).getResultList();
        if (!resultList.isEmpty()) return resultList.get(resultList.size() - 1);
        else return null;
    }

    @Override
    public List<HomeWork> getHomeWorkListByLessonId(int lessonId) {
        if (lessonId < 0) return new ArrayList<>();
        Session session = factory.getCurrentSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<HomeWork> criteria = builder.createQuery(HomeWork.class);
        Root<HomeWork> root = criteria.from(HomeWork.class);
        criteria.select(root).
                where(builder.equal(root.get(HomeWokMapper.LESSID), lessonId));
        return session.createQuery(criteria).getResultList();
    }

    @Override
    public List<HomeWork> findAllHomeWork() {
        Session session = factory.getCurrentSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<HomeWork> criteria = builder.createQuery(HomeWork.class);
        Root<HomeWork> root = criteria.from(HomeWork.class);
        criteria.select(root);
        return session.createQuery(criteria).getResultList();
    }

    @Override
    public boolean deleteHomeWork(int id) {
        if (id < 0) return false;
        int result;
        Session session = factory.getCurrentSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaDelete<HomeWork> criteria = builder.createCriteriaDelete(HomeWork.class);
        Root<HomeWork> root = criteria.from(HomeWork.class);
        criteria.where(builder.equal(root.get(HomeWokMapper.ID), id));
        result = session.createQuery(criteria).executeUpdate();
        return result != 0;
    }

    @Override
    public HomeWork findHomeWorkByMarkId(int markId) {
        if (markId < 0) return null;
        Session session = factory.getCurrentSession();
        Query query = session.createQuery(
                "SELECT hw FROM HomeWork hw, Mark m " +
                        "WHERE m.id = :markId AND (m.student.id = hw.student.id AND m.lesson.id = hw.lesson.id)");
        query.setParameter("markId", markId);
        return (HomeWork) query.uniqueResult();
    }
}
