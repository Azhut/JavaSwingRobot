package gui;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JPanel;

public class GameVisualizer extends JPanel {
    private final Timer m_timer = initTimer();
    private final IRobotModel robotModel;

    private static Timer initTimer() {
        Timer timer = new Timer("events generator", true);
        return timer;
    }

    private volatile int m_targetPositionX = 150;
    private volatile int m_targetPositionY = 100;

    private static final double maxVelocity = 0.1;
    private static final double maxAngularVelocity = 0.001;

    public GameVisualizer(IRobotModel robotModel) {
        this.robotModel = robotModel;
        m_timer.schedule(new TimerTask() {
            @Override
            public void run() {
                onRedrawEvent();
            }
        }, 0, 50);
        m_timer.schedule(new TimerTask() {
            @Override
            public void run() {
                onModelUpdateEvent();
            }
        }, 0, 10);

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                setTargetPosition(e.getPoint());
                repaint();
            }
        });
        setDoubleBuffered(true);
    }

    protected void setTargetPosition(Point p) {
        m_targetPositionX = p.x;
        m_targetPositionY = p.y;
    }

    protected void onRedrawEvent() {
        EventQueue.invokeLater(this::repaint);
    }

    protected void onModelUpdateEvent() {
        double distance = distance(m_targetPositionX, m_targetPositionY,
                robotModel.getPositionX(), robotModel.getPositionY());
        if (distance < 0.5) {
            return;
        }
        double velocity = maxVelocity;
        double angleToTarget = angleTo(robotModel.getPositionX(), robotModel.getPositionY(),
                m_targetPositionX, m_targetPositionY);
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

        angularVelocity = Math.signum(deltaAngle) * maxAngularVelocity;

        moveRobot(velocity, angularVelocity, 10);
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

    private void moveRobot(double velocity, double angularVelocity, double duration) {
        velocity = applyLimits(velocity, 0, maxVelocity);
        angularVelocity = applyLimits(angularVelocity, -maxAngularVelocity, maxAngularVelocity);
        double currentX = robotModel.getPositionX();
        double currentY = robotModel.getPositionY();
        double currentDirection = robotModel.getDirection();

        double newX = currentX + velocity / angularVelocity *
                (Math.sin(currentDirection +
                        angularVelocity * duration) -
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

    private static int round(double value) {
        return (int) (value + 0.5);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        paintRobot(g2d, robotModel.getPositionX(), robotModel.getPositionY(), robotModel.getDirection());
        drawTarget(g2d, m_targetPositionX, m_targetPositionY);
    }

    private void drawTarget(Graphics2D g, int x, int y) {
        AffineTransform t = AffineTransform.getRotateInstance(0, 0, 0);
        g.setTransform(t);
        g.setColor(Color.GREEN);
        fillOval(g, x, y, 5, 5);
        g.setColor(Color.BLACK);
        drawOval(g, x, y, 5, 5);
    }

    private static void fillOval(Graphics g, int centerX, int centerY, int diam1, int diam2) {
        g.fillOval(centerX - diam1 / 2, centerY - diam2 / 2, diam1, diam2);
    }

    private static void drawOval(Graphics g, int centerX, int centerY, int diam1, int diam2) {
        g.drawOval(centerX - diam1 / 2, centerY - diam2 / 2, diam1, diam2);
    }


    private void paintRobot(Graphics2D g, double x, double y, double direction) {
        try {

            // Вызываем метод drawRobot у переданного экземпляра robotModel
            Method drawRobotMethod = robotModel.getClass().getMethod("drawRobot", Graphics2D.class, Double.TYPE, Double.TYPE, Double.TYPE);

            // Вызываем метод drawRobot, передавая ему Graphics2D и координаты робота
            drawRobotMethod.invoke(robotModel, g, x, y, direction);
        } catch (Exception e) {
            // Обработка ошибок
            e.printStackTrace();
        }
    }








}
