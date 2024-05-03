package game.view;

import game.controller.GameController;
import game.model.Game;
import game.model.Player;
import game.model.RobotModel;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Rectangle2D;

public class GameWindow extends JInternalFrame {
    private final GameVisualizer gameVisualizer;
    private final GameController gameController;
    private final Game game;


    public GameWindow() {
        super("Игровое поле", true, true, true);


        this.game = new Game();
        this.gameController = new GameController(game);
        this.gameVisualizer = new GameVisualizer(gameController, game);

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(gameVisualizer, BorderLayout.CENTER);
        getContentPane().add(panel);
        gameVisualizer.requestFocusInWindow();
        add(gameVisualizer);

        Player player = new Player(new RobotModel(), new Rectangle2D.Double(10, 10, 100, 100));
        Player player2 = new Player(new RobotModel(), new Rectangle2D.Double(1, 10, 100, 40));
        game.addPlayer(player);
        game.addPlayer(player2);


        pack();

    }

    public Game getGame() {
        return game;
    }

}
