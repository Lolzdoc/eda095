package Server;

import java.io.*;
import java.net.Socket;
import java.util.Vector;


public class Server {
    private Vector<Connection> connections;
    private MailBox mailBox = new MailBox();

    public Server() {
        connections = new Vector<>();
        BroadCaster caster = new BroadCaster(mailBox, connections);
        caster.start();
    }

    public void addParticipant(Socket socket) {
        try {
            Connection connection = new Connection(socket, mailBox);
            connections.add(connection);
            connection.start();
        } catch (IOException e) {
            System.out.println("[ERROR] Could not connect to client!");
            e.printStackTrace();
        }
    }


}
