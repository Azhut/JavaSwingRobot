package game.view;

import authentication.PlayerFactory;
import game.controller.GameController;
import game.model.Game;
import game.model.Player;
import game.model.RobotModel;
import game.model.TargetModel;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;

public class GameWindow extends JInternalFrame {
    private final GameVisualizer gameVisualizer;
    private final GameController gameController;
    private final Game game;




    public GameWindow() {
        super("Игровое поле", true, true, true);


        this.game = new Game();
        this.gameVisualizer = new GameVisualizer(game);
        this.gameController = new GameController(game);

        PlayerFactory playerFactory=new PlayerFactory("tokens.txt");
        Player player = playerFactory.createPlayer("egor");
        game.addPlayer(player);

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(gameVisualizer, BorderLayout.CENTER);

        getContentPane().add(panel);


        add(gameVisualizer);

        pack();
    }

}
