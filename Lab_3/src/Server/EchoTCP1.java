package Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class EchoTCP1 {
    ArrayList<Socket> sockets = new ArrayList<>();

    public static void main(String args[]) {

        try {
            ServerSocket serverSocket = new ServerSocket(30000);
            while (true) {

                Socket socket = serverSocket.accept();
                BufferedReader inputStream = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                OutputStreamWriter outputStream = new OutputStreamWriter(socket.getOutputStream());
                boolean done = false;
                String line;
                while (!done) {
                    done = false;
                    line = inputStream.readLine();
                    System.out.println(line);
                    if (line != null) {
                        outputStream.write(line, 0, line.length());
                        if (line.contains("quit")) {
                            done = true;
                        }
                    }


                }
                inputStream.close();
                outputStream.close();

            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}
