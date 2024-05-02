
package game.view;

import game.model.IRobotModel;
import game.model.ModelLoader;
import game.model.RobotModel;
import game.view.GameVisualizer;

import javax.swing.*;
import java.awt.*;

public class GameWindow extends JInternalFrame {
    private final IRobotModel robotModel=new RobotModel();

    public GameWindow() {
        super("Игровое поле", true, true, true);



        GameVisualizer m_visualizer = new GameVisualizer(robotModel);

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(m_visualizer, BorderLayout.CENTER);

        getContentPane().add(panel);

        pack();
    }

    public IRobotModel getRobotModel() {
        return robotModel;
    }
}
