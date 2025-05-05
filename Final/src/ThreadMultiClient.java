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
    String username = "user1";

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
                System.out.println("HELLO FROM THREAD");
                //loop for login
                while (true) {
                    String whichButton = bufferedReader.readLine();
                    String checkUser = bufferedReader.readLine();
                    String checkPass = bufferedReader.readLine();

                    System.out.println("PASSWORD FROM VIEW CLIENT: " + checkPass);
                    System.out.println("USERNAME FROM VIEW CLIENT: " + checkUser);
                    ArrayList<String> users;
                    users = modelUser.readUserTable();
                    if (whichButton.equals("signup")) {
                        String signupReturn = userAuthentication.signup(checkUser, checkPass, users);
                        if (signupReturn.equals("addNewUser")) {
                            System.out.println("SIGN UP METHOD RETURNED");
                            modelUser.addUser(checkUser, checkPass);
                            printWriter.println("success");
                            break;
                        }
                    } else if (whichButton.equals("login")) {
                        String loginReturn = userAuthentication.login(checkUser, checkPass, users);
                        if (loginReturn.equals("validUser")) {
                            printWriter.println("success");
                            break;
                        } else {
                            printWriter.println("failed");
                        }
                    }

                    int id = 0;
                    int score;

                    //grab user for leaderboard
                    ArrayList<String> leaderboardPeople;
                    leaderboardPeople = leaderboard.readLeaderboard();
                    for (String s : leaderboardPeople) {
                        String[] person = s.trim().split("\\s+");
                        String name = null;
                        try {
                            id = Integer.parseInt(person[0]);
                            name = person[1];
                            score = Integer.parseInt(person[2]);
                        } catch (NumberFormatException e) {
                            System.out.println(e);
                        }
                        if (name.equals(username)) {
                            break;
                        }

                    }
                    //loop for gameplay
                    String input;
                    while (true) {
                        input = bufferedReader.readLine();
                        String out = gamelogic.runGame(input);
                        printWriter.println(out);
                    }
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}