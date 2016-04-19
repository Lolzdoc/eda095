package Client;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;

/**
 * Created by hansr on 2016-04-05.
 */
public class Main {
    public static void main(String[] args) {
        if (args.length == 2) {
            System.out.println(args);
            String machine = args[0];
            int port = Integer.parseInt(args[1]);

            Client client = new Client(machine, port);
            try {
                client.connect();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
