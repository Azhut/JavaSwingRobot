import game.model.Game;
import network.controller.NetworkController;

import javax.swing.*;
import java.io.IOException;
import java.util.Locale;

/**
 * Start point
 */
public class RobotsProgram {
    public static void main(String[] args){

        Locale.setDefault(new Locale("ru", "RU"));
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception e) {
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(() -> {
            MainApplicationFrame frame = null;
            try {
                frame = new MainApplicationFrame();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            frame.setVisible(true);

            // Путь к файлу с токенами
            String tokenFilePath = "tokens.txt";

            // Создание объекта игры
            Game game = new Game();

            // Создание NetworkController
            try {
                NetworkController controller = new NetworkController(game, tokenFilePath);
                controller.startServer(); // Запускаем сервер
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}
