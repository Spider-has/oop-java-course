package moves;

public class Point {
    double x, y, z;

    public Point(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public double len() {
        return Math.sqrt(x * x + y * y + z * z);
    }

    public void move(Point vec) {
        x += vec.x;
        y += vec.y;
        z += vec.z;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getZ() {
        return z;
    }
}
