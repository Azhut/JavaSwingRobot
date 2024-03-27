package game.view;

import game.controller.JarClassLoader;
import game.model.IRobotModel;
import game.view.GameVisualizer;

import javax.swing.*;
import java.awt.BorderLayout;

/**
 * Окно игры
 */
public class GameWindow extends JInternalFrame {
    private final IRobotModel robotModel;

    public GameWindow(String jarFilePath, String className) {
        super("Игровое поле", true, true, true);

        robotModel = loadRobotModel(jarFilePath, className);

        GameVisualizer m_visualizer = new GameVisualizer(robotModel);

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(m_visualizer, BorderLayout.CENTER);

        getContentPane().add(panel);

        pack();
    }

    /**
     * @param jarFilePath путь до класс
     * @param className полное имя класса
     * @return Интерфейс модели робота
     */
    private IRobotModel loadRobotModel(String jarFilePath, String className) {
        try {
            Class<?> robotModelClass = JarClassLoader.loadClassFromJar(jarFilePath, className);

            return (IRobotModel) robotModelClass.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to load RobotModel from jar file", e);
        }
    }

    public IRobotModel getRobotModel() {
        return robotModel;
    }
}
