package EchoServer;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class EchoTCP2 {
    ArrayList<Socket> sockets = new ArrayList<>();

    public static void main(String args[]) {

        try {
            ServerSocket serverSocket = new ServerSocket(30000);
            while (true) {

                Socket socket = serverSocket.accept();
                System.out.println("[INFO] Client connected!");
                new Thread(new ConnectionService(socket, socket.getInputStream(), socket.getOutputStream())).start();

            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}
