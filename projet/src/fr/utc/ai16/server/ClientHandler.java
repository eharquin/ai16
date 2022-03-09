package fr.utc.ai16.server;

import fr.utc.ai16.Message;
import fr.utc.ai16.MessageType;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Date;

class ClientHandler extends Thread
{
    String name;
    OpenConnection connection;
    final ArrayList<OpenConnection> clients;
    final ObjectInputStream inputStream;
    final ObjectOutputStream outputStream;
    final Socket socket;


    // Constructor
    public ClientHandler(Socket socket, ArrayList<OpenConnection> clients, ObjectInputStream inputStream, ObjectOutputStream outputStream)
    {
        this.socket = socket;
        this.clients = clients;
        this.inputStream = inputStream;
        this.outputStream = outputStream;
    }

    @Override
    public void run()
    {
        while (true)
        {
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
                this.name = (String) message.content;
                this.connection = new OpenConnection((String) message.content, this.socket);
                this.clients.add(this.connection);
                this.sendToAll(new Message(MessageType.LOGIN, message.content));
                break;
            case TEXT:
                this.sendToAll(new Message(MessageType.TEXT, message.content));
                System.out.println("Client sent : " + message.content);
                break;
            case LOGOUT:
                this.inputStream.close();
                this.outputStream.close();
                this.socket.close();
                this.clients.remove(this.connection);
                this.sendToAll(new Message(MessageType.LOGOUT, message.content));
                break;
        }
    }

    public void sendToAll(Message message) throws IOException {
        for (int i = 0; i< this.clients.size(); i++){
            OpenConnection connection = this.clients.get(i);
            ObjectOutputStream foreignOutputStream = new ObjectOutputStream(connection.socket.getOutputStream());
            (new Message(MessageType.TEXT, message.content)).send(foreignOutputStream);
        }
    }
}
