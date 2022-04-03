package fr.utc.ai16.client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.lang.Thread;
import java.net.Socket;

import fr.utc.ai16.Message;

public class MessageReceptor extends Thread {

    private final Socket client;

    public MessageReceptor(Socket client) {

        this.client = client;
    }


    @Override
    public void run() {
        try {
            ObjectInputStream in = new ObjectInputStream(client.getInputStream());
            Message m;
            boolean exit = true;

            while (exit) {
                m = (Message) in.readObject();
                switch (m.type) {
                    case LOGIN:
                        System.out.println("\r" + m.source + " a rejoint la conversation\n--------------------------");
                        break;

                    case TEXT:
                        if (m.destination.equals("*")) {
                            System.out.printf("\r[%s] %s\n", m.source, m.content);
                        } else {
                            System.out.printf("\r[%s -> %s] %s\n", m.source, m.destination, m.content);
                        }
                        break;

                    case LOGOUT:
                        System.out.println("\r" + m.source + " a quitté la conversation\n--------------------------");
                        break;
                    case ERROR:
                        System.out.println("\u001B[31m" + m.content + "\u001B[0m");
                        System.exit(1);
                }

            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


}