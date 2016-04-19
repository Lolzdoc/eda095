package Server;

import java.util.Vector;

public class BroadCaster extends Thread {

    private final MailBox mailBox;
    private Vector<Connection> connections;
    public BroadCaster(MailBox mailBox, Vector<Connection> connections) {
        this.mailBox = mailBox;
        this.connections= connections;
    }


    public void run() {
        while(true) {
            try {
                sleep((long) (Math.random() * 10));
                synchronized (mailBox) {
                    String msg = mailBox.read();
                    if (!msg.matches("")) {
                        for (Connection connection: connections) {
                            System.out.println("BroadCaster.run " + msg);
                            connection.sendln(msg);
                        }
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
