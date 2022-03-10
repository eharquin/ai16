package fr.utc.ai16.client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.lang.Thread;
import java.net.Socket;
import fr.utc.ai16.Message;

public class MessageReceptor extends Thread {

    private Socket client;

    public MessageReceptor(Socket client) {
        this.client = client;
    }

    @Override
    public void run() {
        try {
            ObjectInputStream in = new ObjectInputStream(client.getInputStream());
            Message m = null;

            while (true) {
                m = (Message) in.readObject();
                switch (m.type) {
                    case LOGIN:
                        System.out.println("\r" + m.username + " a rejoint la conversation\n--------------------------");
                        break;

                    case TEXT:
                        System.out.println("\r" + m.username + " a dit : " + m.content + "\n");
                        break;

                    case TEXT_PRIVATE:
                        System.out.println("\r" + m.username + " vous a dit :" + m.content + "\n");

                    case LOGOUT:
                        System.out.println("\r" + m.username + " a quitté la conversation\n--------------------------");
                        break;
                }

            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


}