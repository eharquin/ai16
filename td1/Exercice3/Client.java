import java.net.*;
import java.io.*;

public class Client
{
    public static void main(String[] args)
    {
        try{
            Socket client = new Socket("localhost", 10080);

            System.out.println("Connexion établie!");

            while(true);

            //client.close();
        }
        catch(IOException e)
        {

        }
    }
}