package moves;

public class DirectionVector {
    Point vector;

    Point unitV;

    public DirectionVector(Point vec) {
        this.vector = vec;
        this.normalize();
    }

    public Point moveOnVector(double speed) {
        if (unitV == null) {
            return new Point(0, 0, 0);
        }
        return new Point(unitV.x * speed, unitV.y * speed, unitV.z * speed);
    }

    private void normalize() {
        double len = vector.len();
        if (len == 0) {
            unitV = new Point(0.0, 0.0, 0.0);

        } else {
            unitV = new Point(this.vector.x / len, this.vector.y / len, this.vector.z / len);
        }
    }
}
