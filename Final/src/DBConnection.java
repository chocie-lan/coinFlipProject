import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private final static String URI = "jdbc:sqlite:database.db";
    private static Connection connection;

    public static Connection getConnection() throws SQLException{
        connection = DriverManager.getConnection(URI);
        return connection;
    }
    public static void closeConnection() throws SQLException{
        connection.close();

    }

}
