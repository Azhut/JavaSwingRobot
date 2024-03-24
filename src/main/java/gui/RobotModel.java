package gui;

import java.util.Observable;

public class RobotModel extends Observable {
    private double positionX;
    private double positionY;

    private double direction;

    public RobotModel() {
        this.positionX = 100;
        this.positionY = 100;
    }

    public double getPositionX() {
        return positionX;
    }

    public double getPositionY() {
        return positionY;
    }

    public double getDirection() {
        return direction;
    }

    public void setPosition(double x, double y) {
        this.positionX = x;
        this.positionY = y;

        setChanged();
        notifyObservers();
    }
    public void setDirection(double direction) {
        this.direction = direction;

        setChanged();
        notifyObservers();
    }
}
