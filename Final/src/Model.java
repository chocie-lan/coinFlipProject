import java.sql.*;

public class Model {
    private String username;
    private String password;
    private Connection connection;

    public Model(){
        try{
            this.connection = DBConnection.getConnection();
            System.out.println("Database Connection Successful");
            //createTable();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void createTable(){
        //ADD A TABLE
        String cmd = "CREATE TABLE IF NOT EXISTS userData(userID INTEGER PRIMARY KEY,"+
                "username TEXT NOT NULL,"+
                "password TEXT NOT NULL);";
        try(Statement statement = connection.createStatement()){
            statement.execute(cmd);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

}
