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

            String pseudo = SendLoginInformation(out, sc);

            boolean exit = true;
            //Read and send loop
            while (exit) {
                Message m = null;
                String text = sc.nextLine();
                if (text.equals("quit")) {
                    m = new Message(MessageType.LOGOUT, pseudo, null ,"*");
                    m.send(out);
                    exit = false;
                }
                else if (text.substring(0,1).equals("#"))
                {
                    int index = 0;
                    for (int i = 1;i < text.length();i++)
                    {
                        if (text.substring(i,i+1).equals("#"))
                        {
                           index = i;
                           break;
                        }
                    }
                    String destinataire;
                    if (index != 0)
                    {
                        destinataire = text.substring(1,index);
                        m = new Message(MessageType.TEXT_PRIVATE,pseudo,text,destinataire);
                    }
                    else
                    {
                        //à voir comment gérer ce cas là
                        //m = new Message(MessageType.TEXT_PRIVATE,pseudo,text,destinataire);
                    }
                    destinataire = text.substring(1,index);
                    text = text.substring(index+1,text.length());
                    m = new Message(MessageType.TEXT_PRIVATE,pseudo,text,destinataire);
                    m.send(out);
                }
                else {
                    m = new Message(MessageType.TEXT, pseudo, text , "*");
                    m.send(out);
                }
            }


        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public String SendLoginInformation(ObjectOutputStream out, Scanner sc) throws IOException {
        System.out.println("Entrez votre pseudo:");
        String pseudo = sc.nextLine();

        Message loginMessage = new Message(MessageType.LOGIN, pseudo, null ,"*");
        loginMessage.send(out);
        return pseudo;
    }
}
