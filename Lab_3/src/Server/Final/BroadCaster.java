package Server.Final;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Vector;

import static java.lang.Thread.sleep;


public class BroadCaster extends Thread {

    private final MailBox mailBox;
    private Vector<PrintWriter> outputs;
    public BroadCaster(MailBox mailBox, Vector<PrintWriter> outputs) {
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
                        for (PrintWriter out : outputs) {
                            System.out.println("BroadCaster.run " + msg);
                            out.println(msg);
                            out.flush();
                        }
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
