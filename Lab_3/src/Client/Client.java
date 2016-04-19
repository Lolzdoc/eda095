package Client;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Scanner;

/**
 * Created by dracyr on 2016-04-19.
 */
public class Client {
    private final Socket clientSocket = new Socket();
    private final String host;
    private final int port;

    public Client(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public void connect() throws IOException {
        clientSocket.connect(new InetSocketAddress(host, port));
        InputStream in = clientSocket.getInputStream();
        OutputStream out = clientSocket.getOutputStream();
        new Input(clientSocket, in).start();
        new Output(clientSocket, out).start();

        System.out.println("[INFO] Connected to server!");

    }
}
