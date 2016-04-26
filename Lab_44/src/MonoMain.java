import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;
import java.util.regex.Pattern;

public class MonoMain {
    private static Pattern URL_pattern = Pattern.compile("[-a-zA-Z0-9@:%_\\+.~#?&//=]{2,256}\\.[a-z]{2,4}\\b(\\/[-a-zA-Z0-9@:%_\\+.~#?&/:/=]*)?");

    public static void main(String args[]) {
        Set<String> crawledWebLinks = new HashSet<>();
        Set<String> emaillinks = new HashSet<>();
        LinkedList<String> crawlQueue = new LinkedList<>();

        System.out.print("Enter a Url: \n");
        String target_string = "http://cs.lth.se/eda095/";
        crawlQueue.add(target_string);

        while (!crawlQueue.isEmpty() && crawledWebLinks.size() < 200) {
            String target = crawlQueue.remove(); // Pop first element

            if (crawledWebLinks.add(target)) {
                try {
                    Document doc = Jsoup.connect(target).get();
                    Elements links = doc.getElementsByTag("a");
                    Elements mailLinks = links.select("[href^='mailto:']");
                    links = links.select(":not([href^='mailto:'])");

                    for (Element link : links) {
                        if (!link.attr("href").isEmpty()) {
                            crawlQueue.add(link.attr("abs:href"));
                        }
                    }

                    for (Element mailLink : mailLinks) {
                        emaillinks.add(mailLink.attr("href"));
                        System.out.println("[MAIL] " + target);
                    }

                    /* Why */
                    Elements frames = doc.getElementsByTag("frame").select("[src]");
                    for (Element frame : frames) {
                        System.out.println("[FRAME] " + frame.attr("src"));
                    }

                    System.out.println("[PAGE] " + target);
                } catch (IOException e) {
                    System.out.println("[ERROR] '" + target + "' " + e.getMessage());
                }
            }
        }
    }
}
