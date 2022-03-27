public class Point3D extends Point2D
{
    private float z;

    public Point3D(float x, float y, float z)
    {
        super(x, y);
        this.z = z;
    }

    public float getZ()
    {
        return z;
    }

    public void setZ(float z)
    {
        this.z = z;
    }

    public static float CalculerDistance(Point3D a, Point3D b)
    {
        float distX = (float)Math.pow(b.getX() - a.getX(), 2); 
        float distY = (float)Math.pow(b.getY() - a.getY(), 2); 
        float distZ = (float)Math.pow(b.getZ() - a.getZ(), 2);

        float dist = (float)Math.pow(distX + distY + distZ, 0.5);

        return dist;
    }
    
    @Override
    public String toString()
    {
        return super.toString() + " " + this.z;
    }
}