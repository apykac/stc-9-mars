package ru.innopolis.stc9.dao.implementation;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.innopolis.stc9.dao.interfaces.ProgressDao;
import ru.innopolis.stc9.dao.mappers.*;
import ru.innopolis.stc9.db.connection.ConnectionManager;
import ru.innopolis.stc9.db.connection.ConnectionManagerImpl;
import ru.innopolis.stc9.pojo.*;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ProgressDaoImpl implements ProgressDao {
    @Autowired
    private SessionFactory factory;
    private static ConnectionManager connectionManager = ConnectionManagerImpl.getInstance();
    private Logger logger = Logger.getLogger(ProgressDaoImpl.class);

    /**
     * Если пользователь - студент, то добавляем условие, чтобы каждый студент мог видеть только свои данные
     * если не студент, то выводим весь список
     */
    private String getProgressQuery() {
        return "SELECT marks.id, value, u2.first_name firstName, u2.second_name secondName, " +
                "l3.name lessonsName, l3.date date, s4.sname subjectName, g5.gname groupName, u2.login login " +
                "FROM marks " +
                "INNER JOIN users u2 ON marks.user_id = u2.id " +
                "INNER JOIN lessons l3 ON marks.lesson_id = l3.id " +
                "INNER JOIN subjects s4 ON l3.subject_id = s4.id " +
                "INNER JOIN stgroup g5 ON u2.group_id = g5.id";
    }

    public List<Progress> getProgress() {
        List<Progress> resultList = new ArrayList<>();
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
            List<Object[]> result = session.createQuery(criteria).getResultList();

            for (Object[] values : result) {
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
        }
        return resultList;
    }

    private Progress getResultSet(ResultSet resultSet) throws SQLException {
        Progress progress = new Progress();
        progress.setId(resultSet.getInt("id"));
        progress.setValue(resultSet.getInt("value"));
        progress.setFirstName(resultSet.getString("firstName"));
        progress.setSecondName(resultSet.getString("secondName"));
        progress.setLessonsName(resultSet.getString("lessonsName"));
        progress.setDate(resultSet.getDate("date"));
        progress.setSubjectName(resultSet.getString("subjectName"));
        progress.setGroupName(resultSet.getString("groupName"));
        progress.setLogin(resultSet.getString("login"));
        return progress;
    }

    /**
     * Получаем список оценок
     */
    /*@Override
    public List<Progress> getProgress() {
        logger.info("Progress list requested");
        List<Progress> result = new ArrayList<>();
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(getProgressQuery())) {
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    result.add(getResultSet(resultSet));
                }
                logger.info("Progress list returned successfully");
            }
        } catch (SQLException e) {
            logger.info(e.getMessage());
        }
        return result;
    }*/
}
