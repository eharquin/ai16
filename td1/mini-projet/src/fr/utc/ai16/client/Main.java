package fr.utc.ai16.client;

import java.io.IOException;
import java.net.Socket;

public class Main {

    public static void main(String[] args) throws IOException {

        Socket conn = new Socket("localhost", 18060);
        MessageSender sender = new MessageSender(conn);
        sender.start();

        MessageReceptor receptor = new MessageReceptor(conn);
        receptor.start();

    }


}
