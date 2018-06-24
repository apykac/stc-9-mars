package ru.innopolis.stc9.db.connection;

import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionManagerImpl implements ConnectionManager {

    private static ConnectionManager connectionManager;
    private static final Logger logger = Logger.getLogger("defaultLog");

    public static ConnectionManager getInstance(){
        if (connectionManager == null){
            connectionManager = new ConnectionManagerImpl();
        }
        return connectionManager;
    }

    private ConnectionManagerImpl(){

    }

    /**
     * "jdbc:mysql://sql7.freesqldatabase.com:3306/sql7239469"
     * "sql7239469"
     */
    @Override
    public Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/test?" +
                            "serverTimezone=UTC" +
                            "&useUnicode=yes" +
                            "&characterEncoding=UTF-8",
                    "test",
                    "test");
        } catch (ClassNotFoundException | SQLException e) {
            logger.error(e.getMessage());
        }
        return connection;
    }
}
