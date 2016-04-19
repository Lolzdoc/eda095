package Client;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

/**
 * Created by dracyr on 2016-04-19.
 */
public class Output extends Thread {
    private Socket socket;
    private OutputStream out;

    public Output(Socket socket, OutputStream out) {
        this.socket = socket;
        this.out = out;
    }

    public void run() {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter writer = new PrintWriter(out, true);
        String line;

        try {
            while (socket.isConnected()) {
                line = bf.readLine();
                writer.println(line);
                writer.flush();
            }
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
