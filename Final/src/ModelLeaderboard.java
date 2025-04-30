import java.sql.*;
import java.util.ArrayList;

public class ModelLeaderboard {
    private Connection connection;

    public ModelLeaderboard(){
        try {
            this.connection = DBConnection.getConnection();
            System.out.println("Connection Successful");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void createLeaderboardTable(){
        String cmd = "CREATE TABLE IF NOT EXISTS leaderboard(" +
                "id INTEGER PRIMARY KEY," +
                "name TEXT NOT NULL," +
                "score INTEGER NOT NULL);";
        try (Statement statement = connection.createStatement()) {
            statement.execute(cmd);
            System.out.println("Table created or already exists");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateLeaderboard(int id, int score){
        String cmd = "UPDATE leaderboard SET score = ? WHERE id = ?;";

        try(PreparedStatement preparedStatement = connection.prepareStatement(cmd)) {
            preparedStatement.setInt(1, score);
            preparedStatement.setInt(2, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<String> readLeaderboard(){
        String cmd = "SELECT * FROM leaderboard;";

        try (Statement statement = connection.createStatement()) {
            ResultSet rs = statement.executeQuery(cmd);
            ArrayList<String> arrayList = new ArrayList<>();
            while (rs.next()){
                int id = rs.getInt("id");
                String name = rs.getString("name");
                int score = rs.getInt("score");
                String s = String.format("%3d %10s %3d", id, name, score);
                arrayList.add(s);
            }
            return arrayList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<String> getTopThree(){
        String cmd = "SELECT * FROM leaderboard ORDER BY score DESC LIMIT 3;";

        try (Statement statement = connection.createStatement()) {
            ResultSet rs = statement.executeQuery(cmd);
            ArrayList<String> arrayList = new ArrayList<>();
            while (rs.next()){
                int id = rs.getInt("id");
                String name = rs.getString("name");
                int score = rs.getInt("score");
                String s = String.format("%3d %10s %3d", id, name, score);
                arrayList.add(s);
            }
            return arrayList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteLeaderboard(int id){
        String cmd = "DELETE FROM leaderboard WHERE id = ?";

        try(PreparedStatement preparedStatement = connection.prepareStatement(cmd)) {
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void createLeaderboard(String name, int score){
        String cmd = "INSERT INTO leaderboard (name, score) VALUES (? , ?);";

        try(PreparedStatement preparedStatement = connection.prepareStatement(cmd)) {
            preparedStatement.setString(1, name);
            preparedStatement.setInt(2, score);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

