package ru.innopolis.stc9.dao.implementation;

import org.apache.log4j.Logger;
import ru.innopolis.stc9.dao.mappers.Mapper;
import ru.innopolis.stc9.db.connection.ConnectionManager;
import ru.innopolis.stc9.db.connection.ConnectionManagerImpl;
import ru.innopolis.stc9.pojo.DBObject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public abstract class DBObjectDao {
    private static ConnectionManager connectionManager = ConnectionManagerImpl.getInstance();
    private Logger logger = Logger.getLogger(DBObjectDao.class);

    public abstract Mapper getMapper();

    public boolean executor(DBObject object, String prefix, List<String> mainParam, String postfix, List<String> secondaryParam, boolean isEqually) {
        if (object == null) return false;
        Mapper mapper = getMapper();
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     mapper.getSqlRequestByParam(prefix, mainParam, postfix, secondaryParam, isEqually))) {
            mapper.statementSetter(statement, object, mainParam, secondaryParam, false);
            statement.execute();
        } catch (SQLException e) {
            logger.error("SQLException: " + e.getMessage());
            return false;
        }
        return true;
    }

    public List<DBObject> getter(DBObject object, String prefix, List<String> mainParam, String postfix, List<String> secondaryParam, boolean isEqually) {
        List<DBObject> result = new ArrayList<>();
        Mapper mapper = getMapper();
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     mapper.getSqlRequestByParam(prefix, mainParam, postfix, secondaryParam, isEqually))) {
            mapper.statementSetter(statement, object, mainParam, secondaryParam, false);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) result.add(mapper.getByResultSet(resultSet));
            }
        } catch (SQLException e) {
            logger.error("SQLException: " + e.getMessage());
        }
        return result;
    }
}
