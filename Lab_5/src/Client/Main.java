package Client;


import java.io.IOException;
import java.net.*;
import java.nio.charset.StandardCharsets;

public class Main {

    public static void main(String[] args) {
        try {
            InetAddress serverAddress = InetAddress.getByName(args[0]);
            int port = Integer.parseInt(args[1]);
            String command = args[2].trim();
            byte[] responseBuffer = new byte[50];

            System.out.println("args = " + args[0] + ", " + args[1] + ", " + args[2]);
            DatagramSocket socket = new DatagramSocket();
            DatagramPacket request = new DatagramPacket(command.getBytes(), command.getBytes().length,serverAddress,port);
            DatagramPacket response = new DatagramPacket(responseBuffer,responseBuffer.length);

            socket.send(request);
            System.out.println("Request sent");
            socket.receive(response);
            String data = new String(response.getData());
            System.out.println("data = " + data);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
