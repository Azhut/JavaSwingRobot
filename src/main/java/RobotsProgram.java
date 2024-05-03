import javax.swing.*;
import java.util.Locale;


/**
 * Start point
 */
public class RobotsProgram {
    public static void main(String[] args) {
        Locale.setDefault(new Locale("ru", "RU"));

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