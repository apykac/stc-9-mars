package ru.innopolis.stc9.dao;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import ru.innopolis.stc9.db.connection.ConnectionManager;
import ru.innopolis.stc9.db.connection.ConnectionManagerImpl;
import ru.innopolis.stc9.pojo.Education;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Сергей on 01.06.2018.
 */
@Component
public class EducationDaoImpl implements EducationDao {
    private Logger logger = Logger.getLogger(GroupDaoImpl.class);
    private static ConnectionManager connectionManager = ConnectionManagerImpl.getInstance();

    @Override
    public List<Education> findAllEducations() {
        List<Education> list = new ArrayList<>();
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM education");
             ResultSet set = statement.executeQuery()) {
            while (set.next()) {
                Education education = new Education(set.getInt("id"),
                        set.getInt("stgroup_id"), set.getInt("subject_id"));
                list.add(education);
            }
            logger.info("get all educations");
            return list;
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
        return list;
    }
}
