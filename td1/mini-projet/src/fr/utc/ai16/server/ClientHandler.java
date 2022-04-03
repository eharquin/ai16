package fr.utc.ai16.server;

import fr.utc.ai16.Message;
import fr.utc.ai16.MessageType;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

class ClientHandler extends Thread {
    OpenConnection connection;
    final ArrayList<OpenConnection> clients;
    final ObjectInputStream inputStream;
    final ObjectOutputStream outputStream;
    final Socket socket;

    // Constructor
    public ClientHandler(Socket socket, ArrayList<OpenConnection> clients, ObjectInputStream inputStream, ObjectOutputStream outputStream) {
        this.socket = socket;
        this.clients = clients;
        this.inputStream = inputStream;
        this.outputStream = outputStream;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Message message = (Message) this.inputStream.readObject();
                this.handleMessage(message);
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    public void handleMessage(Message message) throws IOException {
        switch (message.type) {
            case LOGIN:
                if (uniqueUsername(message.username)) {
                    this.connection = new OpenConnection((String) message.username, this.outputStream);
                    this.clients.add(this.connection);
                    this.sendToAll(new Message(MessageType.LOGIN, this.connection.username, null, "*"));
                } else {
                    new Message(MessageType.ERROR, message.username, "Pseudo déjà utilisé", message.username)
                            .send(this.outputStream);
                    this.inputStream.close();
                    this.outputStream.close();
                    this.socket.close();
                }
                break;

            case TEXT:
                if (message.destination.equals("*")) {
                    this.sendToAll(message);
                } else {
                    message.send(this.getClient(message.username).outputStream);
                    message.send(this.getClient(message.destination).outputStream);
                }
                break;

            case LOGOUT:
                this.inputStream.close();
                this.outputStream.close();
                this.socket.close();
                this.clients.remove(this.connection);
                this.sendToAll(new Message(MessageType.LOGOUT, this.connection.username, null, "*"));
                break;
        }
    }

    public void sendToAll(Message message) throws IOException {
        for (int i = 0; i < this.clients.size(); i++) {
            OpenConnection connection = this.clients.get(i);
            System.out.println("Sending message with content " + message.content + " to " + connection.username);
            message.send(connection.getOutputStream());
        }
    }

    public boolean uniqueUsername(String username) {
        OpenConnection connection;
        for (int i = 0; i < this.clients.size(); i++) {
            connection = this.clients.get(i);
            if (username.equals(connection.username)) {
                return false;
            }
        }
        System.out.println("on passe ici");
        return true;
    }

    public OpenConnection getClient(String username) {
        OpenConnection connection;
        for (int i = 0; i < this.clients.size(); i++) {
            connection = this.clients.get(i);
            if (username.equals(connection.username)) {
                return connection;
            }
        }
        return null;
    }
}


