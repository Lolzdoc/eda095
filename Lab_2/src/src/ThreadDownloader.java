package src;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.concurrent.Semaphore;


public class ThreadDownloader extends Thread {
    private URL url;
    private String dir;
    private Semaphore semaphore;

    public ThreadDownloader(URL url, String dir, Semaphore semaphore) {
        this.url = url;
        this.dir = dir;
        this.semaphore = semaphore;
    }

    public void run() {

        try {
            semaphore.acquire();
            ReadableByteChannel rbc = Channels.newChannel(url.openStream());
            FileOutputStream fos = new FileOutputStream(dir + "/" + extractFileName(url));
            fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
            semaphore.release();
        } catch (FileNotFoundException e) {
            semaphore.release();
            System.out.println("[ERROR] Could not find file: " + url);
        } catch (IOException e) {
            semaphore.release();
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private String extractFileName(URL url) {
        String full_path = url.getFile();
        String[] parts = full_path.split("/");
        return parts[parts.length - 1]; // last token is the filename
    }
}
