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
                    String value;
                    while (true) {
                        //something is wrong with the way i am sending it
                        input = bufferedReader.readLine(); //got rid of the value, just to see, but its moe broken somehow???

                        //System.out.println("CONTROLLER GAMEPLAY INPUT: "+ input); //it is also getting "flip coin", is getting it twice?
                        if(input.equals("flipCoin")){
                            System.out.println("INPUT:"+ input);
                            String out = gamelogic.runGame(input);
                            printWriter.println(out);
                        }
                        //also is input supposed to be the current balance??

                        //when it works:
//                            CONTROLLER GAMEPLAY INPUT: flipCoin -> flip coin command
//                            HELLO FROM runGame in GameLogic
//                            CONTROLLER GAMEPLAY INPUT: 146 -> then current balance -> ik u have other questions, but works for now
//                            HELLO FROM runGame in GameLogic //interesting that it is calling run twice...
                        //when it fails:
//                            CONTROLLER GAMEPLAY INPUT: flipCoin -> sends ONLY flip coin, not the balance... weird...
//                            HELLO FROM runGame in GameLogic


                    }
                }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}