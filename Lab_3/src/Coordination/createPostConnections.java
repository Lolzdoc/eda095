package Coordination;

import java.util.ArrayList;

/**
 * Created by hansr on 2016-04-19.
 */
public class createPostConnections {

    public static void main(String args[]) {
        MailBox mailBox = new MailBox();
        ArrayList<Thread> threads = new ArrayList<>();
        threads.add(new printMailbox(mailBox));

        for (int i = 0; i < 10; i++) {
            threads.add(new postName("Thread: " + i, mailBox));
        }

        for (Thread th : threads) {
            th.start();
        }
    }
}
