package game.model;

import java.awt.*;
import java.io.Serializable;

public class Player implements Serializable {
    private final RobotModel robot;

    public Player(RobotModel robot) {
        this.robot = robot;
    }

    public void moveRobot(int deltaX, int deltaY) {
        robot.move(deltaX, deltaY);
    }

    public RobotModel getRobot() {
        return robot;
    }

    public Shape getRobotShape() {
        return robot.getRobotShape();
    }
}
