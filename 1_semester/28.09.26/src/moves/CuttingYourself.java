package moves;

public class CuttingYourself implements MovingType {

    final double speed = 15;

    @Override
    public Point move(Point currPosition, DirectionVector directionVector) {
        return directionVector.moveOnVector(speed);
    }
}
