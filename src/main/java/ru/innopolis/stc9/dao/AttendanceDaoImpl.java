package ru.innopolis.stc9.dao;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import ru.innopolis.stc9.db.connection.ConnectionManager;
import ru.innopolis.stc9.db.connection.ConnectionManagerImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
                         "INSERT INTO attendance (lesson_id, user_id)" +
                                 "VALUES (?,?)"
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


}
