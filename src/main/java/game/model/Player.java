package game.model;

import java.awt.*;

public class Player {
    private IRobotModel robot;
    private TargetModel robotTarget;
    private Image robotSkin;

    public Player(IRobotModel robot, TargetModel robotTarget, Image robotSkin) {
        this.robot = robot;
        this.robotTarget = robotTarget;
        this.robotSkin = robotSkin;
    }

    public IRobotModel getRobot() {
        return robot;
    }

    public TargetModel getRobotTarget() {
        return robotTarget;
    }

    public Image getRobotSkin() {
        return robotSkin;
    }

    public void setRobotTarget(Point point) {
        this.robotTarget = new TargetModel(point.x, point.y);
    }
}
