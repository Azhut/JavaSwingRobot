package game.controller;

import game.model.Game;
import game.model.IRobotModel;
import game.model.Player;
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
        List<Player> players = game.getPlayers();
        for (Player player : players) {
            player.setRobotTarget(p);
        }
    }

    public void onModelUpdateEvent() {
        List<Player> players = game.getPlayers();
        for (Player player : players) {
            IRobotModel robot = player.getRobot();
            TargetModel target = player.getRobotTarget();
            if (target == null) {
                continue;
            }
            double distance = distance(target.getX(), target.getY(), robot.getPositionX(), robot.getPositionY());
            if (distance < 0.5) {
                continue;
            }
            double velocity = MAX_VELOCITY;
            double angleToTarget = angleTo(robot.getPositionX(), robot.getPositionY(), target.getX(), target.getY());
            double angularVelocity = 0;

            if (robot.getDirection() - angleToTarget > Math.PI) {
                robot.setDirection(robot.getDirection() - 2 * Math.PI);
            } else if (robot.getDirection() - angleToTarget < Math.PI) {
                robot.setDirection(robot.getDirection() + 2 * Math.PI);
            }

            double deltaAngle = angleToTarget - robot.getDirection();
            if (deltaAngle > Math.PI) {
                deltaAngle -= 2 * Math.PI;
            } else if (deltaAngle < -Math.PI) {
                deltaAngle += 2 * Math.PI;
            }

            angularVelocity = Math.signum(deltaAngle) * MAX_ANGULAR_VELOCITY;

            moveRobot(robot, velocity, angularVelocity, 10);
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

    private void moveRobot(IRobotModel robot, double velocity, double angularVelocity, double duration) {
        velocity = applyLimits(velocity, 0, MAX_VELOCITY);
        angularVelocity = applyLimits(angularVelocity, -MAX_ANGULAR_VELOCITY, MAX_ANGULAR_VELOCITY);
        double currentX = robot.getPositionX();
        double currentY = robot.getPositionY();
        double currentDirection = robot.getDirection();

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
        robot.setPosition(newX, newY);

        double newDirection = asNormalizedRadians(currentDirection + angularVelocity * duration);
        robot.setDirection(newDirection);
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
}
