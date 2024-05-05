package game.controller;

import game.model.Game;
import game.model.Player;
import game.model.RobotModel;
import game.view.GameView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.geom.Rectangle2D;
import java.util.LinkedList;
import java.util.List;

public class GameController
{
    private final Game game;
    private final GameView view;

    public GameController(Game game)
    {
        List<Player> players = new LinkedList<>();
        Player player = new Player(new RobotModel(new Rectangle2D.Double(10, 10, 100, 100)));
        players.add(player);
        game.addPlayer(player);

        this.game = game;
        this.view = new GameView(players);
        this.view.addKeyListener(new KeyAdapter()
        {
                @Override
                public void keyPressed(KeyEvent e)
                {
                    GameController.this.keyPressed(e);
                }
            }
        );
    }

    private void keyPressed(KeyEvent e)
    {
        Player player = game.getPlayers().get(0);
        int keyCode = e.getKeyCode();
        switch (keyCode)
        {
            case KeyEvent.VK_UP, KeyEvent.VK_W:
                moveRobot(player, 0, -10);
                break;
            case KeyEvent.VK_DOWN, KeyEvent.VK_S:
                moveRobot(player,0, 10);
                break;
            case KeyEvent.VK_LEFT, KeyEvent.VK_A:
                moveRobot(player,-10, 0);
                break;
            case KeyEvent.VK_RIGHT, KeyEvent.VK_D:
                moveRobot(player,10, 0);
                break;
        }
        view.repaint();
    }

    public JPanel getGamePanel()
    {
        return view;
    }

    public Game getGame()
    {
        return game;
    }

    private void moveRobot(Player player, int deltaX, int deltaY)
    {
        player.moveRobot(deltaX, deltaY);
        checkBounds(player);
    }

    private void checkBounds(Player player)
    {
        Rectangle bounds = view.getBounds();

        double posX = player.getRobot().getPositionX();
        double posY = player.getRobot().getPositionY();

        if (posX < bounds.getMinX())
        {
            player.getRobot().setPosition(bounds.getMaxX(), posY);
        }
        else if (posX > bounds.getMaxX())
        {
            player.getRobot().setPosition(bounds.getMinX(), posY);
        }

        if (posY < bounds.getMinY())
        {
            player.getRobot().setPosition(posX, bounds.getMaxY());
        }
        else if (posY > bounds.getMaxY())
        {
            player.getRobot().setPosition(posX, bounds.getMinY());
        }
    }
}
