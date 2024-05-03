package game.view;

import game.model.Game;
import game.model.Player;
import game.model.RobotModel;

import javax.swing.*;

public class RobotCoordinatesWindow extends JInternalFrame {
    public RobotCoordinatesWindow(Game game) {
        super("Robot Coordinates", true, true, true, true);

        JTextArea coordinatesTextArea = new JTextArea();
        coordinatesTextArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(coordinatesTextArea);
        getContentPane().add(scrollPane);

        StringBuilder coordinatesText = new StringBuilder("Robot coordinates:\n");
        for (Player player : game.getPlayers()) {
            RobotModel robot = player.getRobot();
            robot.addObserver((o, arg) -> {
                coordinatesText.setLength(0);
                coordinatesText.append("Robot coordinates:\n");
                for (Player p : game.getPlayers()) {
                    RobotModel r = p.getRobot();
                    coordinatesText.append("Player: ").append("token").append(", Coordinates: (")
                            .append(r.getPositionX()).append(", ").append(r.getPositionY()).append(")\n");
                }
                coordinatesTextArea.setText(coordinatesText.toString());
            });
        }
        coordinatesTextArea.setText(coordinatesText.toString());

        pack();
        setVisible(true);
    }
}
