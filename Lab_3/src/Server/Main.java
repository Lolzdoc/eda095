package Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {

    public static void main(String args[]) {
        Server server = new Server();

        try {
            ServerSocket serverSocket = new ServerSocket(30000);
            while (true) {
                Socket socket = serverSocket.accept();
                server.addParticipant(socket);
                System.out.println("[INFO] Client connected!");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
