package Coordination;


public class printMailbox extends Thread {
    private MailBox nx;


    public printMailbox(MailBox nx) {
        this.nx = nx;
    }

    public void run() {

        while (true) {
            try {
                sleep((long) (Math.random() * 10));
                synchronized (nx) {
                    String msg = nx.read();
                    if (!msg.matches("")) {
                        System.out.println(msg);

                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

}
