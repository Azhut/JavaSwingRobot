package log.view;

import fileManagers.LogDumpManager;
import log.filters.DigitFilter;
import log.controller.LogWindowSource;

import javax.swing.*;
import javax.swing.text.PlainDocument;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


/**
 * Форма создания дампа логов двух типов: все или в диапозоне
 */
public class MakeDumpForm implements LogWindowInnerWidget{
    private final JToggleButton m_isRange;
    private final JTextField m_start;
    private final JTextField m_count;
    private final JButton m_makeDump;
    public MakeDumpForm(LogWindowSource source)
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
                    dumpManager.makeDump(source.range(start, count));
                }
                else
                {
                    dumpManager.makeDump(source.all());
                }
            }
        });
    }
    @Override
    public void fillPanel(JPanel panel)
    {
        panel.add(m_isRange);
        panel.add(m_start);
        panel.add(m_count);
        panel.add(m_makeDump);
    }
}
