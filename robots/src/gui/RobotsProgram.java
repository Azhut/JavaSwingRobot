package gui;

import java.awt.Frame;
import java.util.Locale;

import javax.swing.*;


public class RobotsProgram {
    public static void main(String[] args) {
        Locale.setDefault(new Locale("ru", "RU"));
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
//        UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
//        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
//        UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        SwingUtilities.invokeLater(() -> {
            MainApplicationFrame frame = new MainApplicationFrame();
            frame.pack();
            frame.setVisible(true);
            frame.setExtendedState(Frame.MAXIMIZED_BOTH);

//            javax.swing.Timer timer = new javax.swing.Timer(5000, e -> {
//                if (frame.isDisposed()) {
//                    System.out.println("Приложение завершило работу.");
//                    System.exit(0);
//                }
//            });
//            timer.setInitialDelay(0);
//            timer.start();
        });
    }
}
