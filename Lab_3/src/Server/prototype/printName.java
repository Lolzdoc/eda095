package Server.prototype;


public class printName extends Thread {
    private String name;

    public printName(String name) {
        this.name = name;
    }


    public void run() {
        for (int i = 0; i < 5; i++) {
            try {
                sleep((long) (Math.random() * 1000));
                System.out.println(name);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


}
