package Server.Final;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Vector;


public class ChatSession {
    private Vector <OutputStreamWriter> outputs;
    private final  MailBox mailBox = new MailBox();
    public ChatSession(){
        outputs = new Vector<>();
        BroadCaster caster = new BroadCaster(mailBox, outputs);
        caster.start();
    }
    public void addParticipant(Socket socket) {
        try {
            ConnectionService service = new ConnectionService(socket.getInputStream(), mailBox);
            outputs.add(new OutputStreamWriter(socket.getOutputStream()));
            service.start();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
