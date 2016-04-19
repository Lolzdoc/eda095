package Server.Final;


public class MailBox {
    private String message = "";


    public void post(String message) {
        try {
            wait();
            this.message = message;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public String read() {
        String msg = message;
        message = "";
        notifyAll();
        return msg;
    }
}
