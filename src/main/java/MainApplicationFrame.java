import fileManagers.ConfigManager;
import game.windows.GameWindow;
import game.windows.RobotCoordinatesWindow;
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
    private final ResourceBundle messagesBundle;
    private boolean disposed = false;

    public MainApplicationFrame() {
        messagesBundle = ResourceBundle.getBundle("messages", Locale.getDefault());

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

        GameWindow gameWindow = new GameWindow();
        addWindow(gameWindow);

        RobotCoordinatesWindow robotCoordinatesWindow = new RobotCoordinatesWindow(gameWindow.getGame());
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
        Logger.debug(messagesBundle.getString("log_protocol_working"));
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
        JMenu lookAndFeelMenu = new JMenu(messagesBundle.getString("look_and_feel_menu_label"));
        lookAndFeelMenu.setMnemonic(KeyEvent.VK_V);
        lookAndFeelMenu.getAccessibleContext().setAccessibleDescription(messagesBundle.getString("look_and_feel_menu_description"));
        lookAndFeelMenu.add(createLookAndFeelMenuItem(messagesBundle.getString("system_scheme_label"), KeyEvent.VK_S, UIManager.getSystemLookAndFeelClassName()));
        lookAndFeelMenu.add(createLookAndFeelMenuItem(messagesBundle.getString("universal_scheme_label"), KeyEvent.VK_U, UIManager.getCrossPlatformLookAndFeelClassName()));
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
        JMenu testMenu = new JMenu(messagesBundle.getString("test_menu_label"));
        testMenu.setMnemonic(KeyEvent.VK_T);
        testMenu.getAccessibleContext().setAccessibleDescription(messagesBundle.getString("test_menu_description"));
        testMenu.add(createTestMenuItem(messagesBundle.getString("log_message_label"), KeyEvent.VK_S, () -> Logger.debug(messagesBundle.getString("new_log_message"))));
        testMenu.add(createTestMenuItem(messagesBundle.getString("close_label"), KeyEvent.VK_C, this::handleWindowClosing));
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
        }
    }

    private int handleWindowClosing() {
        int option = JOptionPane.showConfirmDialog(
                this,
                messagesBundle.getString("confirm_exit_message"),
                messagesBundle.getString("exit_title"),
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null

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