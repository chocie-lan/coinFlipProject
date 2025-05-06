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

            //Loop for login
            while (true) {
                String verify = bufferedReader.readLine();
                if (verify.equals("failed")) {
                    view.viewLogin.statusMessage.setText("Invalid login information, please sign up!");
                }else if (verify.equals("success")) {
                    view.viewLogin.statusMessage.setText("User Accepted!");
                    view.cardLayout.show(view.cardPanel, "Game");
                    break;
                }
            }

            //loop for gameplay
            String gameInput;
            while(true){
                gameInput = bufferedReader.readLine();
                if (!gameInput.equals(null) && (gameInput.equals("heads") || gameInput.equals("tails"))) {
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
        int bet = Integer.parseInt(view.viewGame.getBetAmountText());
        String choice = view.viewGame.getSelected();
        if(choice.equals(gameInput)){
            balance += bet;
        } else {
            balance -=bet;
        }
        view.viewGame.setCoinState(gameInput);
        view.viewGame.setBalance(balance);
        printWriter.println(balance);
    }

    public void changeBalanceDice(String gameInput){
        int bet = Integer.parseInt(view.viewGame.getBetAmountText());
        String choice = view.viewGame.getSelectedDice();
        if(choice.equals(gameInput)){
            balance += bet; //used to be times 6, shouldn't it just increase by bet if correct and decrease if incorrect?
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
            System.out.println("Please enter a number");
            view.viewGame.message.setText("Please enter a number for your bet");
        }
        if (intValue > balance) {
            System.out.println("You don't have enough money for that!");
            view.viewGame.message.setText("You don't have enough money for that!");
            return false;
        }
        else if (intValue < 0){
            System.out.println("Enter a positive value");
            view.viewGame.message.setText("Enter a positive value");
            return false;
        }
        else {
            return true;
        }
    }

    private class LoginButtonListener implements ActionListener { //login & signup have a bit of repetition
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
            if(!view.viewGame.getBetAmountText().isEmpty() && checkUserBet()) {
                printWriter.println("flipCoin");
            }else if(view.viewGame.getBetAmountText().isEmpty()){
                view.viewGame.message.setText("Please enter bet amount");
            }
        }
    }

    private static class diceButtonListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            view.viewGame.message.setText("");
            if(!view.viewGame.getBetAmountText().isEmpty() && checkUserBet()) {
                    printWriter.println("rollDice");
            }
        }
    }
}
