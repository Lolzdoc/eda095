package Server.Final;

import java.io.*;
import java.net.Socket;
import java.util.Vector;


public class Server {
    private Vector<PrintWriter> outputs;
    private final  MailBox mailBox = new MailBox();

    public Server() {
        outputs = new Vector<>();
        BroadCaster caster = new BroadCaster(mailBox, outputs);
        caster.start();
    }

    public void addParticipant(Socket socket) {
        try {
            InputStream in = socket.getInputStream();
            OutputStream out = socket.getOutputStream();
            ConnectionService service = new ConnectionService(in, mailBox);

            outputs.add(new PrintWriter(out));
            service.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
