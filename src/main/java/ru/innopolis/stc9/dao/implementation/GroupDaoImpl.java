package ru.innopolis.stc9.dao.implementation;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import ru.innopolis.stc9.dao.interfaces.GroupDao;
import ru.innopolis.stc9.db.connection.ConnectionManager;
import ru.innopolis.stc9.db.connection.ConnectionManagerImpl;
import ru.innopolis.stc9.pojo.Group;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Сергей on 23.05.2018.
 * Класс реализует интерфейс GroupDao
 */
@Component
public class GroupDaoImpl implements GroupDao {
    private Logger logger = Logger.getLogger(GroupDaoImpl.class);
    private static ConnectionManager connectionManager = ConnectionManagerImpl.getInstance();
    private String errMessage = "SQLException. ";

    @Override
    public boolean addGroup(Group group) {
        if (group == null) return false;
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "INSERT INTO stgroup (gname) " +
                             "VALUES (?)\n")) {
            statement.setString(1, group.getName());
            statement.execute();
            logger.info("group added to DB");
            return true;
        } catch (SQLException e) {
            logger.error(errMessage + " " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean updateGroup(Group group) {
        if (group == null) return false;
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "UPDATE stgroup SET gname = ? WHERE id = ?")) {
            statement.setString(1, group.getName());
            statement.setInt(2, group.getId());
            statement.executeUpdate();
            logger.info("update group");
            return true;
        } catch (SQLException e) {
            logger.error(errMessage + " " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean deleteGroup(int groupId) {
        if (groupId < 0) return false;
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "DELETE  FROM stgroup WHERE id =?")) {
            statement.setInt(1, groupId);
            statement.execute();
            logger.info(" delete group");
            return true;
        } catch (SQLException e) {
            logger.error(errMessage + e.getMessage());
            return false;
        }
    }

    @Override
    public Group findGroupById(int id) {
        if (id < 0) return null;
        Group group = null;
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM stgroup WHERE id = ?")) {
            statement.setInt(1, id);
            try (ResultSet set = statement.executeQuery()) {
                while (set.next()) group = new Group(set.getInt("id"), set.getString("gname"));
            }
            logger.info("get group by id");
            return group;
        } catch (SQLException e) {
            logger.error(errMessage + e.getMessage());
        }
        return group;
    }

    @Override
    public Group findGroupByName(String name) {
        if ((name == null) || name.isEmpty()) return null;
        Group group = null;
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM stgroup WHERE gname = ?")) {
            statement.setString(1, name);
            try (ResultSet set = statement.executeQuery()) {
                while (set.next()) {
                    group = new Group(set.getString("gname"));
                    logger.info("get group by name");
                }
            }
            return group;
        } catch (SQLException e) {
            logger.error(errMessage + e.getMessage());
        }
        return group;
    }

    @Override
    public List<Group> findAllGroups() {
        List<Group> list = new ArrayList<>();
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM stgroup");
             ResultSet set = statement.executeQuery()) {
            while (set.next()) {
                Group group = new Group(set.getInt("id"), set.getString("gname"));
                list.add(group);
            }
            logger.info("get all groups");
            return list;
        } catch (SQLException e) {
            logger.error(errMessage + e.getMessage());
        }
        return list;
    }
}
