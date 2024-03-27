package game.model;

import java.awt.*;

public interface IRobotModel {
    double getPositionX();

    double getPositionY();

    double getDirection();

    void setPosition(double x, double y);

    void setDirection(double direction);

    void drawRobot(Graphics2D g, double x, double y, double direction);

}
