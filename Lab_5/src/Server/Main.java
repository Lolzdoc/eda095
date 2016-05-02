package Server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.nio.charset.StandardCharsets;
import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Locale format = Locale.ENGLISH;

        try {
            byte[] buffer = new byte[20];
            DatagramSocket socket = new DatagramSocket(30000);
            DatagramPacket packet = new DatagramPacket(buffer,50);

            String command = "";
            InetAddress client = packet.getAddress();
            String result;

            while (true) {
                command = receive(socket,packet);
                switch (command) {
                    case "t":
                        result = currentTime(format);
                        break;
                    case "d":
                        result = currentDate(format);
                        break;
                    case "dt":
                        result = currentDateTime(format);
                        break;
                    default:
                        result = "Invalid command: "+ command;
                        break;
                }
               // send(client, result);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String receive(DatagramSocket socket, DatagramPacket packet) throws IOException {
        socket.receive(packet);
        String data = new String(packet.getData(), StandardCharsets.UTF_8);
        System.out.println("Data: " + data);
        System.out.println("Length: " + packet.getLength());
        System.out.println("Source Address: " + packet.getAddress());
        System.out.println("Source Port: " + packet.getPort());
        return data;
    }

    private static void send(DatagramSocket socket, DatagramPacket packet) {
        //System.out.println("Sending: " + "\""+result+"\"" + " to: " + client);
    }

    private static String currentDate(Locale format) {
        Date currentDate = new Date();
        DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.LONG, format);

        return dateFormat.format(currentDate);
    }

    private static String currentTime(Locale format) {
        Date currentDate = new Date();
        DateFormat dateFormat = DateFormat.getTimeInstance(DateFormat.LONG, format);

        return dateFormat.format(currentDate);
    }

    private static String currentDateTime(Locale format) {
        Date currentDate = new Date();
        DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, format);

        return dateFormat.format(currentDate);
    }

}
