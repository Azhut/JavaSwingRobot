package log.model;

import log.LogEntry;

import java.util.*;


/**
 * Структура для хранение логов<br>
 * Представляет из себя очередь ограниченной длины, написанной на связном списке из <b>LogEntry</b>
 */
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

    /**
     * Добавление <b>logEntry</b> в структуру
     * @param logEntry - новая запись
     */
    public void add(LogEntry logEntry)
    {
        m_source.add(logEntry);
        deleteExtra();
    }

    /**
     * Ёмкость хранилища
     * @return m_capacity
     */
    public int capacity() { return m_capacity; }


    /**
     * Размер занятого пространства
     * @return размер
     */
    public int size() { return m_source.size(); }


    /**
     * Изменение ёмкости
     * @param new_size - новый размер
     * @return удалось ли изменить ёмкомть
     */
    public boolean changeCapacity(int new_size)
    {
        if (new_size > 0)
        {
            m_capacity = new_size;
            deleteExtra();
            return true;
        }
        return false;
    }


    /**
     * Возвращает часть структуры с позиции <b>startFrom</b> в количестве <b>count</b>
     * @param startFrom - начало
     * @param count - количество
     * @return - структура для итерирования
     */
    public Iterable<LogEntry> range(int startFrom, int count)
    {
        if (startFrom < 0 || startFrom >= m_source.size())
        {
            return Collections.emptyList();
        }
        int indexTo = Math.min(startFrom + count, m_source.size());
        return m_source.subList(startFrom, indexTo);
    }

    /**
     * Возвращает всю структуру
     * @return структура для итерирования
     */
    public Iterable<LogEntry> all()
    {
        return m_source;
    }


    /**
     * Удаляет лишнии записи из списка с начала
     */
    private void deleteExtra()
    {
        while (m_source.size() > m_capacity)
        {
            m_source.pollFirst();
        }
    }

}
