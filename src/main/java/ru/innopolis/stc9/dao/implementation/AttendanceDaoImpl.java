package ru.innopolis.stc9.dao.implementation;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import ru.innopolis.stc9.dao.interfaces.AttendanceDao;
import ru.innopolis.stc9.db.connection.ConnectionManager;
import ru.innopolis.stc9.db.connection.ConnectionManagerImpl;
import ru.innopolis.stc9.pojo.Attendance;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Component
public class AttendanceDaoImpl implements AttendanceDao {

    private static ConnectionManager connectionManager = ConnectionManagerImpl.getInstance();
    private Logger logger = Logger.getLogger(AttendanceDaoImpl.class);

    @Override
    public boolean addLessonAttendance(int lessonId, int[] students) {
        boolean result = true;
        if (lessonId < 1 || students == null || students.length < 1) {
            result = false;
        }
        for (int studentId : students) {
            try (Connection connection = connectionManager.getConnection();
                 PreparedStatement statement = connection.prepareStatement(
                         "INSERT INTO attendance (lesson_id, user_id, attended)" +
                                 "VALUES (?,?, TRUE)"
                 )) {
                statement.setInt(1, lessonId);
                statement.setInt(2, studentId);
                statement.execute();
            } catch (SQLException e) {
                logger.error(e.getMessage());
                result = false;
            }
        }
        return result;
    }

    public boolean updateAttendance(Attendance attendance) {
        if (attendance == null) {
            return false;
        }
        logger.info("Started updating attendance");
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "UPDATE attendance SET attended = ? WHERE " +
                             "user_id = ? AND lesson_id=?"
             )) {
            statement.setBoolean(1, attendance.isAttended());
            statement.setInt(2, attendance.getUserId());
            statement.setInt(3, attendance.getLessonId());
            statement.execute();
            logger.info("Attendance successfully updated");
            return true;
        } catch (SQLException e) {
            logger.error(e.getMessage());
            return false;
        }
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