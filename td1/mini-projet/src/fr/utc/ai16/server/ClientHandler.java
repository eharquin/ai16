package fr.utc.ai16.server;

import fr.utc.ai16.Message;
import fr.utc.ai16.MessageType;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Objects;

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
            } catch (EOFException | SocketException e) {
                if (Objects.isNull(this.connection) || Objects.isNull(this.connection.username)) {
                    System.out.print("Client socket closed");
                } else {
                    System.out.printf("Client socket closed with %s", this.connection.username);
                }
                closeSockets();
                return;
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    public void handleMessage(Message message) throws IOException {
        switch (message.type) {
            case LOGIN:
                if (isUsernameUnique(message.source)) {
                    this.connection = new OpenConnection(message.source, this.outputStream);
                    this.clients.add(this.connection);
                    this.sendToAll(new Message(MessageType.LOGIN, this.connection.username, "*", null));
                } else {
                    new Message(MessageType.ERROR, message.source, message.source, "Pseudo déjà utilisé")
                            .send(this.outputStream);
                    closeSockets();
                }
                break;

            case TEXT:
                if (message.destination.equals("*")) {
                    this.sendToAll(message);
                } else {
                    message.send(this.getClient(message.source).outputStream);
                    message.send(this.getClient(message.destination).outputStream);
                }
                break;

            case LOGOUT:
                closeSockets();
                this.sendToAll(new Message(MessageType.LOGOUT, this.connection.username, "*", null));
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

    public boolean isUsernameUnique(String username) {
        OpenConnection connection;
        for (int i = 0; i < this.clients.size(); i++) {
            connection = this.clients.get(i);
            if (username.equals(connection.username)) {
                return false;
            }
        }
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

    public void closeSockets() {
        this.clients.remove(this.connection);
        try {
            this.inputStream.close();
            this.outputStream.close();
            this.socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


