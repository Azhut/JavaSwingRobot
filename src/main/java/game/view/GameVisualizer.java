package game.view;

import game.model.Player;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.List;

public class GameVisualizer extends JPanel
{
    private final List<PlayerComponent> players;
    public GameVisualizer(List<PlayerComponent> players)
    {
        this.players = players;
        setFocusable(true);
        requestFocusInWindow();
    }

    @Override
    protected void paintComponent(Graphics g)
    {
        super.paintComponent(g);

        for (PlayerComponent player : players)
        {
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.setColor(Color.RED);

            Shape robotShape = player.getShape();

            double posX = player.getPositionX();
            double posY = player.getPositionY();

            AffineTransform transform = AffineTransform.getTranslateInstance(posX, posY);
            g2d.draw(transform.createTransformedShape(robotShape));

//            Rectangle bounds = this.getBounds();
//            if (posX < bounds.getMinX())
//            {
//                player.getRobot().setPosition(bounds.getMaxX(), posY);
//            }
//            else if (posX > bounds.getMaxX())
//            {
//                player.getRobot().setPosition(bounds.getMinX(), posY);
//            }
//            if (posY < bounds.getMinY())
//            {
//                player.getRobot().setPosition(posX, bounds.getMaxY());
//            }
//            else if (posY > bounds.getMaxY())
//            {
//                player.getRobot().setPosition(posX, bounds.getMinY());
//            }
            g2d.dispose();
        }
    }
}
