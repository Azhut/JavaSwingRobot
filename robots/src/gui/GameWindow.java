package gui;

import javax.swing.*;
import java.awt.BorderLayout;

/**
 * Окно игры
 */
public class GameWindow extends JInternalFrame {

    public GameWindow(String jarFilePath, String className) {
        super("Игровое поле", true, true, true);

        IRobotModel robotModel = loadRobotModel(jarFilePath, className);

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
}
