package game.view;

import game.model.Player;

import java.awt.*;

public class PlayerComponent
{
    private Shape shape;
    private double positionX;

    private double positionY;
    public PlayerComponent(Player player)
    {
        this.shape = player.getRobotShape();
        this.positionX = player.getRobot().getPositionX();
        this.positionY = player.getRobot().getPositionY();
    }

    public Shape getShape()
    {
        return shape;
    }

    public double getPositionX() {
        return positionX;
    }

    public double getPositionY() {
        return positionY;
    }
}
