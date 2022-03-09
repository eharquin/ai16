package fr.utc.ai16.client;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.lang.Thread;
import java.net.Socket;
import java.util.Scanner;

import fr.utc.ai16.Message;
import fr.utc.ai16.MessageType;

public class MessageSender extends Thread {

    private Socket client;

    public MessageSender(Socket client) {
        this.client = client;
    }

    @Override
    public void run() {
        try {
            ObjectOutputStream out = new ObjectOutputStream(client.getOutputStream());
            Scanner sc = new Scanner(System.in);

            SendLoginInformation(out, sc);

            boolean exit = true;
            //Read and send loop
            while (exit) {
                Message m = null;
                String text = sc.nextLine();
                if (text == "quit") {
                    m = new Message(MessageType.LOGOUT, null);
                    m.send(out);
                    exit = false;
                } else {
                    m = new Message(MessageType.TEXT, text);
                    m.send(out);
                }
            }


        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void SendLoginInformation(ObjectOutputStream out, Scanner sc) throws IOException {
        System.out.println("Entrez votre pseudo:");
        String pseudo = sc.nextLine();

        Message loginMessage = new Message(MessageType.LOGIN, pseudo);
        loginMessage.send(out);
    }
}
