package fr.utc.ai16.client;

public class Main {

    public void main(String[] args){

        Socket conn = new Socket("localhost", 18060);
        MessageReceptor receptor = new MessageReceptor(conn);
        MessageSender sender = new MessageSender(conn);

        receptor.start();
        sender.start();

    }


}
