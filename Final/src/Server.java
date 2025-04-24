import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    static Model model;
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(5000);
        System.out.println("server started on port: "+ serverSocket.getLocalPort());
        model = new Model();
        model.createTable();
        String userName = "user1"; //will be passed from controller (which gets it from view)
        String password = "password123";
        model.addUser(userName, password);
        while (true){
            Socket clientSocket = serverSocket.accept();
            System.out.println("Client connected "+ clientSocket.getPort());

            InputStreamReader inputStreamReader = new InputStreamReader(clientSocket.getInputStream());
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            PrintWriter printWriter = new PrintWriter(clientSocket.getOutputStream(), true);

            String input;
            while(true){
                input = bufferedReader.readLine();
                if(input != null){
                    String out;
                    double flip = Math.random();
                    if(flip >= 0.5){
                        out = "heads";
                    } else {
                        out = "tails";
                    }
                    printWriter.println(out);
                    input = null;
                }
            }
        }
    }
}
