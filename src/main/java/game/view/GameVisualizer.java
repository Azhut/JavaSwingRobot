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

            // Транслируем и масштабируем форму робота в соответствии с текущими координатами
            AffineTransform transform = AffineTransform.getTranslateInstance(posX, posY);
            g2d.draw(transform.createTransformedShape(robotShape));

            g2d.dispose();
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        System.out.println("Key pressed in GameVisualizer: " + e.getKeyCode());
        // Передача события нажатия клавиши в GameController
        gameController.keyPressed(e);
        // Перерисовка компонента после перемещения робота
        repaint();
    }

    @Override
    public void keyTyped(KeyEvent e) {
//        System.out.println("Key typed: " + e.getKeyChar());
        // Не используется
    }

    @Override
    public void keyReleased(KeyEvent e) {
//        System.out.println("Key released: " + e.getKeyCode());
        // Не используется
    }

}
