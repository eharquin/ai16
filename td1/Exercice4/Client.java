import java.net.*;
import java.io.*;

public class Client
{
    public static void main(String[] args)
    {
        try{
            Socket client = new Socket("localhost", 10080);

            System.out.println("Connexion établie!");

            OutputStream out = client.getOutputStream();
            out.write("Hello Server!".getBytes());

            InputStream in = client.getInputStream();
            byte[] b = new byte[1024];
            in.read(b);
            System.out.println("Message reçu : " + new String(b));

            while(true);


            //client.close();
        }
        catch(IOException e)
        {

        }
    }
}