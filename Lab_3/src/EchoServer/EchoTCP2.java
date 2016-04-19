package EchoServer;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class EchoTCP2 {

    public static void main(String args[]) {
        try {
            ServerSocket serverSocket = new ServerSocket(30000);
            while (!serverSocket.isClosed()) {
                Socket socket = serverSocket.accept();
                InputStream in = socket.getInputStream();
                OutputStream out = socket.getOutputStream();
                new Connection(socket, in, out).start();

                System.out.println("[INFO] Client connected!");
            }
        } catch (IOException e) {
            System.out.println("[ERROR] Client could not connect!");
            e.printStackTrace();
        }
    }
}
