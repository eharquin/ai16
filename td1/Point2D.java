public class Point2D
{
    private double x;
    private double y;

    public Point2D(double x, double y)
    {
        this.x = x;
        this.y = y;
    }

    public double getX()
    {
        return x;
    }

    public double getY()
    {
        return y;
    }

    public double setX(double x)
    {
        this.x = x;
    }

    public double setY(double y)
    {
        this.y = y;
    }

    public static double calculerDistance(Point2D a, Point2D b)
    {
        int distX = Math.pow(b.getX() - a.getX(), 2); 
        int distY = Math.pow(b.getY() - a.getY(), 2); 

        double dist = Math.pow(distX + distY, 0.5);

        return dist;
    }
    
    @Override
    public String toString()
    {
        return this.x + " " + this.y;
    }

}