import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(5000);
        System.out.println("server started on port: "+ serverSocket.getLocalPort());
        while (true){
            Socket clientSocket = serverSocket.accept();
            System.out.println("Client connected "+ clientSocket.getPort());

            InputStreamReader inputStreamReader = new InputStreamReader(clientSocket.getInputStream());
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String inputMsg = bufferedReader.readLine();
            System.out.println("Server recieved: "+ inputMsg);

            PrintWriter printWriter = new PrintWriter(clientSocket.getOutputStream(), true);
            printWriter.println("Message from server");
            System.out.println("Message sent to client");

            printWriter.close();
        }
    }

}
