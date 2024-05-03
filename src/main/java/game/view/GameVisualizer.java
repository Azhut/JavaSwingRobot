package game.view;

import game.controller.GameController;
import game.model.Game;
import game.model.IRobotModel;
import game.model.Player;
import game.model.TargetModel;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class GameVisualizer extends JPanel {
    private final Game game;
    private final GameController gameController;

    public GameVisualizer(Game game) {
        this.game = game;
        this.gameController = new GameController(game);

        Timer timer = new Timer("events generator", true);
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                onRedrawEvent();
            }
        }, 0, 50);

        addMouseListener(gameController);
        setDoubleBuffered(true);
    }

    protected void onRedrawEvent() {
        SwingUtilities.invokeLater(this::repaint);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        List<Player> players = game.getPlayers();
        for (Player player : players) {
            IRobotModel robot = player.getRobot();
            Shape robotShape = player.getRobotShape();
            paintRobot(g2d, robot.getPositionX(), robot.getPositionY(), robot.getDirection(), robotShape); // Передаем Shape вместо Image
            TargetModel target = player.getRobotTarget();
            if (target != null) {
                drawTarget(g2d, target.getX(), target.getY());
            }
        }
    }

    private void drawTarget(Graphics2D g, int x, int y) {
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

    private void paintRobot(Graphics2D g, double x, double y, double direction, Shape robotShape) {
        g.rotate(Math.toRadians(direction), x, y);
        g.draw(robotShape); // Рисуем фигуру робота
        g.rotate(-Math.toRadians(direction), x, y);
    }
}
