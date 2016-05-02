package Server;


import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;

class NameBroadcast implements Runnable {


    @Override
    public void run() {
        try {
            MulticastSocket ms = new MulticastSocket(4099);
            InetAddress ia = InetAddress.getByName("experiment.mcast.net");
            ms.joinGroup(ia);

            while(true) {
                byte[] buf = new byte[65536];
                DatagramPacket dp = new DatagramPacket(buf,buf.length);
                DatagramSocket socket = new DatagramSocket();
                ms.receive(dp);
                String hostName = InetAddress.getLocalHost().getHostName();
                System.out.println("hostName = " + hostName);
                DatagramPacket response =
                        new DatagramPacket(hostName.getBytes(),
                                hostName.getBytes().length,dp.getAddress(),dp.getPort());
                socket.send(response);

                String s = new String(dp.getData(),0,dp.getLength());
                System.out.println("Received: " + s);
            }
        } catch(IOException e) {
            System.out.println("Exception:" + e);
        }
    }
}
