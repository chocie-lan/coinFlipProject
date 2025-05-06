import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Objects;

//look at single responsibility more -> change in github

public class Controller {
    private static int balance = 100;
    private static View view;
    static PrintWriter printWriter;
    static InputStreamReader inputStreamReader;
    static BufferedReader bufferedReader;

    public Controller(){
        System.out.println("hello from controller");
        view = new View();
        view.viewGame.setBalance(balance);
        //view.initializeGUI();
        view.viewGame.initializeGameView();
        view.viewLogin.initializeLogin();

        view.viewLogin.loginButtonListener(new LoginButtonListener());
        view.viewGame.coinFlipButtonListener(new coinFlipButtonListener());
        view.viewLogin.signUpButtonListener(new signUpButtonListener());
        view.viewGame.diceButtonListener(new diceButtonListener());
    }

    public void start() {
        try (Socket socket = new Socket("localhost", 5000)) {
            System.out.println("Connected to: " + socket.getPort());

            inputStreamReader = new InputStreamReader(socket.getInputStream());
            bufferedReader = new BufferedReader(inputStreamReader);
            printWriter = new PrintWriter(socket.getOutputStream(), true);

            //Loop for login
            while (true) {
                System.out.println("HELLO FROM LOGIN LOOP");
                String verify = bufferedReader.readLine();
                if (verify.equals("failed")) {
                    view.viewLogin.statusMessage.setText("Invalid login information, please sign up!");
                }else if (verify.equals("success")) {
                    view.viewLogin.statusMessage.setText("User Accepted!");
                    view.cardLayout.show(view.cardPanel, "Game");
                    System.out.println("END LOGIN LOOP");
                    break;
                }
            }

            //loop for gameplay
            String gameInput;
            while(true){
                gameInput = bufferedReader.readLine();
                System.out.println("GAME INPUT: " + gameInput);
                System.out.println("HELLO FROM CONTROLLER GAME PLAY LOOP"); //ONLY WORKS ON 4th, 7th, 10th... iterations
                if (!gameInput.equals(null) && (gameInput.equals("heads") || gameInput.equals("tails"))) {
                    System.out.println("CALLING CHANGE COIN BALANCE");
                    changeBalanceCoin(gameInput);
                    getLeaderboard();
                }
                if (!gameInput.equals(null) && (gameInput.equals("1") || gameInput.equals("2")
                        || gameInput.equals("3") || gameInput.equals("4") || gameInput.equals("5")
                        || gameInput.equals("6"))) {
                    changeBalanceDice(gameInput);
                    getLeaderboard();
                }
            }
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void changeBalanceCoin(String gameInput) {
        System.out.println("CHANGE COIN BALANCE CALLED");
        int bet = Integer.parseInt(view.viewGame.getBetAmountText());
        String choice = view.viewGame.getSelected();
        if(choice.equals(gameInput)){
            balance += bet*2;
        } else {
            balance -=bet;
        }
        view.viewGame.setCoinState(gameInput);
        view.viewGame.setBalance(balance);
        System.out.println("SENDING BALANCE TO SERVER: "+ balance);
        printWriter.println(balance); //is this just for the database??

    }

    public void changeBalanceDice(String gameInput){
        int bet = Integer.parseInt(view.viewGame.getBetAmountText());
        String choice = view.viewGame.getSelectedDice();
        if(choice.equals(gameInput)){
            balance += bet*6;
        } else {
            balance -=bet;
        }
        view.viewGame.setCoinState(gameInput);
        view.viewGame.setBalance(balance);
        printWriter.println(balance);
    }
    public void getLeaderboard(){
        ArrayList<String> top3 = new ArrayList<>();
        for (int j = 0; j < 3; j++) {
            try {
                String add = bufferedReader.readLine();
                //System.out.println(add);
                top3.add(add);
            } catch (IOException e) {
                System.out.println(e);
            }
        }
        view.viewGame.updateLeaderboardList(top3);
    }

    private class LoginButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e){
            if(!view.viewLogin.getUsernameText().isEmpty() && !view.viewLogin.getPasswordText().isEmpty()){
                System.out.println("LOGIN BUTTON CLICKED");
                printWriter.println("login");
                printWriter.println(view.viewLogin.getUsernameText());
                printWriter.println(view.viewLogin.getPasswordText());
            }else{
                System.out.println("Please enter a username and password!");
            }
        }
    }

    private static class signUpButtonListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e){
            if(!view.viewLogin.getUsernameText().isEmpty() && !view.viewLogin.getPasswordText().isEmpty()){
                System.out.println("SIGN UP BUTTON CLICKED");
                printWriter.println("signup");
                printWriter.println(view.viewLogin.getUsernameText());
                printWriter.println(view.viewLogin.getPasswordText());
            }else{
                System.out.println("Please enter a username and password!");
            }
        }
    }

    private static class coinFlipButtonListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e){
            if(!view.viewGame.getBetAmountText().isEmpty()) {
                int intValue = 0;
                try {
                    intValue = Integer.parseInt(view.viewGame.getBetAmountText());
                } catch (NumberFormatException ex) {
                    System.out.println("Please enter a number");
                    // do not allow them to flip the coin
                }
//                if (intValue > balance) { //check if is dollar amount
//                    System.out.println("You don't have enough money for that!");
//                }
                if(intValue < 0){
                    System.out.println("Enter a positive value");
                }
                else {
                    System.out.println("COIN FLIP BUTTON CLICKED");
                    System.out.println("SINGING COMMAND TO SERVER: flipCoin");
                    printWriter.println("flipCoin");
                }
            }
            else{
                System.out.println("Please enter a bet amount");
            }
        }
    }

    private static class diceButtonListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            if(!view.viewGame.getBetAmountText().isEmpty()) {
                int intValue = 0;
                System.out.println("DICE BUTTON CLICKED");
                try {
                    intValue = Integer.parseInt(view.viewGame.getBetAmountText());
                } catch (NumberFormatException ex) {
                    //throw new RuntimeException(ex);
                    System.out.println("Please enter a number");
                    // do not allow them to flip the coin
                }
//                if (intValue > balance) { //check if is dollar amount
//                    System.out.println("You don't have enough money for that!");
//                }
                if(intValue < 0){
                    System.out.println("Enter a positive value");
                }
                //check if it is a positive integer
                else {
                    // System.out.println("DICE BUTTON CLICKED");
                    System.out.println("SINGING COMMAND TO SERVER: rollDice");
                    printWriter.println("rollDice");
                }
            }
            else{
                System.out.println("Please enter a bet amount");
            }
        }
    }

}
