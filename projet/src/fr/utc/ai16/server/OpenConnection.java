package fr.utc.ai16.server;

import java.io.*;
import java.net.Socket;

public class OpenConnection {
    String username;
    Socket socket;

    public OpenConnection(String username, Socket socket) {
        this.username = username;
        this.socket = socket;
    }

    public InputStream inputStream () throws IOException {
        return this.socket.getInputStream();
    }

    public ObjectInputStream objectInputStream () throws IOException {
        return new ObjectInputStream(this.inputStream());
    }

    public OutputStream outputStream () throws IOException {
        return this.socket.getOutputStream();
    }

    public ObjectOutputStream objectOutputStream () throws IOException {
        return new ObjectOutputStream(this.outputStream());
    }
}
