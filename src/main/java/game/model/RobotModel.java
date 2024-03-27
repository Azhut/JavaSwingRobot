package game.model;

import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.geom.AffineTransform;
import java.util.Observable;

/**
 * Модель робота
 */
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


    /**
     * Метод, который должен отличаться у каждой загружаемого класса робота
     * @param g объект для работы с графикой
     * @param x координата
     * @param y координата
     * @param direction направление
     */
    public void drawRobot(Graphics2D g, double x, double y, double direction) {
        int robotCenterX = round(x);
        int robotCenterY = round(y);
        AffineTransform t = AffineTransform.getRotateInstance(direction, robotCenterX, robotCenterY);
        g.setTransform(t);
        g.setColor(Color.YELLOW);
        fillOval(g, robotCenterX, robotCenterY, 30, 10);
        g.setColor(Color.BLACK);
        drawOval(g, robotCenterX, robotCenterY, 30, 10);
        g.setColor(Color.WHITE);
        fillOval(g, robotCenterX + 10, robotCenterY, 5, 5);
        g.setColor(Color.BLACK);
        drawOval(g, robotCenterX + 10, robotCenterY, 5, 5);
    }

    private static int round(double value) {
        return (int) (value + 0.5);
    }

    private static void fillOval(Graphics2D g, int centerX, int centerY, int diam1, int diam2) {
        g.fillOval(centerX - diam1 / 2, centerY - diam2 / 2, diam1, diam2);
    }

    private static void drawOval(Graphics2D g, int centerX, int centerY, int diam1, int diam2) {
        g.drawOval(centerX - diam1 / 2, centerY - diam2 / 2, diam1, diam2);
    }
}
