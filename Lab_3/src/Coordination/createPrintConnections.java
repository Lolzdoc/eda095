package Coordination;

/**
 * Created by hansr on 2016-04-19.
 */
public class createPrintConnections {

    public static void main(String args[]) {

        for (int i = 0; i < 10; i++) {
            new printName("Thread: " + i).start();
        }

    }


}
