package Coordination;


public class printMailbox extends Thread {
    private MailBox mailBox;

    public printMailbox(MailBox mailBox) {
        this.mailBox = mailBox;
    }

    public void run() {
        while (true) {
            try {
                sleep((long) (Math.random() * 10));
                String msg = mailBox.read();
                if (!msg.matches("")) {
                    System.out.println(msg);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
