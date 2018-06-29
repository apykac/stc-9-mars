package ru.innopolis.stc9.dao.implementation;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.innopolis.stc9.dao.interfaces.HomeWorkDao;
import ru.innopolis.stc9.pojo.HomeWork;

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
    public HomeWork findById(int id) {
        if (id < 0) return null;
        HomeWork homeWork;
        try (Session session = factory.openSession()) {
            homeWork = session.get(HomeWork.class, id);
        }
        return homeWork;
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
