package Server;

import java.io.*;
import java.net.Socket;

/**
 * Created by dracyr on 2016-04-19.
 */
public class Connection extends Thread {
    private PrintWriter writer;
    private InputStream in;
    private OutputStream out;
    private MailBox mailBox;

    public Connection(Socket socket, MailBox mailBox) throws IOException {
        this.in = socket.getInputStream();
        this.out = socket.getOutputStream();
        this.writer = new PrintWriter(out);
        this.mailBox = mailBox;
    }

    public void write(String msg) {
        writer.println(msg);
        writer.flush();
    }

    public void run() {
        try {
            boolean connected = true;
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            String line;
            while (connected) {
                line = reader.readLine();
                if (line != null) {
                    String id = line.substring(0,2);
                    String msg = line.substring(2).trim();
                    switch (id) {
                        case "Q:":
                            connected = true;
                            break;
                        case "M:":
                            mailBox.post(msg);
                            break;
                        case "E:":
                            System.out.println("[ECHO] " + msg);
                            write(msg);
                            break;
                        default:
                            System.out.println("[ERROR] Unknown message type: " + id);
                            write("[ERROR] Unknown message type: " + id);
                            break;
                    }
                } else {
                    connected = false;
                }
            }
            reader.close();
            writer.close();
            System.out.println("[INFO] Client disconnected!");
        } catch (IOException e) {
            System.out.println("[INFO] Client disconnected!");
        }
    }
}
