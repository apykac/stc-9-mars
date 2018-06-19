package ru.innopolis.stc9.dao.implementation;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.innopolis.stc9.dao.interfaces.EducationDao;
import ru.innopolis.stc9.pojo.Education;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;


@Repository
public class EducationDaoImpl implements EducationDao {
    @Autowired
    private SessionFactory factory;

    @Override
    public List<Education> findAllEducations() {
        List<Education> resultList;
        try (Session session = factory.openSession()) {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Education> criteria = builder.createQuery(Education.class);
            Root<Education> root = criteria.from(Education.class);
            criteria.select(root);
            resultList = session.createQuery(criteria).getResultList();
        }
        return resultList;
    }
}
