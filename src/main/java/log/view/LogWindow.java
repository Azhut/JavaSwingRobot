package log.view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.text.PlainDocument;

import fileManagers.LogDumpManager;
import gui.filters.DigitFilter;
import log.controller.LogChangeListener;
import log.LogEntry;
import log.controller.LogWindowSource;

public class LogWindow extends JInternalFrame implements LogChangeListener
{
    private final LogWindowSource m_logSource;
    private final JTextArea m_logContent;
    private JTextField m_logSizeChangeForm;
    private JButton m_logSizeChangeButton;
    private JToggleButton m_isRange;
    private JTextField m_start;
    private JTextField m_count;
    private JButton m_makeDump;

    public LogWindow(LogWindowSource logSource) 
    {
        super("Протокол работы", true, true, true, true);

        m_logSource = logSource;
        m_logSource.registerListener(this);

        m_logContent = new JTextArea("");
        m_logContent.setSize(200, 500);
        m_logContent.setEditable(false);

        createChangeSizeForm();
        createMakeDumpForm();

        JPanel changeSizeFormPanal = new JPanel(new BorderLayout());
        changeSizeFormPanal.add(m_logSizeChangeForm, BorderLayout.CENTER);
        changeSizeFormPanal.add(m_logSizeChangeButton, BorderLayout.LINE_END);

        JPanel makeDumpForm = new JPanel();
        makeDumpForm.setLayout(new BoxLayout(makeDumpForm, BoxLayout.X_AXIS));
        makeDumpForm.add(m_isRange);
        makeDumpForm.add(m_start);
        makeDumpForm.add(m_count);
        makeDumpForm.add(m_makeDump);


        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(m_logContent, BorderLayout.CENTER);
        mainPanel.add(changeSizeFormPanal, BorderLayout.PAGE_START);
        mainPanel.add(makeDumpForm, BorderLayout.PAGE_END);

        getContentPane().add(mainPanel);
        pack();
        updateLogContent();
    }

    private void createChangeSizeForm()
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
                    m_logSource.changeSize(Integer.parseInt(m_logSizeChangeForm.getText()));
                    m_logSizeChangeForm.setText("0");
                }
            }
        });
    }

    private void createMakeDumpForm()
    {
        m_start = new JTextField("");
        m_start.setSize(30, 30);
        PlainDocument doc1 = (PlainDocument) m_start.getDocument();
        doc1.setDocumentFilter(new DigitFilter());

        m_count = new JTextField("");
        m_count.setSize(30, 30);
        PlainDocument doc2 = (PlainDocument) m_start.getDocument();
        doc2.setDocumentFilter(new DigitFilter());

        m_isRange = new JToggleButton("Диапозон");
        m_isRange.setSize(30, 30);

        m_makeDump = new JButton("Сделать дамп");
        m_makeDump.setSize(30, 30);
        m_makeDump.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                LogDumpManager dumpManager = new LogDumpManager();
                if (m_isRange.isSelected() && !m_start.getText().isEmpty() && !m_count.getText().isEmpty())
                {
                    int start = Integer.parseInt(m_start.getText());
                    int count = Integer.parseInt(m_count.getText());
                    dumpManager.makeDump(m_logSource.range(start, count));
                }
                else
                {
                    dumpManager.makeDump(m_logSource.all());
                }
            }
        });
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
