package gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.text.PlainDocument;

import gui.filters.DigitFilter;
import log.LogChangeListener;
import log.LogEntry;
import log.LogWindowSource;

public class LogWindow extends JInternalFrame implements LogChangeListener
{
    private final LogWindowSource m_logSource;
    private final JTextArea m_logContent;
    private final JTextField m_logSizeChangeForm;
    private final Button m_logSizeChangeButton;

    public LogWindow(LogWindowSource logSource) 
    {
        super("Протокол работы", true, true, true, true);

        m_logSource = logSource;
        m_logSource.registerListener(this);

        m_logContent = new JTextArea("");
        m_logContent.setSize(200, 500);
        m_logContent.setEditable(false);

        m_logSizeChangeForm = new JTextField("");
        m_logSizeChangeForm.setSize(200, 30);
        PlainDocument doc = (PlainDocument) m_logSizeChangeForm.getDocument();
        doc.setDocumentFilter(new DigitFilter());

        m_logSizeChangeButton = new Button("OK");
        m_logSizeChangeButton.setSize(30, 30);
        m_logSizeChangeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                m_logSource.changeSize(Integer.parseInt(m_logSizeChangeForm.getText()));
            }
        });

        JPanel formPanal = new JPanel(new BorderLayout());
        formPanal.add(m_logSizeChangeForm, BorderLayout.CENTER);
        formPanal.add(m_logSizeChangeButton, BorderLayout.LINE_END);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(m_logContent, BorderLayout.CENTER);
        mainPanel.add(formPanal, BorderLayout.PAGE_START);

        getContentPane().add(mainPanel);
        pack();
        updateLogContent();
    }



    private void updateLogContent()
    {
        StringBuilder content = new StringBuilder();
        for (LogEntry entry : m_logSource.all())
        {
            content.append(entry.getMessage()).append("\n");
        }
        m_logContent.setText(content.toString());
        m_logContent.invalidate();
    }
    
    @Override
    public void onLogChanged()
    {
        EventQueue.invokeLater(this::updateLogContent);
    }
}
