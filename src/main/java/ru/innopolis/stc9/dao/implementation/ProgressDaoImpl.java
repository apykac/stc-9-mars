package ru.innopolis.stc9.dao.implementation;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.innopolis.stc9.dao.interfaces.ProgressDao;
import ru.innopolis.stc9.dao.mappers.*;
import ru.innopolis.stc9.pojo.*;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ProgressDaoImpl implements ProgressDao {
    @Autowired
    private SessionFactory factory;

    public List<Progress> getProgress() {
        List<Progress> resultList;
        try (Session session = factory.openSession()) {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Object[]> criteria = builder.createQuery(Object[].class);

            Root<Mark> rootMark = criteria.from(Mark.class);
            Root<User> rootUser = criteria.from(User.class);
            Root<Lessons> rootLesson = criteria.from(Lessons.class);
            Root<Subject> rootSubject = criteria.from(Subject.class);
            Root<Group> rootGroup = criteria.from(Group.class);

            criteria.multiselect(rootMark, rootUser, rootLesson, rootSubject, rootGroup).
                    where(builder.and(
                            builder.and(
                                    builder.equal(rootUser.get(UserMapper.ID), rootMark.get(MarkMapper.USERID)),
                                    builder.equal(rootLesson.get(LessonMapper.ID), rootMark.get(MarkMapper.LESSONID))),
                            builder.and(
                                    builder.equal(rootLesson.get(LessonMapper.SUBJID), rootSubject.get(SubjectMapper.ID)),
                                    builder.equal(rootUser.get(UserMapper.GROUPID), rootGroup.get(GroupMapper.ID)))
                    ));
            resultList = getListFromMultiList(session.createQuery(criteria).getResultList());
        }
        return resultList;
    }

    private List<Progress> getListFromMultiList(List<Object[]> multiList) {
        List<Progress> resultList = new ArrayList<>();
        for (Object[] values : multiList) {
            resultList.add(new Progress(
                    ((Mark) values[0]).getId(),
                    ((Mark) values[0]).getValue(),
                    ((User) values[1]).getFirstName(),
                    ((User) values[1]).getSecondName(),
                    ((Lessons) values[2]).getName(),
                    ((Lessons) values[2]).getDate(),
                    ((Subject) values[3]).getName(),
                    ((Group) values[4]).getName(),
                    ((User) values[1]).getLogin()));
        }
        return resultList;
    }
}
