package Server.Final;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Main {

    public static void main(String args[]) {
        ArrayList<Socket> sockets = new ArrayList<>();
        ChatSession session = new ChatSession();

        try {
            ServerSocket serverSocket = new ServerSocket(30000);
            while (true) {
                Socket socket = serverSocket.accept();
                session.addParticipant(socket);
                System.out.println("[INFO] Client connected!");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
