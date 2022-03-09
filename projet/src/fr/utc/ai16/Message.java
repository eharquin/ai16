package fr.utc.ai16;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class Message implements Serializable {

    public MessageType type;
    public String username;
    public Object content;

    public Message(MessageType type, String username, Object content) {
        this.type = type;
        this.username = username;
        this.content = content;
    }

    public void send(ObjectOutputStream stream) throws IOException {
        stream.writeObject(this);
    }
}