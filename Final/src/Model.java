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
        String create = "CREATE TABLE IF NOT EXISTS userData(userID INTEGER PRIMARY KEY,"+
                "username TEXT NOT NULL,"+
                "password TEXT NOT NULL);";
        try(Statement statement = connection.createStatement()){
            statement.execute(create);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    //ADD USER
    public void addUser(String newName, String newPassword){
        String newUser = String.format("INSERT INTO userData(username, password) VALUES('%s','%s');", newName, newPassword);
        try(Statement statement= connection.createStatement()){
            statement.execute(newUser);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    //SEARCH FOR USER
    public void searchUser(){

    }

}
