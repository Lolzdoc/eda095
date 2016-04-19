package Server;

import java.util.Vector;

public class BroadCaster extends Thread {
    private MailBox mailBox;
    private Vector<Connection> connections;
    public BroadCaster(MailBox mailBox, Vector<Connection> connections) {
        this.mailBox = mailBox;
        this.connections= connections;
    }

    public void run() {
        while(true) {
            try {
                sleep((long) (Math.random() * 10));
                String msg = mailBox.read();
                if (!msg.matches("")) {
                    System.out.println("[BROADCAST] " + msg);
                    for (Connection connection: connections) {
                        connection.write(msg);
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
