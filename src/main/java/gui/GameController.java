package gui;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class GameController {
    private final GameVisualizer visualizer;
    private final IRobotModel model;
    private final Target target;

    public GameController(GameVisualizer visualizer, IRobotModel model, Target target) {
        this.visualizer = visualizer;
        this.model = model;
        this.target = target;

        visualizer.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                handleMouseClick(e);
            }
        });
    }

    private void handleMouseClick(MouseEvent e) {
        target.setPosition(e.getX(), e.getY());
        visualizer.repaint();
    }
}
