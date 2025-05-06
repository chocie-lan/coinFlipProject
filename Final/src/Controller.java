import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

public class Controller {
    private static int balance = 100;
    private static View view;

    static PrintWriter printWriter;
    static InputStreamReader inputStreamReader;
    static BufferedReader bufferedReader;


    public Controller(){
        view = new View();
        view.viewGame.setBalance(balance);
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

            handleLogin();
            handleGameplay();
        } catch (IOException e) {
            view.viewLogin.statusMessage.setText("ERROR: Make sure server is running before launching main");
            System.out.println(e);
        }
    }

    public void handleLogin() throws IOException {
        while (true) {
            String verify = bufferedReader.readLine();
            if (verify.equals("failed")) {
                view.viewLogin.statusMessage.setText("Invalid login information, please sign up or double-check password");
            }else if (verify.equals("success")) {
                view.viewLogin.statusMessage.setText("User Accepted!");
                view.cardLayout.show(view.cardPanel, "Game");
                break;
            }
        }
    }


    public boolean isCoinFlip(String gameInput){
        if(gameInput != null && (gameInput.equals("heads") || gameInput.equals("tails"))){
            return true;
        }
        return false;
    }

    public boolean isDiceRoll(String gameInput){
        if(gameInput != null && (gameInput.equals("1") || gameInput.equals("2")
                || gameInput.equals("3") || gameInput.equals("4") || gameInput.equals("5")
                || gameInput.equals("6"))){
            return true;
        }
        return false;
    }

    public void handleGameplay() throws IOException {
        System.out.println("HELLO FROM HANDLE GAMEPLAY");
        String gameInput;
        while(true){
            gameInput = bufferedReader.readLine();
            if (isCoinFlip(gameInput)) {
                changeBalanceCoin(gameInput);
                getLeaderboard();
            }
            if (isDiceRoll(gameInput)) {
                changeBalanceDice(gameInput);
                getLeaderboard();
            }
        }
    }

    public void changeBalanceCoin(String gameInput) {
        int bet = Integer.parseInt(view.viewGame.getBetAmountText());
        String choice = view.viewGame.getSelected();
        if(choice.equals(gameInput)){
            balance += bet;
        } else {
            balance -= bet;
        }
        view.viewGame.setCoinState(gameInput);
        view.viewGame.setBalance(balance);
        printWriter.println(balance);
    }

    public void changeBalanceDice(String gameInput){
        int bet = Integer.parseInt(view.viewGame.getBetAmountText());
        String choice = view.viewGame.getSelectedDice();
        if(choice.equals(gameInput)){
            balance += bet*5;
        } else {
            balance -=bet;
        }
        view.viewGame.setDiceState(gameInput);
        view.viewGame.setBalance(balance);
        printWriter.println(balance);
    }
    public void getLeaderboard(){
        ArrayList<String> top3 = new ArrayList<>();
        for (int j = 0; j < 3; j++) {
            try {
                String add = bufferedReader.readLine();
                top3.add(add);
            } catch (IOException e) {
                System.out.println(e);
            }
        }
        view.viewGame.updateLeaderboardList(top3);
    }


    public static boolean checkUserBet(){
        int intValue = 0;
        try {
            intValue = Integer.parseInt(view.viewGame.getBetAmountText());
        } catch (NumberFormatException ex) {
            view.viewGame.message.setText("Please enter a number for your bet");
        }
        if (intValue > balance) {
            view.viewGame.message.setText("You don't have enough money for that!");
            return false;
        } else if (intValue < 0){
            view.viewGame.message.setText("Enter a positive value");
            return false;
        } else if (balance == 0){
            view.viewGame.message.setText("No more money, you lose!");
            return false;
        } else {
            return true;
        }
    }

    private class LoginButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e){
            if(!view.viewLogin.getUsernameText().isEmpty() && !view.viewLogin.getPasswordText().isEmpty()){
                printWriter.println("login");
                printWriter.println(view.viewLogin.getUsernameText());
                printWriter.println(view.viewLogin.getPasswordText());
            }else if(view.viewLogin.getUsernameText().isEmpty()){
                System.out.println("Please enter a username and password!");
            }
        }
    }

    private static class signUpButtonListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e){
            if(!view.viewLogin.getUsernameText().isEmpty() && !view.viewLogin.getPasswordText().isEmpty()){
                printWriter.println("signup");
                printWriter.println(view.viewLogin.getUsernameText());
                printWriter.println(view.viewLogin.getPasswordText());
            }else if(view.viewLogin.getUsernameText().isEmpty()){
                view.viewGame.message.setText("Please enter a username and password!");
            }
        }
    }

    private static class coinFlipButtonListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e){
            try{
                view.viewGame.getSelected();
                view.viewGame.disableButtons();
                if(!view.viewGame.getBetAmountText().isEmpty() && checkUserBet()) {
                    printWriter.println("flipCoin");
                }else if(view.viewGame.getBetAmountText().isEmpty()){
                    view.viewGame.message.setText("Please enter bet amount");
                }
                view.viewGame.enableButtons();
            } catch (Exception ex) {
                view.viewGame.message.setText("Make a coin selection to bet on");
            }
        }
    }

    private static class diceButtonListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e){
            try{
                view.viewGame.getSelectedDice();
                view.viewGame.disableButtons();
                view.viewGame.message.setText("");
                if(!view.viewGame.getBetAmountText().isEmpty() && checkUserBet()) {
                    printWriter.println("rollDice");
                }else if(view.viewGame.getBetAmountText().isEmpty()){
                    view.viewGame.message.setText("Please enter bet amount");
                }
                view.viewGame.enableButtons();
            } catch (Exception ex) {
                view.viewGame.message.setText("Make a dice selection to bet on");
            }
        }
    }

}
