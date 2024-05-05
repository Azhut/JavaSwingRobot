package game.windows;

import game.controller.GameController;
import game.model.Game;

import javax.swing.*;
import java.awt.*;

public class GameWindow extends JInternalFrame
{
    private final GameController gameController;
    public GameWindow() {
        super("Игровое поле", true, true, true);

        this.gameController = new GameController(new Game());

        JPanel gamePanel = gameController.getGamePanel();

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(gamePanel, BorderLayout.CENTER);
        getContentPane().add(panel);
        gamePanel.requestFocusInWindow();
        add(gamePanel);

//        Player player2 = new Player(new RobotModel(new Rectangle2D.Double(1, 10, 100, 40)) );
//        gameController.addPlayer(player);
//        game.addPlayer(player2);

        pack();
    }

    public Game getGame() {
        return gameController.getGame();
    }
}
