package ru.innopolis.stc9.dao.implementation;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
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
        try (Session session = factory.openSession()) {
            session.beginTransaction();
            session.save(homeWork);
            session.getTransaction().commit();
        }
        return true;
    }

    @Override
    public boolean updateHomeWork(HomeWork homeWork) {
        if (homeWork == null) return false;
        int result;
        try (Session session = factory.openSession()) {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaUpdate<HomeWork> criteria = builder.createCriteriaUpdate(HomeWork.class);
            Root<HomeWork> root = criteria.from(HomeWork.class);
            criteria.set(root.get(HomeWokMapper.HWURL), homeWork.getHomeWorkURL()).
                    set(root.get(HomeWokMapper.STID), homeWork.getStudentId()).
                    set(root.get(HomeWokMapper.LESSID), homeWork.getLessonId()).
                    where(builder.equal(root.get(HomeWokMapper.ID), homeWork.getId()));
            Transaction transaction = session.beginTransaction();
            result = session.createQuery(criteria).executeUpdate();
            transaction.commit();
        }
        return result != 0;
    }

    @Override
    public HomeWork findById(int id) {
        if (id < 0) return null;
        HomeWork homeWork;
        try (Session session = factory.openSession()) {
            homeWork = session.get(HomeWork.class, id);
        }
        return homeWork;
    }

    @Override
    public HomeWork findByStudentId(int studentId) {
        if (studentId < 0) return null;
        List<HomeWork> resultList;
        try (Session session = factory.openSession()) {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<HomeWork> criteria = builder.createQuery(HomeWork.class);
            Root<HomeWork> root = criteria.from(HomeWork.class);
            criteria.select(root);
            criteria.where(builder.equal(root.get(HomeWokMapper.STID), studentId));
            resultList = session.createQuery(criteria).getResultList();
        }
        if (!resultList.isEmpty()) return resultList.get(resultList.size() - 1);
        else return null;
    }

    @Override
    public HomeWork findByLessonId(int lessonId) {
        if (lessonId < 0) return null;
        List<HomeWork> resultList;
        try (Session session = factory.openSession()) {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<HomeWork> criteria = builder.createQuery(HomeWork.class);
            Root<HomeWork> root = criteria.from(HomeWork.class);
            criteria.select(root);
            criteria.where(builder.equal(root.get(HomeWokMapper.LESSID), lessonId));
            resultList = session.createQuery(criteria).getResultList();
        }
        if (!resultList.isEmpty()) return resultList.get(resultList.size() - 1);
        else return null;
    }

    @Override
    public List<HomeWork> getHomeWorkListByLessonId(int lessonId) {
        if (lessonId < 0) return new ArrayList<>();
        List<HomeWork> resultList;
        try (Session session = factory.openSession()) {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<HomeWork> criteria = builder.createQuery(HomeWork.class);
            Root<HomeWork> root = criteria.from(HomeWork.class);
            criteria.select(root).
                    where(builder.equal(root.get(HomeWokMapper.LESSID), lessonId));
            resultList = session.createQuery(criteria).getResultList();
        }
        return resultList;
    }

    @Override
    public HomeWork findHomeWorkByStudentIdAndLessonId(int studentId, int lessonId) {
        if (lessonId < 0 || studentId < 0) return null;
        List<HomeWork> resultList;
        try (Session session = factory.openSession()) {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<HomeWork> criteria = builder.createQuery(HomeWork.class);
            Root<HomeWork> root = criteria.from(HomeWork.class);
            criteria.select(root).
                    where(builder.and(
                            builder.equal(root.get(HomeWokMapper.STID), studentId),
                            builder.equal(root.get(HomeWokMapper.LESSID), lessonId))
                    );
            resultList = session.createQuery(criteria).getResultList();
        }
        if (!resultList.isEmpty()) return resultList.get(resultList.size() - 1);
        else return null;
    }

    @Override
    public List<HomeWork> findAllHomeWork() {
        List<HomeWork> resultList;
        try (Session session = factory.openSession()) {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<HomeWork> criteria = builder.createQuery(HomeWork.class);
            Root<HomeWork> root = criteria.from(HomeWork.class);
            criteria.select(root);
            resultList = session.createQuery(criteria).getResultList();
        }
        return resultList;
    }

    @Override
    public boolean deleteHomeWork(int id) {
        if (id < 0) return false;
        int result;
        try (Session session = factory.openSession()) {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaDelete<HomeWork> criteria = builder.createCriteriaDelete(HomeWork.class);
            Root<HomeWork> root = criteria.from(HomeWork.class);
            criteria.where(builder.equal(root.get(HomeWokMapper.ID), id));
            Transaction transaction = session.beginTransaction();
            result = session.createQuery(criteria).executeUpdate();
            transaction.commit();
        }
        return result != 0;
    }
}
