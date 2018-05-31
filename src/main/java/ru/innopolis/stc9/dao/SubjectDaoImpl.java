package ru.innopolis.stc9.dao;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import ru.innopolis.stc9.db.connection.ConnectionManager;
import ru.innopolis.stc9.db.connection.ConnectionManagerImpl;
import ru.innopolis.stc9.pojo.Subject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Класс реализует интерфейс SubjectDao
 */
@Component
public class SubjectDaoImpl implements SubjectDao {
    private static ConnectionManager connectionManager = ConnectionManagerImpl.getInstance();
    private Logger logger = Logger.getLogger(SubjectDaoImpl.class);

    @Override
    public boolean addSubject(Subject subject) {
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement("INSERT " +
                     "INTO subjects(sname) " +
                     "VALUES (?)")) {
            statement.setString(1, subject.getName());
            statement.execute();
            return true;
        } catch (SQLException e) {
            logger.info(e.getMessage());
            return false;
        }
    }

    @Override
    public boolean deleteSubject(int subjectId) {
        if (subjectId < 0) return false;
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "DELETE FROM subjects WHERE id = ?")) {
            statement.setInt(1, subjectId);
            statement.execute();
            logger.info(" delete subject");
            return true;
        } catch (SQLException e) {
            logger.error(e.getMessage());
            return false;
        }
    }

    @Override
    public List<Subject> findAllSubject() {
        logger.info("Subject list requested.");
        List<Subject> result = new ArrayList<>();
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM subjects")) {
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Subject subject = new Subject(
                            resultSet.getInt("id"),
                            resultSet.getString("sname"));
                    result.add(subject);
                }
                logger.info("Subject list returned successfully");
            }
        } catch (SQLException e) {
            logger.info(e.getMessage());
        }
        return result;
    }
}
