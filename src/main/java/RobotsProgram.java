import javax.swing.*;
import java.util.Locale;


/**
 * Start point
 */
public class RobotsProgram {
    public static void main(String[] args) {
        Locale.setDefault(new Locale("en", "EN"));

//
//        Player player = new Player(new RobotModel(), new TargetModel(550, 400), new Rectangle2D.Double(100, 100, 100, 100));
//        PlayerFactory playerFactory = new PlayerFactory("tokens.txt");
//        String token = "grisha";
//        playerFactory.serializePlayer(player, token);


//        Player loadedPlayer = playerFactory.createPlayer(token);
//        if (loadedPlayer != null) {
//            System.out.println("Загруженный игрок: " + loadedPlayer);
//        } else {
//            System.out.println("Не удалось загрузить игрока.");
//        }
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");

        } catch (Exception e) {
            e.printStackTrace();
        }
        SwingUtilities.invokeLater(() -> {
            MainApplicationFrame frame = new MainApplicationFrame();
            frame.setVisible(true);
        });
    }
}