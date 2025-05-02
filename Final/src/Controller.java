import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

public class Controller {
    private static int balance = 100;
    private static View view;
    static PrintWriter printWriter;
    static InputStreamReader inputStreamReader;
    static BufferedReader bufferedReader;

    public Controller(){
        System.out.println("hello from controller");
        view = new View();
        view.setBalance(balance);
        view.initializeGUI();

        view.loginButtonListener(new LoginButtonListener());
        view.coinFlipButtonListener(new coinFlipButtonListener());
        view.signUpButtonListener(new signUpButtonListener());
        view.diceButtonListener(new diceButtonListener());
    }

    public void start() {
        try (Socket socket = new Socket("localhost", 5000)) {
            System.out.println("Connected to: " + socket.getPort());

            inputStreamReader = new InputStreamReader(socket.getInputStream());
            bufferedReader = new BufferedReader(inputStreamReader);
            printWriter = new PrintWriter(socket.getOutputStream(), true);

            //Loop for login
            while(true) {
                String verify = bufferedReader.readLine();
                if (verify.equals("failed")) {
                    System.out.println("Incorrect username and password!");

                } else if (verify.equals("success")){
                    break;
                }
            }

            //loop for gameplay
            String gameInput;
            while(true){
                gameInput = bufferedReader.readLine();
                //System.out.println(gameInput);
                if(!gameInput.equals(null) && (gameInput.equals("heads") || gameInput.equals("tails"))) {
                    changeBalanceCoin(gameInput);
                    getLeaderboard();
                }
                if (!gameInput.equals(null) && (gameInput.equals("1") || gameInput.equals("2")
                        || gameInput.equals("3") || gameInput.equals("4") || gameInput.equals("5")
                        || gameInput.equals("6")  )) {
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
        int bet = Integer.parseInt(view.getBetAmountText());
        String choice = view.getSelected();
        if(choice.equals(gameInput)){
            balance += bet*2;
        } else {
            balance -=bet;
        }
        view.setCoinState(gameInput);
        view.setBalance(balance);
        printWriter.println(balance);

    }

    public void changeBalanceDice(String gameInput){
        int bet = Integer.parseInt(view.getBetAmountText());
        String choice = view.getSelectedDice();
        if(choice.equals(gameInput)){
            balance += bet*6;
        } else {
            balance -=bet;
        }
        view.setCoinState(gameInput);
        view.setBalance(balance);
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
        view.updateLeaderboardList(top3);
    }

    private class LoginButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e){
            if(!view.getUsernameText().isEmpty() && !view.getPasswordText().isEmpty()){
                //System.out.println("LOGIN BUTTON CLICKED");
                printWriter.println(view.getUsernameText());
                printWriter.println(view.getPasswordText());
                //check for record in database
                //pass to server -> model -> DB
                //if found, go to game tab
                //else, ask them to sign up instead
            }else{
                System.out.println("Please enter a username and password!");
            }
        }
    }

    private static class signUpButtonListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e){
            if(!view.getUsernameText().isEmpty() && !view.getPasswordText().isEmpty()){
                System.out.println("SIGN UP BUTTON CLICKED");
            }else{
                System.out.println("Please enter a username and password!");
            }
        }
    }

    private static class coinFlipButtonListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e){
            if(!view.getBetAmountText().isEmpty()) {
                int intValue = 0;
                try {
                    intValue = Integer.parseInt(view.getBetAmountText());
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
                    System.out.println("COIN FLIP BUTTON CLICKED");
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
            if(!view.getBetAmountText().isEmpty()) {
                int intValue = 0;
                try {
                    intValue = Integer.parseInt(view.getBetAmountText());
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
                    printWriter.println("rollDice");
                }
            }
            else{
                System.out.println("Please enter a bet amount");
            }
        }
    }


}
