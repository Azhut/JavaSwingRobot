package log.model;

import log.LogEntry;

import java.util.*;

public class LogStorage {
    private final LinkedList<LogEntry> m_source;
    private int m_capacity;
    public LogStorage(int size)
    {
        m_source = new LinkedList<>();
        if (size > 0)
        {
            m_capacity = size;
        }
        else
        {
            m_capacity = 1;
        }
    }

    public void add(LogEntry logEntry)
    {
        m_source.add(logEntry);
        deleteExtra();
    }

    public int capacity() { return m_capacity; }

    public int size() { return m_source.size(); }

    public boolean changeSize(int new_size)
    {
        if (new_size > 0)
        {
            m_capacity = new_size;
            deleteExtra();
            return true;
        }
        return false;
    }

    public Iterable<LogEntry> range(int startFrom, int count)
    {
        if (startFrom < 0 || startFrom >= m_source.size())
        {
            return Collections.emptyList();
        }
        int indexTo = Math.min(startFrom + count, m_source.size());
        return m_source.subList(startFrom, indexTo);
    }

    public Iterable<LogEntry> all()
    {
        return m_source;
    }

    private void deleteExtra()
    {
        while (m_source.size() > m_capacity)
        {
            m_source.pollFirst();
        }
    }

}
