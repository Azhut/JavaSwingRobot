package game.view;

import game.model.IRobotModel;

import javax.swing.*;
import java.util.Observable;
import java.util.Observer;

/**
 * Окно вывода координат робота
 */
public class RobotCoordinatesWindow extends JInternalFrame implements Observer {
    private JLabel coordinatesLabel;

    public RobotCoordinatesWindow(IRobotModel robotModel) {
        super("Robot Coordinates", true, true, true, true);

        coordinatesLabel = new JLabel("Robot coordinates: ");
        add(coordinatesLabel);

        ((Observable) robotModel).addObserver(this);

        setVisible(true);
    }

    private void updateCoordinates(IRobotModel robot) {
        coordinatesLabel.setText("Robot coordinates: (" + robot.getPositionX() + ", " + robot.getPositionY() + ")");
    }

    @Override
    public void update(Observable o, Object arg) {
        if (o instanceof IRobotModel) {
            IRobotModel robot = (IRobotModel) o;
            updateCoordinates(robot);
        }
    }
}
