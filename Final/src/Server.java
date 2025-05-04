import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class Server {
    private static int userId = 1;
    static PrintWriter printWriter;
    static InputStreamReader inputStreamReader;
    static BufferedReader bufferedReader;

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(5000);
        System.out.println("server started on port: "+ serverSocket.getLocalPort());
        int num = 0;
        while (true) {
            Socket clientSocket = serverSocket.accept();
            System.out.println("Client connected " + clientSocket.getPort());

            ThreadMultiClient threadMultiClient = new ThreadMultiClient(num, clientSocket);
            Thread thread = new Thread(threadMultiClient);
            thread.start();
            num++;
        }
    }
}
