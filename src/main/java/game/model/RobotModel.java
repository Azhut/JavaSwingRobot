package game.model;

import java.io.Serializable;
import java.util.Observable;

/**
 * Модель робота
 */
public class RobotModel extends Observable implements IRobotModel, Serializable {
    private double positionX;
    private double positionY;
    private double direction;

    public RobotModel() {
        this.positionX = 400;
        this.positionY = 400;
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
    public double getDirection() {
        return direction;
    }

    @Override
    public void setDirection(double direction) {
        this.direction = direction;
        setChanged();
        notifyObservers();
    }

    @Override
    public void setPosition(double x, double y) {
        this.positionX = x;
        this.positionY = y;
        setChanged();
        notifyObservers();
    }


}
