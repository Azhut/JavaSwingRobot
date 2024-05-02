// MainApplicationFrame.java
import fileManagers.ConfigManager;
import game.view.GameWindow;
import log.Logger;
import log.view.LogWindow;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Locale;
import java.util.ResourceBundle;

public class MainApplicationFrame extends JFrame {
    private final JDesktopPane desktopPane = new JDesktopPane();
    private final ConfigManager configManager = new ConfigManager();

    public MainApplicationFrame() {
        initializeUI();
        createWindows();
        setJMenuBar(MenuBuilder.generateMenuBar(this::handleWindowClosing));
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new ClosingWindowAdapter());
    }

    private void initializeUI() {
        ResourceBundle messagesBundle = ResourceBundle.getBundle("messages", Locale.getDefault());
        int inset = 50;
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setBounds(inset, inset, screenSize.width - inset * 2, screenSize.height - inset * 2);
        setContentPane(desktopPane);
    }

    private void createWindows() {
        LogWindow logWindow = new LogWindow(Logger.getDefaultLogSource());
        addWindow(logWindow);

        GameWindow gameWindow = new GameWindow();
        addWindow(gameWindow);

        //RobotCoordinatesWindow robotCoordinatesWindow = new RobotCoordinatesWindow(gameWindow.getRobotModel());
        //addWindow(robotCoordinatesWindow);

        //configManager.loadConfig(desktopPane, this);
    }

    private void addWindow(JInternalFrame frame) {
        desktopPane.add(frame);
        frame.setVisible(true);
    }

    private int handleWindowClosing() {
        int option = JOptionPane.showConfirmDialog(
                this,
                "Подтвердите выход", // Текст сообщения
                "Выход",             // Заголовок окна
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE
        );

        if (option == JOptionPane.YES_OPTION) {
            configManager.saveConfig(desktopPane, this);
            dispose();
        }
        return option;
    }

    private static class ClosingWindowAdapter extends WindowAdapter {
        @Override
        public void windowClosing(WindowEvent e) {
            MainApplicationFrame frame = (MainApplicationFrame) e.getWindow();
            frame.handleWindowClosing();
        }
    }
}



class MenuBuilder {
    static JMenuBar generateMenuBar(Runnable closingAction) {
        JMenuBar menuBar = new JMenuBar();
        menuBar.add(buildLookAndFeelMenu());
        menuBar.add(buildTestMenu(closingAction));
        return menuBar;
    }

    private static JMenu buildLookAndFeelMenu() {
        JMenu lookAndFeelMenu = new JMenu("Режим отображения");
        lookAndFeelMenu.setMnemonic(KeyEvent.VK_V);
        lookAndFeelMenu.getAccessibleContext().setAccessibleDescription("Управление режимом отображения");
        lookAndFeelMenu.add(createLookAndFeelMenuItem("Системная схема", KeyEvent.VK_S, UIManager.getSystemLookAndFeelClassName()));
        lookAndFeelMenu.add(createLookAndFeelMenuItem("Универсальная схема", KeyEvent.VK_U, UIManager.getCrossPlatformLookAndFeelClassName()));
        return lookAndFeelMenu;
    }

    private static JMenu buildTestMenu(Runnable closingAction) {
        JMenu testMenu = new JMenu("Тесты");
        testMenu.setMnemonic(KeyEvent.VK_T);
        testMenu.getAccessibleContext().setAccessibleDescription("Тестовые команды");
        testMenu.add(createTestMenuItem("Сообщение в лог", KeyEvent.VK_S, () -> Logger.debug("Новая строка")));
        testMenu.add(createTestMenuItem("Закрыть", KeyEvent.VK_C, closingAction));
        return testMenu;
    }

    private static JMenuItem createLookAndFeelMenuItem(String label, int mnemonic, String className) {
        JMenuItem menuItem = new JMenuItem(label, mnemonic);
        menuItem.addActionListener((event) -> {
            try {
                UIManager.setLookAndFeel(className);
                SwingUtilities.updateComponentTreeUI((JComponent) SwingUtilities.getRoot(menuItem));
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        return menuItem;
    }

    private static JMenuItem createTestMenuItem(String label, int mnemonic, Runnable action) {
        JMenuItem menuItem = new JMenuItem(label, mnemonic);
        menuItem.addActionListener((event) -> action.run());
        return menuItem;
    }
}
