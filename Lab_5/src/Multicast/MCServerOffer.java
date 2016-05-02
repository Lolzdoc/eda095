package Multicast;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;

public class MCServerOffer {

    public static void main(String args[]) {
        try {
            MulticastSocket multicastSocket = new MulticastSocket(4099);
            InetAddress multicastAdress = InetAddress.getByName("experiment.mcast.net");
            multicastSocket.joinGroup(multicastAdress);

            while(true) {
                String hostName = InetAddress.getLocalHost().getHostName();
                byte[] buffer = new byte[65536];

                DatagramPacket request = new DatagramPacket(buffer,buffer.length);
                DatagramSocket socket = new DatagramSocket();
                multicastSocket.receive(request);
                DatagramPacket response = new DatagramPacket(hostName.getBytes(),hostName.getBytes().length,request.getSocketAddress());
                socket.send(response);

                String s = new String(request.getData(),0,request.getLength());
                System.out.println("Received: " + s);
            }
        } catch(IOException e) {
            System.out.println("Exception:"+e);
        }
    }

}
