package ru.innopolis.stc9.dao.implementation;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.innopolis.stc9.dao.interfaces.AttendanceDao;
import ru.innopolis.stc9.dao.mappers.AttendanceMapper;
import ru.innopolis.stc9.db.connection.ConnectionManager;
import ru.innopolis.stc9.db.connection.ConnectionManagerImpl;
import ru.innopolis.stc9.pojo.Attendance;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.criteria.Root;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class AttendanceDaoImpl implements AttendanceDao {
    @Autowired
    private SessionFactory factory;

    private static ConnectionManager connectionManager = ConnectionManagerImpl.getInstance();
    private Logger logger = Logger.getLogger(AttendanceDaoImpl.class);

    @Override
    public boolean addLessonAttendance(int lessonId, int[] students) {
        boolean result = true;
        if (lessonId < 1 || students == null || students.length < 1)
            result = false;
        for (int studentId : students) {
            Attendance attendance = new Attendance();
            attendance.setLessonId(lessonId);
            attendance.setUserId(studentId);
            attendance.setAttended(true);
            try (Session session = factory.openSession()) {
                session.beginTransaction();
                session.save(attendance);
                session.getTransaction().commit();
            }
        }
        return result;
    }

    @Override
    public boolean updateAttendance(Attendance attendance) {
        if (attendance == null) return false;
        int result;
        try (Session session = factory.openSession()) {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaUpdate<Attendance> criteria = builder.createCriteriaUpdate(Attendance.class);
            Root<Attendance> root = criteria.from(Attendance.class);
            criteria.set(root.get(AttendanceMapper.ATTENDED), attendance.isAttended()).
                    where(builder.and(
                            builder.equal(root.get(AttendanceMapper.LESSONID), attendance.getLessonId()),
                            builder.equal(root.get(AttendanceMapper.USERID), attendance.getUserId())
                    ));
            Transaction transaction = session.beginTransaction();
            result = session.createQuery(criteria).executeUpdate();
            transaction.commit();
        }
        return result != 0;
    }

    @Override
    public int getNumberOfMissedLessons(int id) {
        int result = 0;
        if (id <= 0) {
            return result;
        }
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "SELECT " +
                             "COUNT(lessons.name)" +
                             "-" +
                             "(SELECT COUNT(attendance.user_id) FROM attendance WHERE attendance.user_id = ?) as number " +
                             "FROM lessons")) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    result = resultSet.getInt("number");
                }
                logger.info("number of missed lessons = " + id);
            }
        } catch (SQLException e) {
            logger.info(e.getMessage());
        }
        return result;
    }

    /**
     * Возвращает список студентов из определенной группы, отмеченных ранее присутствующими на определенном уроке.
     *
     * @param groupId  id группы
     * @param lessonId id урока
     * @return объект List<User>
     */
    public List<Attendance> getLessonAttendance(int lessonId, int groupId) {
        List<Attendance> result = new ArrayList<>();
        if (groupId < 1 && lessonId < 1) {
            return result;
        }
        logger.info("Started requesting attendance by group id " + groupId + " and lesson id " + lessonId);
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "SELECT attendance.user_id, attendance.attended FROM attendance " +
                             "INNER JOIN users ON attendance.user_id = users.id " +
                             "WHERE attendance.lesson_id = ? AND users.group_id = ?"
             )) {
            statement.setInt(1, lessonId);
            statement.setInt(2, groupId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Attendance attendance = new Attendance();
                    attendance.setUserId(resultSet.getInt("user_id"));
                    attendance.setLessonId(lessonId);
                    attendance.setAttended(resultSet.getBoolean("attended"));
                    result.add(attendance);
                }
                return result;
            }
        } catch (SQLException e) {
            logger.error(e.getMessage());
            return result;
        }
    }
}
