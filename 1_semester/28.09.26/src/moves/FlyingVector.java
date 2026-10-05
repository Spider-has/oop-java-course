package moves;

public class FlyingVector implements MovingType {
    final double speed = 5;

    @Override
    public Point move(Point currPossion, DirectionVector directionVector) {
        directionVector.unitV.z = directionVector.unitV.z == 0 ? 1 : directionVector.unitV.z;
        return directionVector.moveOnVector(speed);
    }
}
