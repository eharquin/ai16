import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import static java.lang.Math.floor;

public class Server {

    public static void main(String[] args) {
        int nb_max_d = 0; /*nbre d'allumettes maxi au départ*/
        int nb_allu_max = 0; /*nbre d'allumettes maxi que l'on peut tirer au maxi*/
        int qui = 0; /*qui joue? 0=Nous --- 1=PC*/
        int prise = 0; /*nbre d'allumettes prises par le joueur*/
        int nb_allu_rest = 0; /*nbre d'allumettes restantes*/
        try {
            ServerSocket listen = new ServerSocket(10000);
            Socket socket = listen.accept();
            DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream());
            DataInputStream inputStream = new DataInputStream(socket.getInputStream());

            do {
                nb_max_d = inputStream.readInt();
                System.out.println("Paramètrage : nb_max_d = " + nb_max_d);
            }
            while ((nb_max_d < 10) || (nb_max_d > 60));
            do {
                nb_allu_max = inputStream.readInt();
                System.out.println("Paramètrage : nb_allu_max = " + nb_allu_max);
                if (nb_allu_max >= nb_max_d)
                    System.out.println("Erreur !");
            }
            while ((nb_allu_max >= nb_max_d) || (nb_allu_max == 0));
            /* On répète la demande de prise tant que le nombre donné n'est pas correct */
            do {
                qui = inputStream.readInt();
                System.out.println("Paramètrage : qui = " + qui);

                if ((qui != 0) && (qui != 1))
                    System.out.println("Erreur");
            }
            while ((qui != 0) && (qui != 1));
            nb_allu_rest = nb_max_d;
            do {
                System.out.println("Nombre d'allumettes restantes : " + nb_allu_rest);
                if (qui == 0) {
                    do {
                        System.out.println("En attente du jeu du joueur...");
                        prise = inputStream.readInt();
                        System.out.println("Prise du joueur : -" + prise);
                        if ((prise > nb_allu_rest) || (prise > nb_allu_max)) {
                            System.out.println("Erreur !");
                        }
                    }
                    while ((prise > nb_allu_rest) || (prise > nb_allu_max));
                    /* On répète la demande de prise tant que le nombre donné n'est pas correct */
                } else {
                    prise = jeu_ordi(nb_allu_rest, nb_allu_max);
                    outputStream.writeInt(prise);
                    System.out.println("Prise de l'ordi : -" + prise);
                }
                qui = (qui + 1) % 2;

                nb_allu_rest = nb_allu_rest - prise;
                outputStream.writeInt(nb_allu_rest);
            }
            while (nb_allu_rest > 0);

            if (qui == 0) /* Cest à nous de jouer */
                System.out.println("J'ai perdu !");
            else
                System.out.println("J'ai gagné !");

            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static int jeu_ordi(int nb_allum, int prise_max) {
        int prise = 0;
        int s = 0;
        float t = 0;
        s = prise_max + 1;
        t = ((float) (nb_allum - s)) / (prise_max + 1);
        while (t != floor(t)) {
            s--;
            t = ((float) (nb_allum - s)) / (prise_max + 1);
        }
        prise = s - 1;
        if (prise == 0)
            prise = 1;
        return (prise);
    }
}