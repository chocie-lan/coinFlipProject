import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

public class ThreadMultiClient implements Runnable {
    ModelLeaderboard leaderboard = new ModelLeaderboard();
    ModelUser modelUser = new ModelUser();
    UserAuthentication userAuthentication = new UserAuthentication();
    GameLogic gamelogic = new GameLogic();
    PrintWriter printWriter = null;
    BufferedReader bufferedReader;
    private int threadNumber;
    private Socket clientSocket;
    String username = null;

    public ThreadMultiClient(int threadNumber, Socket clientSocket) {
        System.out.println("Hello from threadMultiClient constructor");
        this.threadNumber = threadNumber;
        this.clientSocket = clientSocket;

        try {
            InputStreamReader inputStreamReader = new InputStreamReader(clientSocket.getInputStream());
            bufferedReader = new BufferedReader(inputStreamReader);
            printWriter = new PrintWriter(clientSocket.getOutputStream(), true);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void run() {
        this.threadNumber = threadNumber;
        try {
            while (clientSocket.isConnected()) {
                //loop for login
                while (true) {
                    String whichButton = bufferedReader.readLine();
                    String checkUser = bufferedReader.readLine();
                    String checkPass = bufferedReader.readLine();
                    ArrayList<String> users;
                    users = modelUser.readUserTable();
                    if (whichButton.equals("signup")) {
                        String signupReturn = userAuthentication.signup(checkUser, checkPass, users);
                        if (signupReturn.equals("addNewUser")) {
                            modelUser.addUser(checkUser, checkPass);
                            printWriter.println("success");
                            username = checkUser;
                            break;
                        }
                    } else if (whichButton.equals("login")) {
                        String loginReturn = userAuthentication.login(checkUser, checkPass, users);
                        if (loginReturn.equals("validUser")) {
                            printWriter.println("success");
                            username = checkUser;
                            break;
                        } else {
                            printWriter.println("failed");
                        }
                    }
                }
                
                //initialize leaderboard
                int id = 0;
                int score;
                ArrayList<String> top3;
                ArrayList<String> leaderboardPeople;
                
                top3 = leaderboard.getTopThree();
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
                String value;
                while (true) {
                    input = bufferedReader.readLine();
                    if (input != null && !input.equals("flipCoin") && !input.equals("rollDice")) {
                        int bal = Integer.parseInt(input);
                        leaderboard.updateLeaderboard(id,bal);
                        top3 = leaderboard.getTopThree();
                        for(String s : top3){
                            printWriter.println(s);
                        }
                    } else {
                        System.out.println("INPUT:" + input);
                        String out = gamelogic.runGame(input);
                        System.out.println("returning: " + out);
                        printWriter.println(out);
                    }
                }
            } 
        }catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }
}