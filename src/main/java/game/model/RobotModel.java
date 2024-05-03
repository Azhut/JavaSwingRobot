package game.model;

import java.awt.*;
import java.io.Serializable;
import java.util.Observable;

public class RobotModel extends Observable implements Serializable, IRobotModel {
    private final Shape robotShape;
    private double positionX;
    private double positionY;

    public RobotModel(Shape robotShape) {
        this.robotShape = robotShape;
        this.positionX = 100;
        this.positionY = 100;
    }

    public double getPositionX() {
        return positionX;
    }

    public double getPositionY() {
        return positionY;
    }

    public Shape getRobotShape() {
        return robotShape;
    }

    public void move(int deltaX, int deltaY) {
        setPosition(positionX + deltaX, positionY + deltaY);
    }

    @Override
    public void setPosition(double x, double y) {
        this.positionX = x;
        this.positionY = y;
        setChanged();
        notifyObservers();
    }
}
