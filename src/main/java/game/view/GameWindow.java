
package game.view;

import game.model.IRobotModel;
import game.model.ModelLoader;
import game.view.GameVisualizer;

import javax.swing.*;
import java.awt.*;

public class GameWindow extends JInternalFrame {
    private final IRobotModel robotModel;

    public GameWindow() {
        super("Игровое поле", true, true, true);

        String jarFilePath = "src/main/java/game/model/MyRobotModel.jar";
        String robotClassName = "game.model.RobotModel";



        robotModel = loadRobotModel(jarFilePath, robotClassName);

        GameVisualizer m_visualizer = new GameVisualizer(robotModel);

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(m_visualizer, BorderLayout.CENTER);

        getContentPane().add(panel);

        pack();
    }

    /**
     * Загружает экземпляр класса робота из JAR-файла.
     * @param jarFilePath путь к JAR-файлу
     * @param className полное имя класса робота
     * @return экземпляр класса робота
     */
    private IRobotModel loadRobotModel(String jarFilePath, String className) {
        ModelLoader modelLoader = new ModelLoader();
        try {
            Class<?> robotModelClass = modelLoader.loadModelClass(jarFilePath, className);
            return (IRobotModel) robotModelClass.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to load RobotModel", e);
        }
    }

    public IRobotModel getRobotModel() {
        return robotModel;
    }
}
