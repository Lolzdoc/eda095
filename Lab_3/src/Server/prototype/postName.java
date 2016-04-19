package Server.prototype;


public class postName extends Thread {
    private String name;
    private MailBox nx;

    public postName(String name, MailBox nx) {
        this.name = name;
        this.nx = nx;
    }


    public void run() {
        for (int i = 0; i < 5; i++) {
            try {
                sleep((long) (Math.random() * 10));
                synchronized (nx) {
                    nx.post(name);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


}
