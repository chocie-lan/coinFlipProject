import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

import static java.lang.Thread.sleep;

public class ThreadMultiClient implements Runnable {
    ModelLeaderboard leaderboard = new ModelLeaderboard();
    ModelUser modelUser = new ModelUser();
    UserAuthentication userAuthentication = new UserAuthentication();
    GameLogic gamelogic = new GameLogic();
    PrintWriter printWriter = null;
    BufferedReader bufferedReader;
    InputStreamReader inputStreamReader;
    //private int threadNumber;
    private Socket clientSocket;
    String username = null;

    public ThreadMultiClient(Socket clientSocket) {
        this.clientSocket = clientSocket;
        leaderboard.createLeaderboardTable();
        modelUser.createTable();

        try {
            inputStreamReader = new InputStreamReader(clientSocket.getInputStream());
            bufferedReader = new BufferedReader(inputStreamReader);
            printWriter = new PrintWriter(clientSocket.getOutputStream(), true);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void handleLogin() throws IOException {
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
                    leaderboard.createLeaderboard(checkUser, 100);
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
    }

    public void initializeLeaderboard(){
    //need to get this to be a separate function
    }

    public boolean isLeaderboard(String input){
        if(input != null && !input.equals("flipCoin") && !input.equals("rollDice")){
            return true;
        }
        return false;
    }

    public void handleGameplay() throws IOException {
        initializeLeaderboard();
        int id = 0;
        ArrayList<String> top3;
        ArrayList<String> leaderboardPeople;

        leaderboardPeople = leaderboard.readLeaderboard();
        for(String s : leaderboardPeople){
            String[] person = s.trim().split("\\s+");
            String name = null;
            try {
                id = Integer.parseInt(person[0]);
                name = person[1];
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
            if (isLeaderboard(input)) {
                int bal = Integer.parseInt(input);
                leaderboard.updateLeaderboard(id,bal);
                top3 = leaderboard.getTopThree();
                for(String s: top3){
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

    @Override
    public void run() {
        try {
            while (clientSocket.isConnected()) {
                handleLogin();
                handleGameplay();
                }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}