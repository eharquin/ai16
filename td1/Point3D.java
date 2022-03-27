public class Point3D extends Point2D
{
    private double z;

    public Point3D(double x, double y, double z)
    {
        super(x, y);
        this.z = z;
    }

    public double getZ()
    {
        return z;
    }

    public double setZ(double z)
    {
        this.z = z;
    }

    @Override
    public static double calculerDistance(Point3D a, Point3D b)
    {
        int distX = Math.pow(b.getX() - a.getX(), 2); 
        int distY = Math.pow(b.getY() - a.getY(), 2); 
        int distY = Math.pow(b.getZ() - a.getZ(), 2); 


        double dist = Math.pow(distX + distY + distZ, 0.5);

        return dist;
    }
    
    @Override
    public String toString()
    {
        return super.toString + " " + this.z;
    }
}