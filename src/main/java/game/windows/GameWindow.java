package game.windows;

import game.controller.GameController;
import game.model.Game;
import game.model.Player;
import game.model.RobotModel;
import game.view.GameVisualizer;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Rectangle2D;

public class GameWindow extends JInternalFrame
{
    private final GameController gameController;
    public GameWindow() {
        super("Игровое поле", true, true, true);

        this.gameController = new GameController(new Game());

        JPanel gameVisualizer = gameController.getGamePanel();

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(gameVisualizer, BorderLayout.CENTER);
        getContentPane().add(panel);
        gameVisualizer.requestFocusInWindow();
        add(gameVisualizer);

//        Player player2 = new Player(new RobotModel(new Rectangle2D.Double(1, 10, 100, 40)) );
//        gameController.addPlayer(player);
//        game.addPlayer(player2);

        pack();
    }

    public Game getGame() {
        return gameController.getGame();
    }
}
