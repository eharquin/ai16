package fr.utc.ai16.server;

import fr.utc.ai16.Message;
import fr.utc.ai16.MessageType;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

class ClientHandler extends Thread
{
    OpenConnection connection;
    final ArrayList<OpenConnection> clients;
    final ObjectInputStream inputStream;
    final ObjectOutputStream outputStream;
    final Socket socket;


    public void usernameClient()
    {

    }
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
                this.connection = new OpenConnection((String) message.username, this.outputStream);
                this.clients.add(this.connection);
                this.sendToAll(new Message(MessageType.LOGIN, this.connection.username, null,"*"));
                break;

            case TEXT_PRIVATE:
                OpenConnection conn = GetClient(message);
                if(conn != null) {
                    message.send(conn.outputStream);
                }
                break;

            case TEXT:
                this.sendToAll(new Message(MessageType.TEXT, this.connection.username, message.content, "*"));
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
        for (int i = 0; i< this.clients.size(); i++){
            OpenConnection connection = this.clients.get(i);
            System.out.println("Sending message with content " + message.content + " to " + connection.username);
            message.send(connection.getOutputStream());
        }
    }

    public OpenConnection GetClient(Message message){
        OpenConnection connection;
        for (int i = 0; i < this.clients.size(); i++) {
            connection = this.clients.get(i);
            if (message.destination.equals(connection.username)) {
                return connection;
            }
        }
        return null;
    }
}


