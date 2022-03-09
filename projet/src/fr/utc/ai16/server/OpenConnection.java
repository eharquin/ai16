package fr.utc.ai16.server;

import java.io.*;
import java.net.Socket;

public class OpenConnection {
    String username;
    final OutputStream outputStream;
    final ObjectOutputStream objectOutputStream;

    public OpenConnection(String username, OutputStream outputStream) throws IOException {
        this.username = username;
        this.outputStream = outputStream;
        this.objectOutputStream = new ObjectOutputStream(this.outputStream);
    }

    public OutputStream getOutputStream () throws IOException {
        return this.outputStream;
    }

    public ObjectOutputStream getObjectOutputStream () throws IOException {
        return this.objectOutputStream;
    }
}
