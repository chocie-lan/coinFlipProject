import java.io.*;
import java.lang.reflect.Array;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {
    private static int userId = 1;

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(5000);
        System.out.println("server started on port: "+ serverSocket.getLocalPort());

        ModelLeaderboard leaderboard = new ModelLeaderboard();
        ModelUser modelUser = new ModelUser();

        //test user
        modelUser.createTable();
        String username = "user1"; //will be passed from controller (which gets it from view)
        String password = "password123";
        //modelUser.addUser(username, password);

        /*
        //test leaderboard
        leaderboard.createLeaderboardTable();
        leaderboard.createLeaderboard("user1", 0);
        //leaderboard.createLeaderboard("bob", 0);
        leaderboard.updateLeaderboard(1, 10);
        leaderboard.deleteLeaderboard(2);
        ArrayList<String> arrayList;
        arrayList = leaderboard.readLeaderboard();
        for(String s : arrayList){
            System.out.println(s);
        }
*/
        while (true){
            Socket clientSocket = serverSocket.accept();
            System.out.println("Client connected "+ clientSocket.getPort());

            InputStreamReader inputStreamReader = new InputStreamReader(clientSocket.getInputStream());
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            PrintWriter printWriter = new PrintWriter(clientSocket.getOutputStream(), true);

            ArrayList<String> top3;
            top3 = leaderboard.getTopThree();
            /*
            for(String s : top3){
                System.out.println(s);
            }
            */

            //loop for login
            //String username = null;
            //String password = null;
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
                if(input != null && !input.equals("flipCoin")){
                    int bal = Integer.parseInt(input);
                    System.out.println(bal);
                    leaderboard.updateLeaderboard(id,bal);
                    top3 = leaderboard.getTopThree();
                    for(String s : top3){
                        printWriter.println(s);
                    }
                } else if (input != null) {
                    String out;
                    double flip = Math.random();
                    if (flip >= 0.5) {
                        out = "heads";
                    } else {
                        out = "tails";
                    }
                    printWriter.println(out);
                    input = null;
                }
            }
        }
    }
}
