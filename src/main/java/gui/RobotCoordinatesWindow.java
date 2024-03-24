package gui;

import javax.swing.*;
import java.util.Observable;
import java.util.Observer;

public class RobotCoordinatesWindow extends JInternalFrame implements Observer {
    private JLabel coordinatesLabel;

    public RobotCoordinatesWindow(RobotModel robotModel) {
        super("Robot Coordinates", true, true, true, true);


        coordinatesLabel = new JLabel("Robot coordinates: ");
        add(coordinatesLabel);

        robotModel.addObserver(this);

        setVisible(true);
    }

    private void updateCoordinates(double x, double y) {
        coordinatesLabel.setText("Robot coordinates: (" + x + ", " + y + ")");
    }

    @Override
    public void update(Observable o, Object arg) {
        if (o instanceof RobotModel) {
            RobotModel robot = (RobotModel) o;
            updateCoordinates(robot.getPositionX(), robot.getPositionY());
        }
    }
}
