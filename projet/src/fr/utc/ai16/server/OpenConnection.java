package fr.utc.ai16.server;

import java.io.*;

public class OpenConnection {
    String username;
    final ObjectOutputStream outputStream;

    public OpenConnection(String username, ObjectOutputStream outputStream) throws IOException {
        this.username = username;
        this.outputStream = outputStream;
    }

    public ObjectOutputStream getOutputStream () throws IOException {
        return this.outputStream;
    }
}
