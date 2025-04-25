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

        modelUser.createTable();
        String username = "user1"; //will be passed from controller (which gets it from view)
        String password = "password123";
        modelUser.searchUser(username, password);
       // modelUser.readUserTable(username, password);

        //modelUser.addUser(username, password);

        //test leaderboard
        leaderboard.createLeaderboardTable();
        leaderboard.createLeaderboard("joe", 0);
        leaderboard.createLeaderboard("bob", 0);
        leaderboard.updateLeaderboard("joe", 10);
        leaderboard.deleteLeaderboard("bob");
        ArrayList<String> arrayList;
        arrayList = leaderboard.readLeaderboard();
        /*
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

            //loop for login
            while(true) {
                String checkUser = bufferedReader.readLine();
                String checkPass = bufferedReader.readLine();

                if(!username.equals(checkUser) && !password.equals(checkPass)) {
                    printWriter.println("failed");
                } else {
                    printWriter.println("success");
                    break;
                }
            }

            //loop for gameplay
            String input;
            while (true) {
                input = bufferedReader.readLine();
                if (input != null) {
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
