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
                    Mark mark = new Mark();
                    mark.setId(resultSet.getInt("id"));
                    mark.setUserId(resultSet.getInt("user_id"));
                    mark.setLessonId(resultSet.getInt("lesson_id"));
                    mark.setValue(resultSet.getInt("value"));
                    result.add(mark);
                }
                logger.info("Marks list returned successfully");
            }

        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
        return result;
    }
}
