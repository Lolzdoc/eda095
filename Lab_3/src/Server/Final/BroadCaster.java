package Server.Final;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Vector;

import static java.lang.Thread.sleep;


public class BroadCaster extends Thread {

    private MailBox mailBox;
    private Vector<OutputStreamWriter> outputs;
    public BroadCaster(MailBox mailBox, Vector <OutputStreamWriter> outputs) {
        this.mailBox = mailBox;
        this.outputs = outputs;
    }


    public void run() {
        while(true) {
            try {
                sleep((long) (Math.random() * 10));
                synchronized (mailBox) {
                    String msg = mailBox.read();
                    if (!msg.matches("")) {

                        for (OutputStreamWriter out : outputs) {
                            System.out.println("BroadCaster.run " + msg);
                            out.write(msg, 0, msg.length());
                        }

                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }


    }
}
