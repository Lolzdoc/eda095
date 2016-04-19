package EchoServer;


import java.io.*;
import java.net.Socket;

public class ConnectionService implements Runnable {
    private Socket socket;
    private InputStream in;
    private OutputStream out;

    public ConnectionService(Socket socket, InputStream in, OutputStream out) {
        this.socket = socket;
        this.in = in;
        this.out = out;
    }

    public void run() {
        try {
            BufferedReader inputStream = new BufferedReader(new InputStreamReader(in));
            OutputStreamWriter outputStream = new OutputStreamWriter(out);
            PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);

            boolean done = false;
            String line;
            while (!done && socket.isConnected()) {
                done = false;
                line = inputStream.readLine();
                if (line != null) {
                    System.out.println(line);
                    writer.println(line);
                    writer.flush();
                    if (line.contains("quit")) {
                        done = true;
                    }
                } else {
                    done = true;
                }
            }
            inputStream.close();
            outputStream.close();
            System.out.println("[INFO] Client disconnected!");
        } catch (IOException e) {
            System.out.println("[INFO] Client disconnected!");
            // e.printStackTrace();
        }
    }
}
