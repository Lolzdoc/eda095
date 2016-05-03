import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class Processor implements Runnable {
    private Spider spider;
    private String url;

    public Processor(Spider spider, String url) {
        this.spider = spider;
        this.url = url;
    }

    @Override
    public void run() {
        processURL(url);
    }

    private void processURL(String url) {
        try {
            Document doc = Jsoup.connect(url)
                    .userAgent("Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:25.0) Gecko/20100101 Firefox/25.0")
                    .referrer("http://www.google.com")
                    .timeout(1000*5) //it's in milliseconds, so this means 5 seconds.
                    .get();
            Elements links = doc.getElementsByTag("a");
            Elements mailLinks = links.select("[href^='mailto:']");
            links = links.select(":not([href^='mailto:'])");

            for (Element link : links) {
                if (!link.attr("href").isEmpty()) {
                    spider.addLink(link.attr("abs:href"));
                }
            }

            for (Element mailLink : mailLinks) {
                spider.addEmail(mailLink.attr("href"));
                System.out.println("[MAIL] " + mailLink.attr("href"));
            }

                /* Why */
            Elements frames = doc.getElementsByTag("frame").select("[src]");
            for (Element frame : frames) {
                spider.addLink(frame.attr("src"));
                System.out.println("[FRAME] " + frame.attr("src"));
            }

          System.out.println("[PAGE] " + url);
        } catch (IOException e) {
           System.out.println("[ERROR] '" + url + "' " + e.getMessage());
        }
    }
}
