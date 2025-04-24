import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Objects;

public class Controller {
    private int balance = 100;

    public void start() {
        try (Socket socket = new Socket("localhost", 5000)) {
            System.out.println("Connected to: " + socket.getPort());

            InputStreamReader inputStreamReader = new InputStreamReader(socket.getInputStream());
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            PrintWriter printWriter = new PrintWriter(socket.getOutputStream(), true);

            InputStreamReader inputStreamReader1 = new InputStreamReader(System.in);
            BufferedReader bufferedReader1 = new BufferedReader(inputStreamReader1);
            String contUserName;
            String contPassword;
            String bet;
            String amount;
            int amountInt;

            System.out.println("Enter your username: ");
            contUserName = bufferedReader1.readLine();
            System.out.println(contUserName);
            printWriter.println(contUserName);

            System.out.println("Enter your password: ");
            contPassword = bufferedReader1.readLine();
            System.out.println(contPassword);
            printWriter.println(contPassword);

            for(int i =0; i < 5; i++){
                System.out.println("Heads or Tails?");
                bet = bufferedReader1.readLine();
                System.out.println("Amount: ");
                amount = bufferedReader1.readLine();
                amountInt = Integer.parseInt(amount);

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
}
