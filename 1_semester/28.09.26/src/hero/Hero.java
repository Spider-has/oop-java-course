package hero;

import moves.DirectionVector;
import moves.MovingType;
import moves.Point;

public class Hero {
    private Point position;
    private DirectionVector dir;
    MovingType moving;

    public Hero(Point pos, MovingType moving) {
        this.position = pos;
        this.moving = moving;
        this.dir = new DirectionVector(new Point(0, 0, 0));
    }

    public void setMoving(MovingType moving) {
        this.moving = moving;
    }

    public void setDirection(DirectionVector dir) {
        this.dir = dir;
    }

    public void move() {
        position = moving.move(position, dir);
    }

    public Point position() {
        return position;
    }
}
