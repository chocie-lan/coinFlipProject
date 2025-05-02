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
                String whichButton = bufferedReader.readLine();
                String checkUser = bufferedReader.readLine();
                String checkPass = bufferedReader.readLine();

                System.out.println("PASSWORD FROM VIEW CLIENT: " + checkPass);
                System.out.println("USERNAME FROM VIEW CLIENT: "+ checkUser);
                ArrayList<String>users;
                users = modelUser.readUserTable();
                boolean userFound = false;

                for(String s : users){
                    String myUsername = "";
                    String myPassword = "";
                    String[] existingUsers = s.trim().split("\\s+");
                    try{
                        myUsername = existingUsers[0];
                        myPassword = existingUsers[1];
                    }catch(NumberFormatException e){
                        System.out.println("error");
                    }
                    if(myUsername.equals(checkUser) && myPassword.equals(checkPass)){
                        if(whichButton.equals("login")){
                            userFound = true;
                            printWriter.println("success");
                            break;
                        } else if (whichButton.equals("signup")) {
                            System.out.println("A user with that name already exists");
                        }

//                    }else if(myUsername.equals(checkUser) && !myPassword.equals(checkPass)){
//                        userFound = true;
//                        System.out.println("PARTIAL MATCH DETECTED");
//                        printWriter.println("partial match");
                        //this breaks it for some reason...
                    }else{
                        System.out.println("NO MATCH");
                    }
                }
                if(!userFound){
                    if(whichButton.equals("login")){
                        printWriter.println("failed");
                    } else if(whichButton.equals("signup")){
                        printWriter.println("success");
                        modelUser.addUser(checkUser, checkPass);
                        //add user to database
                    }

                }else{
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

                    //leaderboard.updateLeaderboard(id,bal);
                }

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
