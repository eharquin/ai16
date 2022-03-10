package fr.utc.ai16;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class Message implements Serializable {

    public MessageType type;
    public String username;
    public Object content;
    public String destination;

    public Message(MessageType type, String username, Object content,String destination) {
        this.type = type;
        this.username = username;
        this.content = content;
        this.destination = destination;
    }

    public void send(ObjectOutputStream stream) throws IOException {
        stream.writeObject(this);
    }
}