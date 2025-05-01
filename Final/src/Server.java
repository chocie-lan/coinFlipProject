import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(5000);
        System.out.println("server started on port: "+ serverSocket.getLocalPort());

        ModelLeaderboard leaderboard = new ModelLeaderboard();
        ModelUser modelUser = new ModelUser();

        //test user
        modelUser.createTable();
        leaderboard.createLeaderboardTable();
        String username = "user1"; //will be passed from controller (which gets it from view)
        String password = "password123";
        //modelUser.addUser(username, password);

        while (true){
            Socket clientSocket = serverSocket.accept();
            System.out.println("Client connected "+ clientSocket.getPort());

            InputStreamReader inputStreamReader = new InputStreamReader(clientSocket.getInputStream());
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            PrintWriter printWriter = new PrintWriter(clientSocket.getOutputStream(), true);

            ArrayList<String> top3;
            top3 = leaderboard.getTopThree();

            while(true) {
                String checkUser = bufferedReader.readLine();
                String checkPass = bufferedReader.readLine();
                System.out.println(checkUser);
                System.out.println(checkPass);

                if(!username.equals(checkUser) && !password.equals(checkPass)) {
                    System.out.println("failed");
                    printWriter.println("failed");

                }else {
                    System.out.println("success");
                    printWriter.println("success");
                    break;
                }
            }

            int id = 0;
            int score;
            //grab user for leaderboard
            ArrayList<String> leaderboardPeople;
            leaderboardPeople = leaderboard.readLeaderboard();
            for(String s : leaderboardPeople){
                String[] person = s.trim().split("\\s+");
                String name = null;
                try {
                    id = Integer.parseInt(person[0]);
                    name = person[1];
                    score = Integer.parseInt(person[2]);
                } catch (NumberFormatException e) {
                    System.out.println(e);
                }
                if(name.equals(username)){
                    break;
                }

            }

            //loop for gameplay
            String input;
            while (true) {
                input = bufferedReader.readLine();
                if(input != null && !input.equals("flipCoin") && !input.equals("rollDice")){
                    int bal = Integer.parseInt(input);
                    //System.out.println(bal);
                    leaderboard.updateLeaderboard(id,bal);
                    top3 = leaderboard.getTopThree();
                    for(String s : top3){
                        printWriter.println(s);
                    }
                } else if (input != null && input.equals("flipCoin")) {
                    String out;
                    double flip = Math.random();
                    if (flip >= 0.5) {
                        out = "heads";
                    } else {
                        out = "tails";
                    }
                    printWriter.println(out);
                    input = null;
                } else if (input != null && input.equals("rollDice")){
                    String out;
                    int roll = (int)(Math.random() * 6) + 1;
                    //System.out.println(roll);
                    if(roll == 1){
                        out = "1";
                    } else if (roll == 2) {
                        out = "2";
                    } else if (roll == 3) {
                        out = "3";
                    } else if (roll == 4) {
                        out = "4";
                    } else if (roll == 5) {
                        out = "5";
                    } else {
                        out = "6";
                    }
                    printWriter.println(out);
                    input = null;
                }
            }
        }
    }
}
