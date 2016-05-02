package Server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;


public class ServerSingleThread {

    public static void main(String[] args) {
        Locale format = Locale.ENGLISH;

        try {
            byte[] buffer = new byte[20];
            DatagramSocket socket = new DatagramSocket(30000);
            DatagramPacket packet = new DatagramPacket(buffer,buffer.length);

            String command;
            String result;

            while (true) {
                command = receive(socket,packet).trim();
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
                        result = "Invalid command: " + "\"" + command + "\"";
                        break;
                }
                byte[] responseData = result.getBytes();
                DatagramPacket response = new DatagramPacket(responseData,0,responseData.length,packet.getAddress(),packet.getPort());
               send(socket, response);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String receive(DatagramSocket socket, DatagramPacket packet) throws IOException {
        socket.receive(packet);
        String data = new String(packet.getData());

        System.out.println("Data: " + data);
        System.out.println("Length: " + packet.getLength());
        System.out.println("Source Address: " + packet.getAddress());
        System.out.println("Source Port: " + packet.getPort());
        System.out.println("----------------------------------------------------");
        return data;
    }

    private static void send(DatagramSocket socket, DatagramPacket packet) throws IOException {
        //System.out.println("Sending: " + "\""+result+"\"" + " to: " + client);
        socket.send(packet);
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
