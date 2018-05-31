package ru.innopolis.stc9.dao;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import ru.innopolis.stc9.db.connection.ConnectionManager;
import ru.innopolis.stc9.db.connection.ConnectionManagerImpl;
import ru.innopolis.stc9.pojo.Mark;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Component
public class MarkDaoImpl implements MarkDao {
    private static ConnectionManager connectionManager = ConnectionManagerImpl.getInstance();
    private Logger logger = Logger.getLogger(MarkDaoImpl.class);

    @Override
    public List<Mark> getMarksByLessonId(int lessonId) {
        logger.info("Marks by lesson id " + lessonId + " requested");
        List<Mark> result = new ArrayList<>();
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "SELECT * FROM marks WHERE lesson_id = ?"
             )) {
            statement.setInt(1, lessonId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Mark mark = MarkMapper.getMarkFromResultSet(resultSet);
                    result.add(mark);
                }
                logger.info("Marks list returned successfully");
            }

        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
        return result;
    }

    @Override
    public Mark getMarkById(int id) {
        logger.info("Mark with id " + id + " requested");
        Mark result = null;
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "SELECT * FROM marks WHERE id = ?"
             )) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    result = MarkMapper.getMarkFromResultSet(resultSet);
                    logger.info("Mark with id " + id + " returned successfully");
                }
            }
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
        return result;
    }

    @Override
    public boolean updateMark(Mark mark) {
        if (mark == null) {
            return false;
        }
        logger.info("Started updating mark with id " + mark.getId());
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "UPDATE marks SET value = ?, comment = ? WHERE id = ?"
             )) {
            statement.setInt(1, mark.getValue());
            statement.setString(2, mark.getComment());
            statement.setInt(3, mark.getId());
            statement.execute();
            logger.info("Mark with id " + mark.getId() + " updated.");
            return true;
        } catch (SQLException e) {
            logger.error(e.getMessage());
            return false;
        }
    }


}
