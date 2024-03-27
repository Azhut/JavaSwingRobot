package log.view;


import log.filters.DigitFilter;
import log.controller.LogWindowSource;

import javax.swing.*;
import javax.swing.text.PlainDocument;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Форма изменения размеров хранилища логов
 */
public class LogSizeChangeForm implements LogWindowInnerWidget
{
    private final JTextField m_logSizeChangeForm;
    private final JButton m_logSizeChangeButton;
    public LogSizeChangeForm(LogWindowSource source)
    {
        m_logSizeChangeForm = new JTextField("");
        m_logSizeChangeForm.setSize(200, 30);
        PlainDocument doc = (PlainDocument) m_logSizeChangeForm.getDocument();
        doc.setDocumentFilter(new DigitFilter());

        m_logSizeChangeButton = new JButton("OK");
        m_logSizeChangeButton.setSize(30, 30);
        m_logSizeChangeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!m_logSizeChangeForm.getText().isEmpty())
                {
                    source.changeSize(Integer.parseInt(m_logSizeChangeForm.getText()));
                    m_logSizeChangeForm.setText("0");
                }
            }
        });
    }

    @Override
    public void fillPanel(JPanel panel)
    {
        panel.add(m_logSizeChangeForm, BorderLayout.CENTER);
        panel.add(m_logSizeChangeButton, BorderLayout.LINE_END);
    }
}
