package game.controller;

import game.model.Game;
import game.model.IRobotModel;
import game.model.RobotModel;
import game.model.TargetModel;

import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class GameController extends MouseAdapter {
    private final Game game;

    private static final double MAX_VELOCITY = 0.1;
    private static final double MAX_ANGULAR_VELOCITY = 0.001;

    public GameController(Game game) {
        this.game = game;

        Timer timer = new Timer("events generator", true);
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                onModelUpdateEvent();
            }
        }, 0, 10);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        setTargetPosition(e.getPoint());
    }

    public void setTargetPosition(Point p) {
        List<IRobotModel> robots = game.getRobots();
        for (IRobotModel robot : robots) {
            game.setRobotTarget(robot, p);
        }
    }

    public void onModelUpdateEvent() {
        List<IRobotModel> robots = game.getRobots();
        for (IRobotModel robotModel : robots) {
            TargetModel target = game.getRobotTarget(robotModel);
            if (target == null) {
                continue;
            }
            double distance = distance(target.getX(), target.getY(), robotModel.getPositionX(), robotModel.getPositionY());
            if (distance < 0.5) {
                continue;
            }
            double velocity = MAX_VELOCITY;
            double angleToTarget = angleTo(robotModel.getPositionX(), robotModel.getPositionY(), target.getX(), target.getY());
            double angularVelocity = 0;

            if (robotModel.getDirection() - angleToTarget > Math.PI) {
                robotModel.setDirection(robotModel.getDirection() - 2 * Math.PI);
            } else if (robotModel.getDirection() - angleToTarget < Math.PI) {
                robotModel.setDirection(robotModel.getDirection() + 2 * Math.PI);
            }

            double deltaAngle = angleToTarget - robotModel.getDirection();
            if (deltaAngle > Math.PI) {
                deltaAngle -= 2 * Math.PI;
            } else if (deltaAngle < -Math.PI) {
                deltaAngle += 2 * Math.PI;
            }

            angularVelocity = Math.signum(deltaAngle) * MAX_ANGULAR_VELOCITY;

            moveRobot(robotModel, velocity, angularVelocity, 10);
        }
    }

    private static double distance(double x1, double y1, double x2, double y2) {
        double diffX = x1 - x2;
        double diffY = y1 - y2;
        return Math.sqrt(diffX * diffX + diffY * diffY);
    }

    private static double angleTo(double fromX, double fromY, double toX, double toY) {
        double diffX = toX - fromX;
        double diffY = toY - fromY;
        return asNormalizedRadians(Math.atan2(diffY, diffX));
    }

    private static double applyLimits(double value, double min, double max) {
        if (value < min)
            return min;
        if (value > max)
            return max;
        return value;
    }

    private void moveRobot(IRobotModel robotModel, double velocity, double angularVelocity, double duration) {
        velocity = applyLimits(velocity, 0, MAX_VELOCITY);
        angularVelocity = applyLimits(angularVelocity, -MAX_ANGULAR_VELOCITY, MAX_ANGULAR_VELOCITY);
        double currentX = robotModel.getPositionX();
        double currentY = robotModel.getPositionY();
        double currentDirection = robotModel.getDirection();

        double newX = currentX + velocity / angularVelocity *
                (Math.sin(currentDirection + angularVelocity * duration) -
                        Math.sin(currentDirection));
        if (!Double.isFinite(newX)) {
            newX = currentX + velocity * duration * Math.cos(currentDirection);
        }
        double newY = currentY - velocity / angularVelocity *
                (Math.cos(currentDirection + angularVelocity * duration) -
                        Math.cos(currentDirection));
        if (!Double.isFinite(newY)) {
            newY = currentY + velocity * duration * Math.sin(currentDirection);
        }
        robotModel.setPosition(newX, newY);

        double newDirection = asNormalizedRadians(currentDirection + angularVelocity * duration);
        robotModel.setDirection(newDirection);
    }

    private static double asNormalizedRadians(double angle) {
        while (angle < 0) {
            angle += 2 * Math.PI;
        }
        while (angle >= 2 * Math.PI) {
            angle -= 2 * Math.PI;
        }
        return angle;
    }

    public void setRobots(List<IRobotModel> robots) {
        game.setRobots(robots);
    }

    public void setRobotTarget(IRobotModel robot, TargetModel target) {
        Point point=new Point(target.getX(), target.getY());
        game.setRobotTarget(robot, point);
    }
}
