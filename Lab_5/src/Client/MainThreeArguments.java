package Client;

import java.io.IOException;
import java.net.*;

public class MainThreeArguments {

    public static void main(String[] args) {
        try {
            byte[] responseBuffer = new byte[256];
            InetAddress serverAddress = InetAddress.getByName(args[0]);
            int port = Integer.parseInt(args[1]);
            String command = args[2].trim();

            DatagramSocket socket = new DatagramSocket();
            DatagramPacket request = new DatagramPacket(command.getBytes(), command.getBytes().length,serverAddress,port);
            DatagramPacket response = new DatagramPacket(responseBuffer,responseBuffer.length);

            socket.send(request);
            System.out.println("Request sent");
            socket.receive(response);
            String data = new String(response.getData());
            System.out.println("Response: " + data);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
