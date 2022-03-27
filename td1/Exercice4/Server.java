import java.net.*;
import java.io.*;

public class Server
{

    public static void main(String[] args)
    {
        try
        {
            ServerSocket server = new ServerSocket(10080);
            while(true)
            {
                Socket soc = server.accept();
                System.out.println("Nouvelle connexion!");

                InputStream in = soc.getInputStream();
                byte[] b = new byte[1024];
                in.read(b);
                System.out.println("Message reçu : " + new String(b));

                OutputStream out = soc.getOutputStream();
                out.write("Hello Client!".getBytes());
            }

            //server.close();
        }
        catch(IOException e)
        {

        }
    }
}