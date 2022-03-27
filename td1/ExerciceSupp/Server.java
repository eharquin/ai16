import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

import static java.lang.Math.floor;

public class Server {

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

    public static void main(String[] args) {
        int nb_max_d = 0; /*nbre d'allumettes maxi au départ*/
        int nb_allu_max = 0; /*nbre d'allumettes maxi que l'on peut tirer au maxi*/
        int qui = 0; /*qui joue? 0=Nous --- 1=PC*/
        int prise = 0; /*nbre d'allumettes prises par le joueur*/
        int nb_allu_rest = 0; /*nbre d'allumettes restantes*/
        try {
            ServerSocket listen = new ServerSocket(10000);
            Socket socket = listen.accept();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            DataInputStream inputStream = new DataInputStream(socket.getInputStream());

            do {
                new Message(
                        MessageType.ASK_INT,
                        "Nombre d'allumettes disposées entre les deux joueurs (entre 10 et 60)"
                ).send(objectOutputStream);
                nb_max_d = inputStream.readInt();
                System.out.println("Paramètrage : nb_max_d = " + nb_max_d);
            } while ((nb_max_d < 10) || (nb_max_d > 60));

            do {
                new Message(
                        MessageType.ASK_INT,
                        "Nombre maximal d'allumettes que l'on peut retirer"
                ).send(objectOutputStream);
                nb_allu_max = inputStream.readInt();
                System.out.println("Paramètrage : nb_allu_max = " + nb_allu_max);
                if (nb_allu_max >= nb_max_d)
                    new Message(
                            MessageType.PRINT,
                            "La quantité prise ne peut pas être supérieure au nombre d'allumettes"
                    ).send(objectOutputStream);
            }
            while ((nb_allu_max >= nb_max_d) || (nb_allu_max == 0));

            /* On répète la demande de prise tant que le nombre donné n'est pas correct */
            do {
                new Message(
                        MessageType.ASK_INT,
                        "Quel joueur commence? 0 -> Joueur ; 1 -> Ordinateur"
                ).send(objectOutputStream);
                qui = inputStream.readInt();
                System.out.println("Paramètrage : qui = " + qui);

                if ((qui != 0) && (qui != 1))
                    System.out.println("Erreur");
            }
            while ((qui != 0) && (qui != 1));
            nb_allu_rest = nb_max_d;
            do {
                System.out.println("Nombre d'allumettes restantes : " + nb_allu_rest);
                new Message(MessageType.PRINT, "Nombre d'allumettes restantes : " + nb_allu_rest).send(objectOutputStream);
                new Message(MessageType.PRINT_ALLU, nb_allu_rest).send(objectOutputStream);

                if (qui == 0) {
                    do {
                        System.out.println("En attente du jeu du joueur...");
                        new Message(MessageType.ASK_INT, "Combien d'allumettes souhaitez-vous piocher").send(objectOutputStream);
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
                    new Message(MessageType.PRINT, "Prise de l'ordi : -" + prise).send(objectOutputStream);
                    System.out.println("Prise de l'ordi : -" + prise);
                }
                qui = (qui + 1) % 2;

                nb_allu_rest = nb_allu_rest - prise;
            }
            while (nb_allu_rest > 0);

            if (qui == 0) {
                new Message(MessageType.PRINT, "Vous avez gagné !").send(objectOutputStream);
                System.out.println("J'ai perdu !");
            } else {
                new Message(MessageType.PRINT, "Vous avez perdu !").send(objectOutputStream);
                System.out.println("J'ai gagné !");
            }
            new Message(
                    MessageType.EXIT,
                    null
            ).send(objectOutputStream);
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}