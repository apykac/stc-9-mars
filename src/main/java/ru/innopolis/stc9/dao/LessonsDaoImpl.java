package ru.innopolis.stc9.dao;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import ru.innopolis.stc9.db.connection.ConnectionManager;
import ru.innopolis.stc9.db.connection.ConnectionManagerImpl;
import ru.innopolis.stc9.pojo.Lessons;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Класс реализует интерфейс LessonsDao
 */
@Component
public class LessonsDaoImpl implements LessonsDao {
    private static ConnectionManager connectionManager = ConnectionManagerImpl.getInstance();
    private Logger logger = Logger.getLogger(LessonsDaoImpl.class);

    @Override
    public boolean addLesson(Lessons lesson) {
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement("INSERT " +
                     "INTO lessons(subject_id, date, name) " +
                     "VALUES (?, ?, ?)")) {
            statement.setInt(1, lesson.getSubject_id());
            statement.setDate(2, lesson.getDate());
            statement.setString(3, lesson.getName());
            statement.execute();
            return true;
        } catch (SQLException e) {
            logger.info(e.getMessage());
            return false;
        }
    }

    @Override
    public boolean deleteLesson(int lessonId) {
        if (lessonId < 0) return false;
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "DELETE FROM lessons WHERE id = ?")) {
            statement.setInt(1, lessonId);
            statement.execute();
            logger.info(" delete lessons id=" + lessonId);
            return true;
        } catch (SQLException e) {
            logger.error(e.getMessage());
            return false;
        }
    }

    @Override
    public List<Lessons> findAllLessons() {
        logger.info("Lessons list requested.");
        List<Lessons> result = new ArrayList<>();
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "SELECT lessons.id, lessons.subject_id, subjects.sname, lessons.date, lessons.name " +
                             "FROM lessons " +
                             "INNER JOIN subjects ON lessons.subject_id = subjects.id")) {
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Lessons lesson = new Lessons(
                            resultSet.getInt("id"),
                            resultSet.getInt("subject_id"),
                            resultSet.getString("sname"),
                            resultSet.getDate("date"),
                            resultSet.getString("name"));
                    result.add(lesson);
                }
                logger.info("Lessons list returned successfully");
            }
        } catch (SQLException e) {
            logger.info(e.getMessage());
        }
        return result;
    }

    @Override
    public Lessons getLessonById(int id) {
        Lessons result = null;
        logger.info("Started requesting lesson.");
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "SELECT * FROM lessons WHERE id = ?")) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    result = new Lessons(
                            resultSet.getInt("id"),
                            resultSet.getInt("subject_id"),
                            resultSet.getDate("date"),
                            resultSet.getString("name"));
                }
                logger.info("Lesson with id " + id + " returned successfully");
            }
        } catch (SQLException e) {
            logger.info(e.getMessage());
        }
        return result;
    }
}
