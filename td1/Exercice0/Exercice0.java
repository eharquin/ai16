import java.util.Scanner;
import static java.lang.Math.pow;

public class Exercice0
{
    public static void main(String[] args) 
    {
        int[] tab = new int[5];
        Scanner sc = new Scanner(System.in);

        for(int i = 0; i < 5; i++)
        {
            System.out.println("Entrez le nombre "+ (i+1) +":");
            tab[i] = sc.nextInt();
        }

        System.out.println("\n\nmin : " + min(tab));
        System.out.println("max : " + max(tab));
        System.out.println("\nmoyenne : " + moyenne(tab));
        System.out.println("ecart-type : " + ecart(tab));
    }

    public static int min(int[] tab)
    {
        if(tab == null)
        {
            return 0;
        }

        int min = tab[0];
        for(int i = 1; i < tab.length; i++)
        {
            if(tab[i] < min)
            {
                min = tab[i];
            }
        }

        return min;
    }

    public static int max(int[] tab)
    {
        if(tab == null)
        {
            return 0;
        }

        int max = tab[0];
        for(int i = 1; i < tab.length; i++)
        {
            if(tab[i] > max)
            {
                max = tab[i];
            }
        }

        return max;
    }

    public static double moyenne(int tab[])
    {
        if(tab == null)
        {
            return 0.0;
        }

        int somme = 0;
        for(int i = 0; i < tab.length; i++)
        {
            somme += tab[i];
        }

        return somme / tab.length;
    }

    public static double ecart(int tab[])
    {
        if(tab == null)
        {
            return 0.0;
        }

        double variance = 0;
        double t = 0.0;
        double moyenne = moyenne(tab);
        for(int i = 0; i < tab.length; i++)
        {
            t += (tab[i] - moyenne)*(tab[i] - moyenne);
        }
        variance = t/(tab.length);



        return Math.pow(variance, 0.5);
    }

}