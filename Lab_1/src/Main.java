import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    private static final String DOWNLOAD_FOLDER_NAME = "downloaded";

    private static Pattern URL_tag_pattern_pdf = Pattern.compile("<\\s*a\\s*href\\s*=\\s*\"?http{1}\\s*([\\w\\s%#\\/\\.;:_-]*pdf)\\s*\"?.*?");
    private static Pattern URL_pattern = Pattern.compile("[-a-zA-Z0-9@:%_\\+.~#?&//=]{2,256}\\.[a-z]{2,4}\\b(\\/[-a-zA-Z0-9@:%_\\+.~#?&//=]*)?");


    public static void main(String args[]) {

        Scanner reader = new Scanner(System.in);  // Reading from System.in
        String dir = System.getProperty("user.dir");
        dir = dir + "/" + DOWNLOAD_FOLDER_NAME;
        System.out.println("Downloaded files are stored in: " + dir);
        for (; ; ) {
            System.out.println("Enter a Url: ");
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

                    ArrayList<URL> URLs_found = getContent(URL_tag_pattern_pdf ,inputLine.toString());
                    ArrayList<Thread> downloaders = new ArrayList<>(); // contains all of the downloader threads

                    File theDir = new File(dir);// if the directory does not exist, create it

                    if (!theDir.exists()) {
                        if (createFolder(theDir)) {
                            System.out.println("created directory: " + dir);
                        } else {
                            System.out.println("ERROR: failed to create directory: " + dir);
                        }
                    }

                    for (URL aURLs_found : URLs_found) {
                        Thread thr = new Thread(new Downloader(aURLs_found, dir));
                        downloaders.add(thr);
                        thr.start();
                    }

                    int downloads = 0;
                    for (Thread loader : downloaders) { // wait for all of the downloaders to finish before quiting
                        try {
                            loader.join(10000); // Timeout after 10 sec
                            downloads++;
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                            downloads--;
                        }
                    }
                    System.out.println("Successfully downloaded: " + downloads + " File(s)");
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

    private static ArrayList<URL> getContent(Pattern pattern, String input) {
        ArrayList<URL> content = new ArrayList<>();
        Matcher URL_tag = pattern.matcher(input);
        while (URL_tag.find()) {
            try {
                content.add(new URL(input.substring(input.indexOf('\"', URL_tag.start()) + 1, URL_tag.end() - 1)));
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }

        return content;
    }


}
