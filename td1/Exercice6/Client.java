import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client {

    public static void afficher_allu(int n) {
        int i;
        System.out.print("\n");
        for (i = 0; i < n; i++)
            System.out.print("  o");
        System.out.print("\n");
        for (i = 0; i < n; i++)
            System.out.print("  |");
        System.out.print("\n");
        for (i = 0; i < n; i++)
            System.out.print("  |");
        System.out.print("\n");

    }

    public static void main(String[] args) {
        int nb_max_d = 0; /*nbre d'allumettes maxi au départ*/
        int nb_allu_max = 0; /*nbre d'allumettes maxi que l'on peut tirer au maxi*/
        int qui = 0; /*qui joue? 0=Nous --- 1=PC*/
        int prise = 0; /*nbre d'allumettes prises par le joueur*/
        int nb_allu_rest = 0; /*nbre d'allumettes restantes*/

        try {
            Socket conn = new Socket("localhost", 10000);
            DataInputStream inputStream = new DataInputStream(conn.getInputStream());
            DataOutputStream outputStream = new DataOutputStream(conn.getOutputStream());
            Scanner sc = new Scanner(System.in);

            do {
                System.out.println("Nombre d'allumettes disposées entre les deux joueurs (entre 10 et 60) :");
                nb_max_d = sc.nextInt();
                outputStream.writeInt(nb_max_d);
            }
            while ((nb_max_d < 10) || (nb_max_d > 60));
            do {
                System.out.println("Nombre maximal d'allumettes que l'on peut retirer : ");
                nb_allu_max = sc.nextInt();
                outputStream.writeInt(nb_allu_max);
                if (nb_allu_max >= nb_max_d)
                    System.out.println("Erreur !");
            }
            while ((nb_allu_max >= nb_max_d) || (nb_allu_max == 0));
            /* On répète la demande de prise tant que le nombre donné n'est pas correct */
            do {
                System.out.println("Quel joueur commence? 0 -> Joueur ; 1 -> Ordinateur : ");
                qui = sc.nextInt();
                outputStream.writeInt(qui);

                if ((qui != 0) && (qui != 1))
                    System.out.println("Erreur");
            }
            while ((qui != 0) && (qui != 1));
            nb_allu_rest = nb_max_d;
            do {
                System.out.println("Nombre d'allumettes restantes :" + nb_allu_rest);
                afficher_allu(nb_allu_rest);
                if (qui == 0) {
                    do {
                        System.out.println("Combien d'allumettes souhaitez-vous piocher ? ");
                        prise = sc.nextInt();
                        outputStream.writeInt(prise);
                        if ((prise > nb_allu_rest) || (prise > nb_allu_max)) {
                            System.out.println("Erreur !");
                        }
                    }
                    while ((prise > nb_allu_rest) || (prise > nb_allu_max));
                    /* On répète la demande de prise tant que le nombre donné n'est pas correct */
                } else {
                    prise = inputStream.readInt();
                    System.out.println("Prise de l'ordi :" + prise);
                }
                qui = (qui + 1) % 2;

                nb_allu_rest = inputStream.readInt();
            }
            while (nb_allu_rest > 0);

            if (qui == 0) /* C'est à nous de jouer */
                System.out.println("Vous avez gagné!");
            else
                System.out.println("Vous avez perdu!");

            conn.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
