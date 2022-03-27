import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class Message implements Serializable {

    public MessageType type;
    public Object content;

    public Message(MessageType type, Object content) {
        this.type = type;
        this.content = content;
    }

    public void send(ObjectOutputStream stream) throws IOException {
        stream.writeObject(this);
    }
}
