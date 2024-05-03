
package game.model;


import java.awt.Point;
import java.io.Serializable;

public class TargetModel implements Serializable {
    private int x;
    private int y;

    public TargetModel(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Point getPosition() {
        return new Point(x, y);
    }
}
