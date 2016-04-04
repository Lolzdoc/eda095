import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;


public class Downloader implements Runnable {
    private URL url;
    private String dir;

    public Downloader(URL url, String dir) {
        this.url = url;
        this.dir = dir;
    }

    public void run() {
        try {
            ReadableByteChannel rbc = Channels.newChannel(url.openStream());
            FileOutputStream fos = new FileOutputStream(dir + "/" + extractFileName(url));
            fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private String extractFileName(URL url) {
        String full_path = url.getFile();
        String[] parts = full_path.split("/");
        return parts[parts.length - 1];

    }
}
