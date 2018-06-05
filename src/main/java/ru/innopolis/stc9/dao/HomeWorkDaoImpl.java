package ru.innopolis.stc9.dao;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import ru.innopolis.stc9.db.connection.ConnectionManager;
import ru.innopolis.stc9.db.connection.ConnectionManagerImpl;
import ru.innopolis.stc9.pojo.HomeWork;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Сергей on 31.05.2018.
 * Реализация интерфейса HomeWorkDao с помощью jdbc
 */
@Component
public class HomeWorkDaoImpl implements HomeWorkDao {
    private Logger logger = Logger.getLogger(GroupDaoImpl.class);
    private static ConnectionManager connectionManager = ConnectionManagerImpl.getInstance();
    private String clmHwURL = "h_w_url";
    private String clmStudentId = "student_id";
    private String clmLessonId = "lesson_id";

    @Override
    public boolean addHomeWork(HomeWork homeWork) {
        if (homeWork == null) return false;
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "INSERT INTO home_work (h_w_url, student_id, lesson_id) " +
                             "VALUES (?, ?, ?)\n")) {
            statement.setString(1, homeWork.getHomeWorkURL());
            statement.setInt(2, homeWork.getStudentId());
            statement.setInt(3, homeWork.getLessonId());
            statement.execute();
            logger.info("homework added to DB");
            return true;
        } catch (SQLException e) {
            logger.error(e.getMessage());
            return false;
        }
    }

    @Override
    public boolean updateHomeWork(HomeWork homeWork) {
        if (homeWork == null) return false;
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "UPDATE home_work SET h_w_url = ?, student_id = ?, lesson_id = ? WHERE id = ?")) {
            statement.setString(1,homeWork.getHomeWorkURL());
            statement.setInt(2, homeWork.getStudentId());
            statement.setInt(3, homeWork.getLessonId());
            statement.setInt(4, homeWork.getId());
            statement.executeUpdate();
            logger.info("update homework");
            return true;
        } catch (SQLException e) {
            logger.error(e.getMessage());
            return false;
        }
    }

    @Override
    public HomeWork findById(int id) {
        HomeWork homeWork = null;
        if (id < 0) return homeWork;
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM home_work WHERE id = ?")) {
            statement.setInt(1, id);
            try (ResultSet set = statement.executeQuery()) {
                while (set.next()) homeWork = new HomeWork(set.getInt("id"), set.getString(clmHwURL),
                        set.getInt(clmStudentId), set.getInt(clmLessonId));
            }
            logger.info("get homework by id");
            return homeWork;
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
        return homeWork;
    }

    @Override
    public HomeWork findByStudentId(int studentId) {
        HomeWork homeWork = null;
        if (studentId < 0) return homeWork;
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM home_work WHERE student_id = ?")) {
            statement.setInt(1, studentId);
            try (ResultSet set = statement.executeQuery()) {
                while (set.next()) homeWork = new HomeWork(set.getInt("id"), set.getString(clmHwURL),
                        set.getInt(clmStudentId), set.getInt(clmLessonId));
            }
            logger.info("get homework by student_id");
            return homeWork;
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
        return homeWork;
    }

    @Override
    public HomeWork findByLessonId(int lessonId) {
        HomeWork homeWork = null;
        if (lessonId < 0) return homeWork;
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM home_work WHERE lesson_id = ?")) {
            statement.setInt(1, lessonId);
            try (ResultSet set = statement.executeQuery()) {
                while (set.next()) homeWork = new HomeWork(set.getInt("id"), set.getString(clmHwURL),
                        set.getInt(clmStudentId), set.getInt(clmLessonId));
            }
            logger.info("get homework by lesson_id");
            return homeWork;
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
        return homeWork;
    }

    @Override
    public HomeWork findHomeWorkByStudentIdAndLessonId(int studentId, int lessonId) {
        HomeWork homeWork = null;
        if (lessonId < 0) return homeWork;
        logger.info("Started requesting homework by student_id and lesson_id");
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM home_work WHERE student_id = ? AND lesson_id = ?")) {
            statement.setInt(1, studentId);
            statement.setInt(2, lessonId);
            try (ResultSet set = statement.executeQuery()) {
                if (set.next()) {
                    homeWork = new HomeWork(set.getInt("id"),
                            set.getString(clmHwURL),
                            set.getInt(clmStudentId),
                            set.getInt(clmLessonId));
                }
            }
            logger.info("Homework by student id and lesson id successfully returned.");
            return homeWork;
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
        return homeWork;
    }

    @Override
    public List<HomeWork> findAllHomeWork() {
        List<HomeWork> list = new ArrayList<>();
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM home_work");
             ResultSet set = statement.executeQuery()) {
            while (set.next()) {
                HomeWork homeWork = new HomeWork(set.getInt("id"), set.getString(clmHwURL),
                        set.getInt(clmStudentId), set.getInt(clmLessonId));
                list.add(homeWork);
            }
            logger.info("get all homeworks");
            return list;
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
        return list;
    }

    @Override
    public boolean deleteHomeWork(int id) {
        if (id < 0) return false;
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "DELETE  FROM home_work WHERE id =?")) {
            statement.setInt(1, id);
            statement.execute();
            logger.info(" delete homework");
            return true;
        } catch (SQLException e) {
            logger.error(e.getMessage());
            return false;
        }
    }
}
