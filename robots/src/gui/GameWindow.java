package gui;

import javax.swing.*;
import java.awt.BorderLayout;

public class GameWindow extends JInternalFrame {
    private final GameVisualizer m_visualizer;
    private final IRobotModel robotModel;

    public IRobotModel getRobotModel() {
        return robotModel;
    }

    public GameWindow(IRobotModel robotModel) {
        super("Игровое поле", true, true, true);

        this.robotModel = robotModel;

        m_visualizer = new GameVisualizer((RobotModel) robotModel); // Приведение типа к RobotModel

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(m_visualizer, BorderLayout.CENTER);

        getContentPane().add(panel);

        pack();
    }
}
