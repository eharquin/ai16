public class Main
{
    public static void main(String[] args)
    {
        float dist;
        Point2D[] points = new Point2D[3];

        points[0] = new Point2D(1, 1);
        points[1] = new Point2D(1, -3);
        points[2] = new Point2D(-2, 5);

        System.out.println("P0 X = " + points[0].getX());
        System.out.println("P0 Y = " + points[0].getY());
        System.out.println("P0 = " + points[0].toString());

        System.out.println("\nP1 X = " + points[1].getX());
        System.out.println("P1 Y = " + points[1].getY());
        System.out.println("P1 = " + points[1].toString());

        System.out.println("\nP2 X = " + points[2].getX());
        System.out.println("P2 Y = " + points[2].getY());
        System.out.println("P2 = " + points[2].toString());

        dist = Point2D.CalculerDistance(points[0], points[2]);
        System.out.println("\nDistance between P0 and P2 = " + dist);

        System.out.println("\nchange P0 X = 10, Y = 10 ");
        points[0].setX(-10);
        points[0].setY(10);

        System.out.println("\nP0 X = " + points[0].getX());
        System.out.println("P0 Y = " + points[0].getY());
        System.out.println("P0 = " + points[0].toString());

        dist = Point2D.CalculerDistance(points[0], points[2]);
        System.out.println("\nDistance between P0 and P2 = " + dist);
    }
}