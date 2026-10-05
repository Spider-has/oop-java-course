package moves;

public class Walking implements MovingType {
    final int speed = 1;

    @Override
    public Point move(Point currPossion, DirectionVector directionVector) {
        directionVector.unitV.z = 0;
        currPossion.move(directionVector.moveOnVector(speed));
        return currPossion;
    }
}
