import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client {

    public static void afficher_allu(int n) {
        int i,j;
        System.out.print("\n");
        for (i = 0; i < n; i++) System.out.print("  ()");
        System.out.print("\n");
        for (j = 0; j < 4; j++) {
            for (i = 0; i < n; i++) System.out.print("  ||");
            System.out.print("\n");
        }
    }

    public static void main(String[] args) {
        try {
            Socket socket = new Socket("localhost", 10000);
            DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream());
            ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
            Scanner sc = new Scanner(System.in);

            try {
                do {
                    Message message;
                    message = (Message) objectInputStream.readObject();
                    switch (message.type){
                        case ASK_INT:
                            System.out.println(message.content + " : ");
                            outputStream.writeInt(sc.nextInt());
                            break;
                        case PRINT_ALLU:
                            afficher_allu((int) message.content);
                            break;
                        case PRINT:
                            System.out.println(message.content);
                            break;
                        case ERROR:
                            System.out.println(message.content);
                            socket.close();
                            System.exit(1);
                            break;
                        case EXIT:
                            socket.close();
                            System.exit(0);
                            break;
                    }
                } while (true);
            } catch (ClassNotFoundException e){
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
