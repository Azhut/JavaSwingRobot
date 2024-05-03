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

    private static final double MAX_VELOCITY = 0.5;
    private static final double MAX_ANGULAR_VELOCITY = 0.001;
    private static final double STOP_DISTANCE = 0.1;
    private static final double SLOW_DOWN_DISTANCE = 0.5;

    public GameController(Game game) {
        this.game = game;

        Timer timer = new Timer("Генератор событий", true);
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

    public void setTargetPosition(Point p) { //TODO
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
            double angleToTarget = angleTo(robot.getPositionX(), robot.getPositionY(), target.getX(), target.getY());
            double deltaAngle = normalizeAngle(angleToTarget - robot.getDirection());
            if (Math.abs(deltaAngle) > MAX_ANGULAR_VELOCITY) {
                double sign = Math.signum(deltaAngle);
                robot.setDirection(robot.getDirection() + sign * MAX_ANGULAR_VELOCITY);
            } else {
                robot.setDirection(angleToTarget);
            }
            double distance = distance(target.getX(), target.getY(), robot.getPositionX(), robot.getPositionY());
            if (distance > STOP_DISTANCE) {
                double velocity = Math.min(MAX_VELOCITY, distance);
                if (distance < SLOW_DOWN_DISTANCE) {
                    velocity *= distance / SLOW_DOWN_DISTANCE;
                }
                moveRobot(robot, velocity, robot.getDirection(), 50);
            } else {

                robot.setDirection(angleToTarget);
                moveRobot(robot, 0, robot.getDirection(), 0);
            }
        }
    }

    private double distance(double x1, double y1, double x2, double y2) {
        double diffX = x1 - x2;
        double diffY = y1 - y2;
        return Math.sqrt(diffX * diffX + diffY * diffY);
    }

    private double angleTo(double fromX, double fromY, double toX, double toY) {
        return Math.atan2(toY - fromY, toX - fromX);
    }

    private double normalizeAngle(double angle) {
        while (angle < -Math.PI) {
            angle += 2 * Math.PI;
        }
        while (angle >= Math.PI) {
            angle -= 2 * Math.PI;
        }
        return angle;
    }

    private void moveRobot(IRobotModel robot, double velocity, double direction, double duration) {
        double newX = robot.getPositionX() + velocity * Math.cos(direction) * duration;
        double newY = robot.getPositionY() + velocity * Math.sin(direction) * duration;
        robot.setPosition(newX, newY);
    }
}
