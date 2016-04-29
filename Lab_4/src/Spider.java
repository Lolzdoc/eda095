import java.util.HashSet;
import java.util.LinkedList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by dracyr on 2016-04-26.
 */
public class Spider extends Thread {
    private final HashSet<String> crawledLinks = new HashSet<>();
    private final HashSet<String> crawledEmails = new HashSet<>();
    private final LinkedList<String> linkQueue = new LinkedList<>();
    private ExecutorService pool;
    private Boolean havePrinted = false;
    public Spider(ExecutorService pool) {
        this.pool = pool;
    }

    public synchronized void addLink(String url) {
        if (crawledLinks.size() < 1000) {
            if (crawledLinks.add(url)) {
                pool.submit(new Processor(this, url));
            }
        } else {
            pool.shutdownNow();
            if (!havePrinted) {
                printOut();
                havePrinted = true;
            }
        }
    }

    public synchronized void addEmail(String email) {
        crawledEmails.add(email);
    }

    public void printOut() {
        System.out.println("\ncrawledLinks = " + crawledLinks.size());
        System.out.println("crawledEmails = " + crawledEmails.size());

    }
}
