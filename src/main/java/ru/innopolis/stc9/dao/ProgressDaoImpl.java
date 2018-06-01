package ru.innopolis.stc9.dao;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import ru.innopolis.stc9.db.connection.ConnectionManager;
import ru.innopolis.stc9.db.connection.ConnectionManagerImpl;
import ru.innopolis.stc9.pojo.Progress;
import ru.innopolis.stc9.pojo.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Класс реализует интерфейс ProgressDao
 */
@Component
public class ProgressDaoImpl implements ProgressDao {
    private static ConnectionManager connectionManager = ConnectionManagerImpl.getInstance();
    private Logger logger = Logger.getLogger(ProgressDaoImpl.class);

    /**
     * Если пользователь - студент, то добавляем условие, чтобы каждый студент мог видеть только свои данные
     * если не студент, то выводим весь список
     */
    private String getProgressQuery(String role) {
        String sql = "SELECT marks.id, value, u2.first_name firstName, u2.second_name secondName, " +
                "l3.name lessonsName, l3.date date, s4.sname subjectName, g5.gname groupName " +
                "FROM marks " +
                "INNER JOIN users u2 ON marks.user_id = u2.id " +
                "INNER JOIN lessons l3 ON marks.lesson_id = l3.id " +
                "INNER JOIN subjects s4 ON l3.subject_id = s4.id " +
                "INNER JOIN stgroup g5 ON u2.group_id = g5.id " +
                "WHERE value >= ? AND value <= ? ";
        if (isStudent(role)) {
            sql += " AND u2.login LIKE ?";
        }
        return sql;
    }

    /**
     * Проверяем является ли пользователь студентом
     *
     * @param role роль пользователя
     */
    private boolean isStudent(String role) {
        return role.equals("ROLE_STUDENT");
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
        return progress;
    }

    /**
     * Получаем список оценок
     *
     * @param greaterOrEqualMark больше или равно оценки
     * @param lessOrEqualMark    меньше или равно оценки
     * @param user               сам пользователь
     */
    @Override
    public List<Progress> getProgress(int greaterOrEqualMark, int lessOrEqualMark, User user) {
        if (user == null) {
            return null;
        }
        String role = user.getPermissionGroup();
        String login = user.getLogin();
        logger.info("Progress list requested");
        List<Progress> result = new ArrayList<>();
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(getProgressQuery(role))) {
            statement.setInt(1, greaterOrEqualMark);
            statement.setInt(2, lessOrEqualMark);
            if (isStudent(role)) {
                statement.setString(3, login);
            }
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
    }
}
