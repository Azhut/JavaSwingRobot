package gui;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyVetoException;
import java.io.*;
import java.util.Properties;

/**
 * Сохраняет положение окон в файл
 */
public class ConfigManager
{
    private final String configFilePath = new File("robots/src/config.txt").getAbsolutePath();

    /**
     * Сохраняет конфигурацию приложения
     * @param desktopPane - внутренние фреймы
     * @param jFrame - основной фрейм
     */
    public void saveConfig(JDesktopPane desktopPane, JFrame jFrame)
    {
        try (OutputStream output = new FileOutputStream(configFilePath))
        {
            Properties properties = new Properties();

            for (JInternalFrame frame : desktopPane.getAllFrames())
            {
                saveFrameProperties(properties, frame, desktopPane);
            }

            saveFrameProperties(properties, jFrame, desktopPane);

            properties.store(output, null);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * Загружает конфигурацию приложения
     * @param desktopPane - внутренние фреймы
     * @param jFrame - основной фрейм
     */
    public void loadConfig(JDesktopPane desktopPane, JFrame jFrame)
    {
        try (InputStream input = new FileInputStream(configFilePath))
        {
            Properties properties = new Properties();
            properties.load(input);

            for (JInternalFrame frame : desktopPane.getAllFrames())
            {
                loadFrameProperties(properties, frame, desktopPane);
            }

            loadFrameProperties(properties, jFrame, desktopPane);

        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * Сохраняет в характеристики конфигурации данные о фрэйме
     * @param properties - характеристики
     * @param frame - фрейм
     */
    private void saveFrameProperties(Properties properties, Container frame, JDesktopPane desktopPane)
    {
        String className = frame.getClass().getName();
        properties.setProperty(className + "_x", String.valueOf(frame.getX()));
        properties.setProperty(className + "_y", String.valueOf(frame.getY()));
        properties.setProperty(className + "_width", String.valueOf(frame.getWidth() > 0 ? frame.getWidth() : 200));
        properties.setProperty(className + "_height", String.valueOf(frame.getHeight() > 0 ? frame.getHeight() : 200));
        if (frame instanceof JInternalFrame jInternalFrame)
        {
            properties.setProperty(className + "_maximized", String.valueOf((jInternalFrame.isMaximum())));
            properties.setProperty(className + "_z-order", String.valueOf((desktopPane.getComponentZOrder(jInternalFrame))));
        }
    }
    /**
     * Загружает данные о фрэйме с проверкой на максимизируемость
     * @param properties - характеристики
     * @param frame - фрейм
     */
    private void loadFrameProperties(Properties properties, Container frame, JDesktopPane desktopPane)
    {
        String className = frame.getClass().getName();
        int x = Integer.parseInt(properties.getProperty(className + "_x", String.valueOf(frame.getX())));
        int y = Integer.parseInt(properties.getProperty(className + "_y", String.valueOf(frame.getY())));
        int width = Integer.parseInt(properties.getProperty(className + "_width", String.valueOf(frame.getWidth())));
        int height = Integer.parseInt(properties.getProperty(className + "_height", String.valueOf(frame.getHeight())));
        frame.setBounds(x, y, width, height);

        if (frame instanceof JInternalFrame jInternalFrame)
        {
            boolean maximized = Boolean.parseBoolean(properties.getProperty(className + "_maximized", String.valueOf(jInternalFrame.isMaximum())));
            int zorder = Integer.parseInt(properties.getProperty(className + "_z-order", String.valueOf(desktopPane.getComponentZOrder(desktopPane))));
            if (maximized)
            {
                try {
                    jInternalFrame.setMaximum(true);
                } catch (PropertyVetoException e) {
                    throw new RuntimeException(e);
                }
            }

            if (zorder != -1)
            {
                desktopPane.setComponentZOrder(jInternalFrame, zorder);
            }
        }
    }
}

