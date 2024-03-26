package log.view;

import java.awt.*;
import javax.swing.*;
import log.controller.LogChangeListener;
import log.LogEntry;
import log.controller.LogWindowSource;

public class LogWindow extends JInternalFrame implements LogChangeListener
{
    private final LogWindowSource m_logSource;
    private final JTextArea m_logContent;

    public LogWindow(LogWindowSource logSource) 
    {
        super("Протокол работы", true, true, true, true);

        m_logSource = logSource;
        m_logSource.registerListener(this);

        m_logContent = new JTextArea("");
        m_logContent.setSize(200, 500);
        m_logContent.setEditable(false);

        JPanel changeSizeFormPanel = new JPanel(new BorderLayout());
        LogSizeChangeForm logSizeChangeForm = new LogSizeChangeForm(m_logSource);
        logSizeChangeForm.fillPanel(changeSizeFormPanel);

        JPanel makeDumpFormPanel = new JPanel();
        makeDumpFormPanel.setLayout(new BoxLayout(makeDumpFormPanel, BoxLayout.X_AXIS));
        MakeDumpForm makeDumpForm = new MakeDumpForm(m_logSource);
        makeDumpForm.fillPanel(makeDumpFormPanel);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(m_logContent, BorderLayout.CENTER);
        mainPanel.add(changeSizeFormPanel, BorderLayout.PAGE_START);
        mainPanel.add(makeDumpFormPanel, BorderLayout.PAGE_END);

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
