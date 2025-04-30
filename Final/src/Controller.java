import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Objects;

public class Controller {
    private static int balance = 100;
    private static View view;
    static PrintWriter printWriter;
    public Controller(){
        System.out.println("hello from controller");
        view = new View();
        view.loginButtonListener(new LoginButtonListener());
        view.coinFlipButtonListener(new coinFlipButtonListener());
        view.signUpButtonListener(new signUpButtonListener());
    }


    public void start() {
        try (Socket socket = new Socket("localhost", 5000)) {
            System.out.println("Connected to: " + socket.getPort());

            view.initializeGUI();

            InputStreamReader inputStreamReader = new InputStreamReader(socket.getInputStream());
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            printWriter = new PrintWriter(socket.getOutputStream(), true);

            InputStreamReader inputStreamReader1 = new InputStreamReader(System.in);
            BufferedReader bufferedReader1 = new BufferedReader(inputStreamReader1);

            String bet;
            String amount;
            int amountInt = 0;

            //Loop for login
            while(true) {
                System.out.println("Username:");
                String username = bufferedReader1.readLine();
                System.out.println("Password");
                String password = bufferedReader1.readLine();
                //String username = view.getUsernameText();
                //String password = view.getPasswordText();

                printWriter.println(username);
                printWriter.println(password);

                String verify = bufferedReader.readLine();
                if (verify.equals("failed")) {
                    System.out.println("Incorrect username and password!");

                } else if (verify.equals("success")){
                    break;
                }
            }

            //loop for gameplay
            for(int i =0; i < 5; i++){
                System.out.println("Heads or Tails?");
                bet = bufferedReader1.readLine();
                System.out.println("Amount: ");
                amount = bufferedReader1.readLine();
                try {
                    amountInt = Integer.parseInt(amount);
                } catch (NumberFormatException e) {
                    System.out.println(e);
                }

                printWriter.println("flipCoin");
                System.out.println("Coin flip requested");

                String input = bufferedReader.readLine();
                System.out.println(input);

                if(input.equals(bet)){
                    amountInt = amountInt*2;
                    balance = balance+amountInt;
                    amountInt = 0;
                } else {
                    balance = balance-amountInt;
                    amountInt = 0;
                }

                System.out.println("Balance = "+ balance);
                printWriter.println(balance);

                try {
                    Thread.sleep(1000); // Pause for 1 second
                } catch (InterruptedException e) {
                    // Handle the exception if the sleep is interrupted
                    e.printStackTrace();
                }
            }

        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private class LoginButtonListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e){
            if(!view.username.getText().isEmpty() && !view.password.getText().isEmpty()){
                System.out.println("LOGIN BUTTON CLICKED");
                printWriter.println("checkUser: "+ view.username.getText());
                printWriter.println("checkPassword: "+ view.password.getText());
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
            if(!view.username.getText().isEmpty() && !view.password.getText().isEmpty()){
                System.out.println("SIGN UP BUTTON CLICKED");
            }else{
                System.out.println("Please enter a username and password!");
            }
        }
    }

    private static class coinFlipButtonListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e){
            if(!view.betAmount.getText().isEmpty()) {
                int intValue = 0;
                try {
                    intValue = Integer.parseInt(view.betAmount.getText());
                } catch (NumberFormatException ex) {
                    //throw new RuntimeException(ex);
                    System.out.println("Please enter a number");
                    // do not allow them to flip the coin
                }
                if (intValue > balance) { //check if is dollar amount
                    System.out.println("You don't have enough money for that!");
                }
                else if(intValue < 0){
                    System.out.println("Enter a positive value");
                }
                //check if it is a positive integer
                else {
                    System.out.println("COIN FLIP BUTTON CLICKED");
                }
            }

            else{
                System.out.println("Please enter a bet amount");
            }

        }
    }
}
