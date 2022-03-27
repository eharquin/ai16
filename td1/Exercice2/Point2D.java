public class Point2D
{
    private float x;
    private float y;

    public Point2D(float x, float y)
    {
        this.x = x;
        this.y = y;
    }

    public float getX()
    {
        return x;
    }

    public float getY()
    {
        return y;
    }

    public void setX(float x)
    {
        this.x = x;
    }

    public void setY(float y)
    {
        this.y = y;
    }

    public static float CalculerDistance(Point2D a, Point2D b)
    {
        float distX = (float)Math.pow(b.getX() - a.getX(), 2); 
        float distY = (float)Math.pow(b.getY() - a.getY(), 2); 

        float dist = (float)Math.pow(distX + distY, 0.5);

        return dist;
    }
  
    @Override
    public String toString()
    {
        return this.x + " " + this.y;
    }
}