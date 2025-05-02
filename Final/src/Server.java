import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class Server {
    private static int userId = 1;
    static PrintWriter printWriter;
    static InputStreamReader inputStreamReader;
    static BufferedReader bufferedReader;

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(5000);
        System.out.println("server started on port: "+ serverSocket.getLocalPort());

        ModelLeaderboard leaderboard = new ModelLeaderboard();
        ModelUser modelUser = new ModelUser();

        //test user
        modelUser.createTable();
        String username = "user3"; //will be passed from controller (which gets it from view)
        //String password = ""; //CHANGE, OBVIOUSLY
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

            inputStreamReader = new InputStreamReader(clientSocket.getInputStream());
            bufferedReader = new BufferedReader(inputStreamReader);
            printWriter = new PrintWriter(clientSocket.getOutputStream(), true);

            //loop for login
            while(true) {
                //String checkUser = bufferedReader.readLine(); //check that printing
                //String checkPass = bufferedReader.readLine();
                String checkPass ="pass";
                String checkUser ="user";

                //System.out.println(checkPass);
                //System.out.println(checkUser);
                ArrayList<String>users;
                users = modelUser.readUserTable();
                //String verifyUser = String.format("%10s %10s",checkUser, checkPass); //change to
                String verifyUser = checkUser + " "+ checkPass;
                boolean userFound = false;

                for(String s : users){
                    System.out.println("s : "+ s+ " verifyUser: "+verifyUser);
                    if((s.equals(verifyUser))){
                        //strings do not match for some reason
                        System.out.println("STRINGS MATCH");
                    }else{
                        System.out.println("NO MATCH");
                    }
                    //if(s.equals(verifyUser)){
                    if (checkUser.equals("user") && checkPass.equals("pass")) {
                        //System.out.println("User found");
                        printWriter.println("User Accepted");
                        //System.out.println("Message sent to client");
                        userFound = true; //do i need this?
                        break;
                        //go to game window
                    } else if (s.contains(checkUser) && !s.contains(checkPass)) {
                        System.out.println("found matching username");
                        //ask them to retry their password(if login was clicked)
                        //or tell them username exists (if sign up was clicked)
                    }
                    else {
                        //printWriter.println("User Not Found");
                        continue;
                    }
                }
                if(!userFound){
                    printWriter.println("User Not Found");
                    System.out.println("Cannot find your account, please sign up");
                }

            }

//            int id = 0;
//            int score;
//            //grab user for leaderboard
//            ArrayList<String> leaderboardPeople;
//            leaderboardPeople = leaderboard.readLeaderboard();
//            for(String s : leaderboardPeople){
//                String[] person = s.trim().split("\\s+");
//                String name = null;
//                try {
//                    id = Integer.parseInt(person[0]);
//                    name = person[1];
//                    score = Integer.parseInt(person[2]);
//                } catch (NumberFormatException e) {
//                    System.out.println(e);
//                }
//                if(name.equals(username)){
//                    break;
//                }
//
//            }

            //loop for gameplay
//            String input;
//            while (true) {
//                input = bufferedReader.readLine();
//
//                if(input != null && !input.equals("flipCoin")){
//                    int bal = Integer.parseInt(input);
//                    System.out.println(bal);
//
//                    //leaderboard.updateLeaderboard(id,bal);
//                }
//
//                if (input != null) {
//                    String out;
//                    double flip = Math.random();
//                    if (flip >= 0.5) {
//                        out = "heads";
//                    } else {
//                        out = "tails";
//                    }
//                    printWriter.println(out);
//                    input = null;
//                }
           // }
        }
    }
}
