package gui;

public interface IRobotModel {
    double getPositionX();
    double getPositionY();
    double getDirection();
    void setPosition(double x, double y);
    void setDirection(double direction);
}
