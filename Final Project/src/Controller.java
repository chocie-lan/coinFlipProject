import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class Controller {
    public void start() {
        try (Socket socket = new Socket("localhost", 5000)) {
            System.out.println("Connected to:" + socket.getPort());
            PrintWriter printWriter = new PrintWriter(socket.getOutputStream(), true);
            printWriter.println("Client test");
            System.out.println("Client sent message to server");

            InputStreamReader inputStreamReader = new InputStreamReader(socket.getInputStream());
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String inputMsg = bufferedReader.readLine();
            System.out.println("Client recieves: "+ inputMsg);

            printWriter.close();
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
