package game.controller;

import game.model.Game;
import game.model.Player;

import java.awt.event.KeyEvent;

public class GameController {
    private final Game game;

    public GameController(Game game) {
        this.game = game;
    }

    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        switch (keyCode) {
            case KeyEvent.VK_UP:
                moveRobots(0, -10);
                break;
            case KeyEvent.VK_DOWN:
                moveRobots(0, 10);
                break;
            case KeyEvent.VK_LEFT:
                moveRobots(-10, 0);
                break;
            case KeyEvent.VK_RIGHT:
                moveRobots(10, 0);
                break;
            case KeyEvent.VK_A:
                moveRobots(-10, 0);
                break;
            case KeyEvent.VK_D:
                moveRobots(10, 0);
                break;
            case KeyEvent.VK_W:
                moveRobots(0, -10);
                break;
            case KeyEvent.VK_S:
                moveRobots(0, 10);
                break;
        }
    }


    private void moveRobots(int deltaX, int deltaY) { //TODO: Здесь просто можно дополнительно передавать робота, которого нужно подвинуть
        for (Player player : game.getPlayers()) {
            player.moveRobot(deltaX, deltaY);
        }
    }
}
