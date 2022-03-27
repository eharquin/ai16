import java.net.*;
import java.io.*;
import java.util.logging.*;
import  java.util.Scanner;


public class ClientSocket
{
    public static void main(String[] args)
    {

        int compteur=0,nbMax;
        byte b[]=new byte[20];

        try
        {
            Socket client = new Socket ("localhost", 10080);

            OutputStream out = client.getOutputStream();
            InputStream in=client.getInputStream();
            
            out.write("bonjour".getBytes());
            
            System.out.println("Donnez le nombre max de messages à envoyer au serveur :");
            
            Scanner sc = new Scanner(System.in);
            nbMax = sc.nextInt();
            
            while(compteur!=nbMax)
            { 
                in.read(b);

                System.out.println("le serveur a dit: "+new String(b));
                System.out.println("Le client envoie le message:message"+compteur);

                out.write(("message"+compteur).getBytes());
                compteur++;
                b=new byte[20];
            }
            out.write(("END").getBytes());
            
            try
            {
                Thread.sleep(20);
            }
            catch (InterruptedException ex)
            {
                Logger.getLogger(ClientSocket.class.getName()).log(Level.SEVERE, null, ex);
            }
            in.close();
            out.close();
            client.close();
        }
        catch (IOException ex)
        {
            Logger.getLogger(ClientSocket.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
