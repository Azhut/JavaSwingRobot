package game.model;

import java.awt.*;
import java.io.Serializable;

public class Player implements Serializable {
    public RobotModel getRobot() {
        return robot;
    }

    private final RobotModel robot;
    private final Shape robotShape;

    public Player(RobotModel robot, Shape robotShape) {
        this.robot = robot;
        this.robotShape = robotShape;
    }

    public Shape getRobotShape() {
        return robotShape;
    }

    public void moveRobot(int deltaX, int deltaY) {
        System.out.println("Moving robot by: " + deltaX + ", " + deltaY);
        double currentX = robot.getPositionX();
        double currentY = robot.getPositionY();
        robot.setPosition(currentX + deltaX, currentY + deltaY);
    }



}
