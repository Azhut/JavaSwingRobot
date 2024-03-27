import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Locale;
import java.util.ResourceBundle;
import javax.swing.*;

import fileManagers.ConfigManager;
import game.view.GameWindow;
import game.view.RobotCoordinatesWindow;
import log.view.LogWindow;
import log.Logger;

/**
 * Главное окно
 */
public class MainApplicationFrame extends JFrame {
    private final JDesktopPane desktopPane = new JDesktopPane();
    private boolean disposed = false;
    private final ConfigManager configManager = new ConfigManager();

    public MainApplicationFrame() {


        ResourceBundle messagesBundle = ResourceBundle.getBundle("messages", Locale.getDefault());


        int inset = 50;
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setBounds(inset, inset, screenSize.width - inset * 2, screenSize.height - inset * 2);
        setContentPane(desktopPane);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                configManager.saveConfig(desktopPane, MainApplicationFrame.this);
            }
        });

        LogWindow logWindow = createLogWindow();
        addWindow(logWindow);

        String jarFilePath = "RobotModel.jar";
        String robotClassName = "game.model.RobotModel";
        GameWindow gameWindow = new GameWindow(jarFilePath, robotClassName);
        addWindow(gameWindow);

        RobotCoordinatesWindow robotCoordinatesWindow = new RobotCoordinatesWindow(gameWindow.getRobotModel());
        addWindow(robotCoordinatesWindow);

        configManager.loadConfig(desktopPane, this);

        setJMenuBar(generateMenuBar());
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                int res = handleWindowClosing();

                if (res == JOptionPane.YES_OPTION) {
                    e.getWindow().setVisible(false);
                    System.exit(0);
                }
            }
        });
    }

    protected LogWindow createLogWindow() {
        LogWindow logWindow = new LogWindow(Logger.getDefaultLogSource());
        setMinimumSize(logWindow.getSize());
        logWindow.pack();
        Logger.debug("Протокол работает");
        return logWindow;
    }

    protected void addWindow(JInternalFrame frame) {
        desktopPane.add(frame);
        frame.setVisible(true);
    }

    private JMenuBar generateMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        menuBar.add(generateLookAndFeelMenu());
        menuBar.add(generateTestMenu());
        return menuBar;
    }

    private JMenu generateLookAndFeelMenu() {
        JMenu lookAndFeelMenu = new JMenu("Режим отображения");
        lookAndFeelMenu.setMnemonic(KeyEvent.VK_V);
        lookAndFeelMenu.getAccessibleContext().setAccessibleDescription("Управление режимом отображения");
        lookAndFeelMenu.add(createLookAndFeelMenuItem("Системная схема", KeyEvent.VK_S, UIManager.getSystemLookAndFeelClassName()));
        lookAndFeelMenu.add(createLookAndFeelMenuItem("Универсальная схема", KeyEvent.VK_U, UIManager.getCrossPlatformLookAndFeelClassName()));
        return lookAndFeelMenu;
    }

    private JMenuItem createLookAndFeelMenuItem(String label, int mnemonic, String className) {
        JMenuItem menuItem = new JMenuItem(label, mnemonic);
        menuItem.addActionListener((event) -> {
            setLookAndFeel(className);
            this.invalidate();
        });
        return menuItem;
    }

    private JMenu generateTestMenu() {
        JMenu testMenu = new JMenu("Тесты");
        testMenu.setMnemonic(KeyEvent.VK_T);
        testMenu.getAccessibleContext().setAccessibleDescription("Тестовые команды");
        testMenu.add(createTestMenuItem("Сообщение в лог", KeyEvent.VK_S, () -> Logger.debug("Новая строка")));
        testMenu.add(createTestMenuItem("Закрыть", KeyEvent.VK_C, this::handleWindowClosing));
        return testMenu;
    }

    private JMenuItem createTestMenuItem(String label, int mnemonic, Runnable action) {
        JMenuItem menuItem = new JMenuItem(label, mnemonic);
        menuItem.addActionListener((event) -> action.run());
        return menuItem;
    }

    private void setLookAndFeel(String className) {
        try {
            UIManager.setLookAndFeel(className);
            SwingUtilities.updateComponentTreeUI(this);
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException |
                 UnsupportedLookAndFeelException e) {
            // just ignore
        }
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
            disposed = true;
            dispose();
        }
        return option;
    }

    public boolean isDisposed() {
        return disposed;
    }

}
