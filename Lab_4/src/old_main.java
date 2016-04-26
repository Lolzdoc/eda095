import org.jsoup.Jsoup;
import org.jsoup.*;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
import java.util.concurrent.Semaphore;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    private static final String DOWNLOAD_FOLDER_NAME = "downloaded";

    private static Pattern URL_tag_pattern_pdf = Pattern.compile("href=\"([\\w\\/\\.;:_-]+pdf)\"");
    private static Pattern URL_pattern = Pattern.compile("[-a-zA-Z0-9@:%_\\+.~#?&//=]{2,256}\\.[a-z]{2,4}\\b(\\/[-a-zA-Z0-9@:%_\\+.~#?&/:/=]*)?");
    private static Pattern EMAIL_pattern = Pattern.compile("(mailto:)+([a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,})+");

    private static Semaphore semaphore = new Semaphore(10);

    public static void main(String args[]) {

        Set<String> foundWebLinks;
        Set<String> crawledWebLinks;
        Set<String> emaillinks;
        ArrayList<String>crawlQueue;

        crawlQueue = new ArrayList<String>();

        foundWebLinks = new HashSet<>();
        emaillinks = new HashSet<>();
        crawledWebLinks = new HashSet<>();

        Scanner reader = new Scanner(System.in);  // Reading from System.in
        String dir = System.getProperty("user.dir");
        dir = dir + "/" + DOWNLOAD_FOLDER_NAME;

        System.out.print("Enter a Url: \n");
        String target_string = "http://cs.lth.se/eda095/";

        Matcher matcher = URL_pattern.matcher(target_string);//reader.nextLine());
        if (matcher.find()) {
            crawlQueue.add(matcher.group(0));

            while (!crawlQueue.isEmpty() && foundWebLinks.size() < 50) {
                String target = crawlQueue.get(0); // Pop first element
                crawlQueue.remove(0);
                if(crawledWebLinks.add(target)){
                    try {
                        System.out.println("target = " + target);
                        Document doc = Jsoup.connect(target).get();
                        Element content = doc.getElementById("content");
                        if(content != null){
                            Elements links = content.getElementsByTag("a");

                            for (Element link : links) {
                                String linkHref = link.attr("href");
                                Matcher emailMatcher = EMAIL_pattern.matcher(linkHref);
                                if (emailMatcher.find()) {
                                    linkHref = emailMatcher.group(2);
                                    emaillinks.add(linkHref);
                                    System.out.println("emailLinks = " + linkHref);
                                } else if (URL_pattern.matcher(linkHref).find()) {

                                    if(foundWebLinks.add(linkHref)){
                                        crawlQueue.add(linkHref);
                                        System.out.println("foundWebLinks = " + linkHref);
                                    }

                                }
                            }

                        }

                    } catch (IOException e) {
                        e.printStackTrace();
                        System.out.println("ERROR: Not a Valid URL");
                    }



                }

            }
        }
    }

    private static boolean createFolder(File theDir) {
        try {
            theDir.mkdir();
            return true;
        } catch (SecurityException se) {
            return false;
        }
    }


}
