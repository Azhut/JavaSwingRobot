package game.model;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Game {
    private List<IRobotModel> robots;
    private Map<IRobotModel, TargetModel> robotTargets;

    public Game() {
        robots = new ArrayList<>();
        robotTargets = new HashMap<>();
    }

    // Метод для добавления модели робота в игру
    public void addRobot(IRobotModel robot) {
        robots.add(robot);
    }

    // Метод для удаления модели робота из игры
    public void removeRobot(IRobotModel robot) {
        robots.remove(robot);
        robotTargets.remove(robot); // Удаляем цель робота
    }

    // Метод для установки цели для робота
    public void setRobotTarget(IRobotModel robot, Point targetPoint) {
        TargetModel target=new TargetModel((int) targetPoint.getX(), (int) targetPoint.getY());
        robotTargets.put(robot, target);
    }

    // Метод для получения цели для робота
    public TargetModel getRobotTarget(IRobotModel robot) {
        return robotTargets.get(robot);
    }

    // Метод для получения списка моделей роботов в игре
    public List<IRobotModel> getRobots() {
        return robots;
    }

    // Метод для установки списка моделей роботов в игре
    public void setRobots(List<IRobotModel> robots) {
        this.robots = robots;
    }
}
