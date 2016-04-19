package Server;

import Server.prototype.MailBox;

import java.util.Vector;

/**
 * Created by hansr on 2016-04-19.
 */
public class ChatSession {
    private MailBox box;
    private Vector <ConnectionService> participants;
    public ChatSession(MailBox box){
        participants = new Vector<>();
        this.box = box;
    }


}
