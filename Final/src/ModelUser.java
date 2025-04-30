import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Objects;

public class ModelUser {
    private String username = "Bob";
    private String password = "Password";
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
            //reference modelLeaderboard
            ArrayList<String> arrayList = new ArrayList<>();
            while (rs.next()){
                //int id = rs.getInt("id");
                String name = rs.getString("username");
                String password = rs.getString("password");
                String s = String.format("%10s %10s",name, password);
                arrayList.add(s);
            }
            return arrayList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void searchUser(String name, String password){
        boolean userFound = false;
        String checkString = String.format("%10s %10s",name, password);
        ArrayList<String> userArray = readUserTable();//call readTable -> return the array list
        for(String s : userArray){
            if(s.equals(checkString)){
                System.out.println("Logging you in");
                userFound = true;
                break;
            }
        }
        if(!userFound){
            System.out.println("Unable to find your account; please create an account or try again ");
        }
    }

}
