package utilities;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Represents a database connection class that provides methods to connect to a PostgreSQL database.
 */
public class DatabaseConnection {

    private static String url = "jdbc:postgresql://localhost/test";
    private static Properties props = new Properties();

    public static Connection getConnection() {
        try {
            props.setProperty("user","foo");
            props.setProperty("password","secret");
            props.setProperty("ssl","true");
            return DriverManager.getConnection(url, props);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
