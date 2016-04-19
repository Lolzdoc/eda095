package Server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Main {
    ArrayList<Socket> sockets = new ArrayList<>();

    public static void main(String args[]) {

        try {
            ServerSocket serverSocket = new ServerSocket(30000);
            while (true) {

                Socket socket = serverSocket.accept();
                new Thread(new ConnectionService(socket.getInputStream(), socket.getOutputStream())).start();

            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}
