package Server.Final;


import java.io.*;

public class ConnectionService extends Thread {
    private InputStream in;
    private final MailBox box;
    public ConnectionService(InputStream in,MailBox box) {
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
                System.out.println(line);
                if (line != null) {
                    if (line.contains("quit")) {
                        done = true;
                    } else {
                        synchronized (box) {
                            box.post(line);
                        }
                    }
                }
            }
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
