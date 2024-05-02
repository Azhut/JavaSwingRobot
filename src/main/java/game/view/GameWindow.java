package game.view;

import authentication.PlayerSerializer;
import game.controller.GameController;
import game.model.Game;
import game.model.Player;
import game.model.RobotModel;
import game.model.TargetModel;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.io.IOException;

public class GameWindow extends JInternalFrame {
    private final GameVisualizer gameVisualizer;
    private final GameController gameController;
    private final Game game;

    // Конструктор
    public GameWindow() {
        setTitle("Моя игра");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        // Создание объекта игры
        this.game = new Game();




        // Создание визуализатора игры
        this.gameVisualizer = new GameVisualizer(game);

        // Создание контроллера игры
        this.gameController = new GameController(game);

        // Установка размеров окна
        int width = 800; // Пример ширины
        int height = 600; // Пример высоты
        setPreferredSize(new Dimension(width, height));

        // Добавление визуализатора в окно
        add(gameVisualizer);

        pack();
    }

}
