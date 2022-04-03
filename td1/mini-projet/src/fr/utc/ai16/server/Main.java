package fr.utc.ai16.server;

import java.lang.Object;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Main {
    static ArrayList<OpenConnection> clients = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        while (true) {
            ServerSocket serverSocket = new ServerSocket(18060);
            while (true) {
                Socket socket = null;

                try {
                    socket = serverSocket.accept();

                    System.out.println("A new client is connected : " + socket);

                    ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
                    ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());

                    Thread t = new ClientHandler(socket, clients, inputStream, outputStream);

                    t.start();


                } catch (Exception e) {
                    socket.close();
                    e.printStackTrace();
                }
            }
        }

    }

}
