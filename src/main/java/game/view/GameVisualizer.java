package game.view;

import game.controller.GameController;
import game.model.Game;
import game.model.Player;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.AffineTransform;

public class GameVisualizer extends JPanel implements KeyListener {
    private final GameController gameController;
    private final Game game;

    public GameVisualizer(GameController gameController, Game game) {
        this.gameController = gameController;
        this.game = game;
        addKeyListener(this);
        setFocusable(true);
        requestFocusInWindow();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (Player player : game.getPlayers()) {
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.setColor(Color.RED);

            Shape robotShape = player.getRobotShape();


            double posX = player.getRobot().getPositionX();
            double posY = player.getRobot().getPositionY();

            AffineTransform transform = AffineTransform.getTranslateInstance(posX, posY);
            g2d.draw(transform.createTransformedShape(robotShape));

            // Проверяем выход за границы окна и возвращаем робота с другой стороны
            Rectangle bounds = this.getBounds();
            if (posX < bounds.getMinX()) {
                player.getRobot().setPosition(bounds.getMaxX(), posY);
            } else if (posX > bounds.getMaxX()) {
                player.getRobot().setPosition(bounds.getMinX(), posY);
            }
            if (posY < bounds.getMinY()) {
                player.getRobot().setPosition(posX, bounds.getMaxY());
            } else if (posY > bounds.getMaxY()) {
                player.getRobot().setPosition(posX, bounds.getMinY());
            }
            g2d.dispose();
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        gameController.keyPressed(e);

        repaint();
    }

    @Override
    public void keyTyped(KeyEvent e) {

        // Не используется
    }

    @Override
    public void keyReleased(KeyEvent e) {

        // Не используется
    }

}
