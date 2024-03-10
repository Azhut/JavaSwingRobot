package gui;

import javax.swing.*;
import java.awt.BorderLayout;

public class GameWindow extends JInternalFrame {
    private final GameVisualizer m_visualizer;

    public RobotModel getRobotModel() {
        return robotModel;
    }

    private final RobotModel robotModel; // Создаем экземпляр модели робота

    public GameWindow() {
        super("Игровое поле", true, true, true);

        robotModel = new RobotModel();

        m_visualizer = new GameVisualizer(robotModel);

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(m_visualizer, BorderLayout.CENTER);

        getContentPane().add(panel);

        pack();
    }

}
