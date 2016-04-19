package EchoServer;


import Coordination.MailBox;

public class Participant {
    private String name;
    private MailBox mailBox;
    private ConnectionService service;


    public Participant(String name, MailBox box,ConnectionService service){
        this.name = name;
        this.mailBox = box;
        this.service = service;
    }
}
