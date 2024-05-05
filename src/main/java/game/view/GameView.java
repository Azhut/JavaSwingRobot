package game.view;

import game.model.Player;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.List;

public class GameView extends JPanel
{
    private final List<Player> players;
    public GameView(List<Player> players)
    {
        this.players = players;
        setFocusable(true);
        requestFocusInWindow();
    }

    @Override
    protected void paintComponent(Graphics g)
    {
        super.paintComponent(g);

        for (Player player : players)
        {
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.setColor(Color.RED);

            Shape robotShape = player.getShape();
            double posX = player.getRobot().getPositionX();
            double posY = player.getRobot().getPositionY();

            AffineTransform transform = AffineTransform.getTranslateInstance(posX, posY);
            g2d.draw(transform.createTransformedShape(robotShape));

            g2d.dispose();
        }
    }
}
