package src;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ExecutorsMain {
    private static final String DOWNLOAD_FOLDER_NAME = "downloaded";

    private static Pattern URL_tag_pattern_pdf = Pattern.compile("href=\"([\\w\\/\\.;:_-]+pdf)\"");
    private static Pattern URL_pattern = Pattern.compile("[-a-zA-Z0-9@:%_\\+.~#?&//=]{2,256}\\.[a-z]{2,4}\\b(\\/[-a-zA-Z0-9@:%_\\+.~#?&//=]*)?");


    public static void main(String args[]) {

        Scanner reader = new Scanner(System.in);  // Reading from System.in
        String dir = System.getProperty("user.dir");
        dir = dir + "/" + DOWNLOAD_FOLDER_NAME;


        while(true) {
            System.out.print("Enter a Url: ");
            URL target;

            Matcher matcher = URL_pattern.matcher(reader.nextLine());
            if (matcher.find()) {
                try {
                    target = new URL(matcher.group(0));
                    BufferedReader in = new BufferedReader(
                            new InputStreamReader(target.openStream()));
                    StringBuilder inputLine = new StringBuilder();
                    String line;

                    while ((line = in.readLine()) != null) {
                        inputLine.append(line); // reads all the lines in the html file
                    }

                    in.close(); // close stream from server

                    ArrayList<URL> URLs_found = getContent(URL_tag_pattern_pdf ,inputLine.toString(), target);

                    File theDir = new File(dir);// if the directory does not exist, create it

                    if (!theDir.exists()) {
                        if (createFolder(theDir)) {
                            System.out.println("created directory: " + dir);
                        } else {
                            System.out.println("ERROR: failed to create directory: " + dir);
                        }
                    }
                    ExecutorService pool = Executors.newFixedThreadPool(30);

                    for (URL aURLs_found : URLs_found) {
                        pool.execute(new RunnableDownloader(aURLs_found, dir));
                    }

                    pool.shutdown();
                    try {
                        pool.awaitTermination(60, TimeUnit.SECONDS);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    // System.out.println("Successfully downloaded: " + downloads + " File(s)");
                    System.out.println("Downloaded files are stored in: " + dir);
                } catch (IOException e) {
                    System.out.println("ERROR: Not a Valid URL");
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

    private static ArrayList<URL> getContent(Pattern pattern, String input, URL parent) {
        ArrayList<URL> content = new ArrayList<>();
        Matcher URL_tag = pattern.matcher(input);
        while (URL_tag.find()) {
            try {
                String url = URL_tag.group(1);
                if(!url.substring(0,7).equals("http://")) {
                    url = "http://" + parent.getHost() + "/" + url;
                }
                System.out.println(url);
                content.add(new URL(url));
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }

        return content;
    }


}
