package EchoServer;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class EchoTCP1 {

    public static void main(String args[]) {
        try {
            ServerSocket serverSocket = new ServerSocket(30000);
            while (!serverSocket.isClosed()) {
                Socket socket = serverSocket.accept();
                BufferedReader inputStream = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                OutputStreamWriter outputStream = new OutputStreamWriter(socket.getOutputStream());
                PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);

                boolean connected = true;
                String line;
                while (connected) {
                    line = inputStream.readLine();
                    System.out.println(line);
                    if (line != null) {
                        writer.println(line);
                        writer.flush();

                        if (line.contains("quit")) {
                            connected = false;
                        }
                    }
                }
                inputStream.close();
                outputStream.close();
                serverSocket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}
