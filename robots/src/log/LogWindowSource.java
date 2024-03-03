package log;
import java.util.ArrayList;
import java.util.Collections;

/**Добавление синхронизированных блоков в методы registerListener, unregisterListener, append, size, range и all, чтобы обеспечить потокобезопасность при доступе к общим ресурсам.
 *В методе append добавлена проверка и удаление старых сообщений, если количество сообщений превышает заданный лимит m_iQueueLength.
 *Обновление массива m_activeListeners при регистрации и удалении слушателей.
 *Исправление формирования массива activeListeners в методе append для избежания возможных гонок данных.*/
public class LogWindowSource {
    private final int m_iQueueLength;
    private final ArrayList<LogEntry> m_messages;
    private final ArrayList<LogChangeListener> m_listeners;
    private volatile LogChangeListener[] m_activeListeners;

    public LogWindowSource(int iQueueLength) {
        m_iQueueLength = iQueueLength;
        m_messages = new ArrayList<>(iQueueLength);
        m_listeners = new ArrayList<>();
    }

    public synchronized void registerListener(LogChangeListener listener) {
        m_listeners.add(listener);
        m_activeListeners = null;
    }

    public synchronized void unregisterListener(LogChangeListener listener) {
        m_listeners.remove(listener);
        m_activeListeners = null;
    }

    public synchronized void append(LogLevel logLevel, String strMessage) {
        LogEntry entry = new LogEntry(logLevel, strMessage);
        m_messages.add(entry);

        // If the number of messages exceeds the queue length, remove the oldest messages
        while (m_messages.size() > m_iQueueLength) {
            m_messages.remove(0);
        }

        LogChangeListener[] activeListeners = m_activeListeners;
        if (activeListeners == null) {
            activeListeners = m_listeners.toArray(new LogChangeListener[0]);
            m_activeListeners = activeListeners;
        }
        for (LogChangeListener listener : activeListeners) {
            listener.onLogChanged();
        }
    }

    public synchronized int size() {
        return m_messages.size();
    }

    public synchronized Iterable<LogEntry> range(int startFrom, int count) {
        if (startFrom < 0 || startFrom >= m_messages.size()) {
            return Collections.emptyList();
        }
        int indexTo = Math.min(startFrom + count, m_messages.size());
        return m_messages.subList(startFrom, indexTo);
    }

    public synchronized Iterable<LogEntry> all() {
        return m_messages;
    }
}
