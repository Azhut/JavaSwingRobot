package game.model;

import java.io.Serializable;
import java.util.Observable;

/**
 * Модель робота
 */
public class RobotModel extends Observable implements IRobotModel, Serializable {
    private double positionX;
    private double positionY;

    public RobotModel() {
        this.positionX = 100;
        this.positionY = 100;
    }

    @Override
    public double getPositionX() {
        return positionX;
    }

    @Override
    public double getPositionY() {
        return positionY;
    }

    @Override
    public void setPosition(double x, double y) {
        this.positionX = x;
        this.positionY = y;
        setChanged();
        notifyObservers();
    }

}
