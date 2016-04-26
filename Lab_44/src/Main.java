import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {

    public static void main(String args[]) {
        System.out.print("Enter a Url: ");
        Scanner scan = new Scanner(System.in);
        String url = scan.nextLine(); //"http://cs.lth.se/eda095/";
        ExecutorService pool = Executors.newFixedThreadPool(30);

        Spider spider = new Spider(pool);
        System.out.println("Crawling the web...");
        spider.addLink(url);
    }
}
