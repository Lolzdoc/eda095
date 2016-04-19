package Server;


import java.io.*;

public class ConnectionService extends Thread {
    private InputStream in;
    private final MailBox box;

    public ConnectionService(InputStream in, MailBox box) {
        this.in = in;
        this.box = box;
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
                    System.out.println(line);
                    if (line.contains("quit")) {
                        done = true;
                    } else {
                        synchronized (box) {
                            box.post(line);
                        }
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
