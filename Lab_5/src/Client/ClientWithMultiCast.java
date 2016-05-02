package Client;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;

public class ClientWithMultiCast {

    public static void main(String[] args) {
        try {
            byte[] responseBuffer = new byte[256];
            int port = Integer.parseInt(args[0]);
            String command = args[1].trim();
            InetAddress serverAddress = findServer();

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

    private static InetAddress findServer() throws IOException {
        byte[] buffer = new byte[256];
        MulticastSocket ms = new MulticastSocket();
        ms.setTimeToLive(1);
        InetAddress multicastAddress = InetAddress.getByName("experiment.mcast.net");

        DatagramPacket nameRequest = new DatagramPacket(new byte[0],0,multicastAddress,4099);
        ms.send(nameRequest);
        DatagramPacket response = new DatagramPacket(buffer,buffer.length);
        ms.receive(response);
        String serverHostName = new String(response.getData()).trim();
        return InetAddress.getByName(serverHostName);
    }
}
