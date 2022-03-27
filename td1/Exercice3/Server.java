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
            }

            //server.close();
        }
        catch(IOException e)
        {

        }
    }
}