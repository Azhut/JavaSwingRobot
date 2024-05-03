package game.model;

import java.awt.*;
import java.io.Serializable;

public class Player implements Serializable {
    private IRobotModel robot;
    private TargetModel robotTarget;
    private Shape robotShape;


    public Player() {

    }


    public Player(IRobotModel robot, TargetModel robotTarget, Shape robotShape) {
        this.robot = robot;
        this.robotTarget = robotTarget;
        this.robotShape = robotShape;
    }


    public Shape getRobotShape() {
        return robotShape;
    }

    public IRobotModel getRobot() {
        return robot;
    }

    public TargetModel getRobotTarget() {
        return robotTarget;
    }

    public void setRobotTarget(Point point) {
        this.robotTarget = new TargetModel(point.x, point.y);
    }
}
