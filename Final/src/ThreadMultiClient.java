import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

public class ThreadMultiClient implements Runnable{

    ModelLeaderboard leaderboard = new ModelLeaderboard();
    ModelUser modelUser = new ModelUser();
    PrintWriter printWriter = null;
    BufferedReader bufferedReader;
    private int threadNumber;
    private Socket clientSocket;
    String username = "user1";
    public ThreadMultiClient(int threadNumber, Socket clientSocket){
        this.threadNumber = threadNumber;
        this.clientSocket = clientSocket;

        try{
            InputStreamReader inputStreamReader = new InputStreamReader(clientSocket.getInputStream());
            bufferedReader = new BufferedReader(inputStreamReader);
            printWriter = new PrintWriter(clientSocket.getOutputStream(), true);
        }catch (IOException e){
            throw new RuntimeException(e);
        }
    }
    @Override
    public void run(){
        this.threadNumber = threadNumber;
        try{
            while(clientSocket.isConnected()){
                System.out.println("HELLO FROM THREAD");
//                inputStreamReader = new InputStreamReader(clientSocket.getInputStream());
//                bufferedReader = new BufferedReader(inputStreamReader);
//                printWriter = new PrintWriter(clientSocket.getOutputStream(), true);

                //loop for login
                while(true) {
                    String whichButton = bufferedReader.readLine();
                    String checkUser = bufferedReader.readLine();
                    String checkPass = bufferedReader.readLine();

                    System.out.println("PASSWORD FROM VIEW CLIENT: " + checkPass);
                    System.out.println("USERNAME FROM VIEW CLIENT: "+ checkUser);
                    ArrayList<String> users;
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
                            break;
                            //add user to database
                        }

                    }else{
                        break;
                    }

                }

                int id = 0;
                int score;
                ArrayList<String> top3;
                top3 = leaderboard.getTopThree();
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
            } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }
}
