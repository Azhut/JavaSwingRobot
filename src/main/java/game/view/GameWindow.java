package game.view;

import game.controller.GameController;
import game.model.Game;
import game.model.IRobotModel;
import game.model.RobotModel;
import game.model.TargetModel;

import javax.swing.*;
import java.awt.*;

public class GameWindow  extends JInternalFrame {
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

    // Метод для обновления отображения роботов на игровом поле
    public void updateRobots() {
        gameController.setRobots(game.getRobots()); // Обновляем роботов через контроллер
        gameVisualizer.repaint(); // Перерисовать визуализатор
    }

    // Метод для обновления отображения целей для роботов на игровом поле
    public void updateTargets() {
        for (IRobotModel robot : game.getRobots()) {
            TargetModel target = game.getRobotTarget(robot);
            gameController.setRobotTarget(robot, target); // Устанавливаем цель для робота через контроллер
        }
        gameVisualizer.repaint(); // Перерисовать визуализатор
    }
}
