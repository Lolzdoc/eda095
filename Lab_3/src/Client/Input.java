package Client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 * Created by dracyr on 2016-04-19.
 * Prints what server has to say
 */
public class Input extends Thread {
    private Socket socket;
    private InputStream in;

    public Input(Socket socket, InputStream in) {
        this.socket = socket;
        this.in = in;
    }

    public void run() {
        BufferedReader streamReader = new BufferedReader(new InputStreamReader(in));
        String line;
        boolean connected = true;
        try {
            while (socket.isConnected() && connected) {
                line = streamReader.readLine();
                if (line != null) {
                    System.out.println(streamReader.readLine());
                } else {
                    connected = false;
                }
            }
            in.close();
            socket.close();
            System.out.println("[INFO] Server disconnected");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
