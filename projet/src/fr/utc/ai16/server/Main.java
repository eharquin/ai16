package fr.utc.ai16.server;

import java.lang.Object;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {

    public static void main(String[] args) throws IOException
    {
        ServerSocket serverSocket = new ServerSocket(5056);
        ArrayList Clients = new ArrayList(OpenConnection);




        while (true)
        {
            Socket socket = null;

            try
            {
                socket = serverSocket.accept();

                System.out.println("A new client is connected : " + socket);

                ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
                ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());

                Clients

                Thread t = new ClientHandler(Clients, socket, inputStream, outputStream);

                t.start();



                Clients.add(outputStream);



            }
            catch (Exception e){
                socket.close();
                e.printStackTrace();
            }
        }
    }

}
