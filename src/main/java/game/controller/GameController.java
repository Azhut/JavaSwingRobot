package game.controller;

import game.model.Game;
import game.model.Player;
import game.model.RobotModel;
import game.view.GameVisualizer;
import game.view.PlayerComponent;

import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.geom.Rectangle2D;
import java.util.LinkedList;
import java.util.List;

public class GameController
{
    private final Game game;
    private final GameVisualizer view;

    public GameController(Game game)
    {
        List<Player> players = new LinkedList<>();
        Player player = new Player(new RobotModel(new Rectangle2D.Double(10, 10, 100, 100)));

        players.add(player);

        List<PlayerComponent> playerComponents = new LinkedList<>();
        playerComponents.add(new PlayerComponent(player));

        this.game = game;
        game.addPlayer(player);
        this.view = new GameVisualizer(playerComponents);
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

    public void keyPressed(KeyEvent e)
    {
        int keyCode = e.getKeyCode();
        switch (keyCode)
        {
            case KeyEvent.VK_UP, KeyEvent.VK_W:
                moveRobots(0, -10);
                break;
            case KeyEvent.VK_DOWN, KeyEvent.VK_S:
                moveRobots(0, 10);
                break;
            case KeyEvent.VK_LEFT, KeyEvent.VK_A:
                moveRobots(-10, 0);
                break;
            case KeyEvent.VK_RIGHT, KeyEvent.VK_D:
                moveRobots(10, 0);
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

    public void addPlayer(Player player)
    {
        game.addPlayer(player);
    }


    private void moveRobots(int deltaX, int deltaY) //TODO: Здесь просто можно дополнительно передавать робота, которого нужно подвинуть
    {
        for (Player player : game.getPlayers())
        {
            player.moveRobot(deltaX, deltaY);
        }
    }
}
