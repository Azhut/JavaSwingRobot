package gui;

import javax.swing.*;
import java.awt.BorderLayout;

public class GameWindow extends JInternalFrame {
    private final GameVisualizer m_visualizer;


    public GameWindow(String jarFilePath, String className) {
        super("Игровое поле", true, true, true);


        m_visualizer = new GameVisualizer(loadRobotModel(jarFilePath, className));

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(m_visualizer, BorderLayout.CENTER);

        getContentPane().add(panel);

        pack();
    }

    private IRobotModel loadRobotModel(String jarFilePath, String className) {
        try {
            Class<?> robotModelClass = JarClassLoader.loadClassFromJar(jarFilePath, className);



            // Создаем экземпляр класса RobotModel и приводим его к интерфейсу IRobotModel
            return (IRobotModel) robotModelClass.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to load RobotModel from jar file", e);
        }
    }
}
