package log.view;

import javax.swing.*;


/**
 * Внутренний виджет окна логгера
 */
public interface LogWindowInnerWidget {

    /**
     * Добавление содержимого виджета на панель
     * @param panel - входная панель
     */
    void fillPanel(JPanel panel);
}
