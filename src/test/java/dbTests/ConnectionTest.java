package dbTests;

import com.mysql.cj.jdbc.ConnectionImpl;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.innopolis.stc9.db.connection.ConnectionManager;
import ru.innopolis.stc9.db.connection.ConnectionManagerImpl;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import static org.junit.Assert.*;

public class ConnectionTest {
    ConnectionManager connectionManager = ConnectionManagerImpl.getInstance();
    Connection connection;

    @Before
    public void before() {
        connection = connectionManager.getConnection();
    }

    @Test
    public void testConnection() {
        try (Statement statement = connection.createStatement()) {
            assertTrue(statement.execute("SHOW TABLES"));
            statement.execute("SHOW TABLES");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @After
    public void after() {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
