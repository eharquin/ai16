public class Main
{
    public static void main(String[] args)
    {
        float dist;
        Point3D[] points = new Point3D[3];

        points[0] = new Point3D(1, 1, 1);
        points[1] = new Point3D(1, -3, 4);
        points[2] = new Point3D(-2, 5, 3);

        System.out.println("P0 X = " + points[0].getX());
        System.out.println("P0 Y = " + points[0].getY());
        System.out.println("P0 Z = " + points[0].getZ());
        System.out.println("P0 = " + points[0].toString());

        System.out.println("\nP1 X = " + points[1].getX());
        System.out.println("P1 Y = " + points[1].getY());
        System.out.println("P1 Z = " + points[1].getZ());
        System.out.println("P1 = " + points[1].toString());

        System.out.println("\nP2 X = " + points[2].getX());
        System.out.println("P2 Y = " + points[2].getY());
        System.out.println("P2 Z = " + points[2].getZ());
        System.out.println("P2 = " + points[2].toString());

        dist = Point3D.CalculerDistance(points[0], points[2]);
        System.out.println("\nDistance between P0 and P2 = " + dist);

        System.out.println("\nchange P0 X = 10, Y = 10, Z = 10");
        points[0].setX(10);
        points[0].setY(10);
        points[0].setZ(10);

        System.out.println("P0 X = " + points[0].getX());
        System.out.println("P0 Y = " + points[0].getY());
        System.out.println("P0 Z = " + points[0].getZ());
        System.out.println("P0 = " + points[0].toString());

        dist = Point3D.CalculerDistance(points[0], points[2]);
        System.out.println("\nDistance between P0 and P2 = " + dist);
    }
}