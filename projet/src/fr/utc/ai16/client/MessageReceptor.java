package fr.utc.ai16.client;

import java.lang.Thread;
import fr.utc.ai16.Message;
import fr.utc.ai16.MessageType;

public class MessageReceptor extends Thread{

    private Socket client;

    public MessageReceptor(Socket client){
        this.client = client;
    }

    @Override
    public void run(){
        try{
            ObjectInputstream in =  new ObjectInputStream(client.getInputStream());
            Message m = null;

            while(true){
                m = (Message)in.readObject();
                switch (m.type){
                    String content = (String)m.content;
                    case LOGIN:
                        System.out.printl("\r" + content + "a rejoint la conversation\n--------------------------");
                        break;

                    case TEXT:
                        System.out.printl("\r" + content + "\n");
                        break;

                    case LOGOUT:
                        System.out.printl("\r" + content + "a quitté la conversation\n--------------------------");
                        break;
                }

            }
        } catch(IOException e){
            System.out.println(e.getMessage());
        }
    }


    


}