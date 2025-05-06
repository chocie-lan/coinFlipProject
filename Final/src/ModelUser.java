import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Objects;

public class ModelUser {
    private Connection connection;

    public ModelUser(){
        try{
            this.connection = DBConnection.getConnection();
            System.out.println("Database Connection Successful");
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


    public ArrayList<String> readUserTable(){
        String cmd = "SELECT * FROM userData;";
        try (Statement statement = connection.createStatement()) {
            ResultSet rs = statement.executeQuery(cmd);
            ArrayList<String> arrayList = new ArrayList<>();
            while (rs.next()){
                String name = rs.getString("username");
                String password = rs.getString("password");
                String s = name + " " +password;
                arrayList.add(s);
            }
            return arrayList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
