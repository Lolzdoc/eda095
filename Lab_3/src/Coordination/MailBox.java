package Coordination;


public class MailBox {
    private String message = "";

    public synchronized void post(String message) {
        try {
            wait();
            this.message = message;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public synchronized String read() {
        String temp = message;
        message = "";
        notifyAll();
        return temp;
    }
}
