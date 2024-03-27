package gui.controller;

import gui.model.TargetModel;
import gui.view.GameVisualizer;
import gui.model.IRobotModel;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class GameController {
    private final GameVisualizer visualizer;
    private final IRobotModel model;
    private final TargetModel targetModel;

    public GameController(GameVisualizer visualizer, IRobotModel model, TargetModel targetModel) {
        this.visualizer = visualizer;
        this.model = model;
        this.targetModel = targetModel;

        visualizer.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                handleMouseClick(e);
            }
        });
    }

    private void handleMouseClick(MouseEvent e) {
        targetModel.setPosition(e.getX(), e.getY());
        visualizer.repaint();
    }
}
