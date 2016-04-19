package Coordination;


public class postName extends Thread {
    private String name;
    private MailBox mailBox;

    public postName(String name, MailBox mailBox) {
        this.name = name;
        this.mailBox = mailBox;
    }

    public void run() {
        for (int i = 0; i < 5; i++) {
            try {
                sleep((long) (Math.random() * 10));
                mailBox.post(name);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
