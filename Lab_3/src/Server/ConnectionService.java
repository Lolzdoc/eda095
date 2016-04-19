package Server;


import java.io.*;
import java.net.Socket;

public class ConnectionService implements Runnable {
    private InputStream in;
    private OutputStream out;

    public ConnectionService(InputStream in, OutputStream out) {
        this.in = in;
        this.out = out;
    }


    public void run() {
        try {
            BufferedReader inputStream = new BufferedReader(new InputStreamReader(in));
            OutputStreamWriter outputStream = new OutputStreamWriter(out);
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
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
