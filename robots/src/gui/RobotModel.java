package gui;
import java.util.Observable;

public class RobotModel extends Observable implements IRobotModel {
    private double positionX;
    private double positionY;
    private double direction;

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
    public double getDirection() {
        return direction;
    }

    @Override
    public void setPosition(double x, double y) {
        this.positionX = x;
        this.positionY = y;
        setChanged();
        notifyObservers();
    }

    @Override
    public void setDirection(double direction) {
        this.direction = direction;
        setChanged();
        notifyObservers();
    }

}
