package Server;

import Client.Output;

import java.io.*;
import java.net.Socket;

/**
 * Created by dracyr on 2016-04-19.
 */
public class Connection extends Thread {
    private PrintWriter pw;
    private InputStream in;
    private OutputStream out;
    private MailBox mailBox;

    public Connection(Socket socket, MailBox mailBox) {
        this.in = socket.getInputStream();
        this.out = socket.getOutputStream();
        this.pw = new PrintWriter(out);
        this.mailBox = mailBox;
    }

    public void sendln(String msg) {
        pw.println(msg);
        pw.flush();
    }

    public void run() {
        try {
            BufferedReader inputStream = new BufferedReader(new InputStreamReader(in));

            boolean done = false;
            String line;
            while (!done) {
                done = false;
                line = inputStream.readLine();
                if (line != null) {
                    if (!line.contains("Q:")) {
                        synchronized (mailBox) {
                            mailBox.post(line);
                        }
                    } else {
                        done = true;
                    }
                } else {
                    done = true;
                }
            }
            inputStream.close();
            System.out.println("[INFO] Client disconnected!");
        } catch (IOException e) {
            System.out.println("[INFO] Client disconnected!");
        }
    }
}
