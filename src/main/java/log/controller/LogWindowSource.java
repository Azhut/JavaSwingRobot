package log.controller;

import log.LogEntry;
import log.LogLevel;
import log.model.LogStorage;

import java.util.LinkedList;

/**
 * Что починить:
 * 1. Этот класс порождает утечку ресурсов (связанные слушатели оказываются
 * удерживаемыми в памяти)
 * <br>
 * Класс, отвечающий за хранение логов и слушателей логгера с обеспечением потокобезопасности
 */
public class LogWindowSource
{
    private final LogStorage m_messages;
    private final LinkedList<LogChangeListener> m_listeners;
    private volatile LogChangeListener[] m_activeListeners;
    public LogWindowSource(int iQueueLength)
    {
        m_messages = new LogStorage(iQueueLength);
        m_listeners = new LinkedList<>();
    }

    /**
     * Регистрация слушателя лога
     * @param listener - слушатель
     */
    public void registerListener(LogChangeListener listener)
    {
        synchronized(m_listeners)
        {
            m_listeners.add(listener);
            m_activeListeners = null;
        }
    }

    /**
     * Удаление слушателя лога
     * @param listener - слушатель
     */
    public void unregisterListener(LogChangeListener listener)
    {
        synchronized(m_listeners)
        {
            m_listeners.remove(listener);
            m_activeListeners = null;
        }
    }

    /**
     * Добавление нового лога
     * @param logLevel - уровень лога
     * @param strMessage - сообщение лога
     */
    public void append(LogLevel logLevel, String strMessage) {

        synchronized (m_messages)
        {
            m_messages.add(new LogEntry(logLevel, strMessage));
        }

        notifyListeners();
    }

    /**
     * Получение размера коллекции сообщений
     * @return m_messages.size() - размер коллекции
     */
    public int size()
    {
        synchronized (m_messages)
        {
            return m_messages.size();
        }
    }


    /**
     * Возвращает записи лога из диапозона
     * @param startFrom - начальный индекс
     * @param count - сколько надо
     * @return записи лога
     */
    public Iterable<LogEntry> range(int startFrom, int count)
    {
        synchronized (m_messages)
        {
            return m_messages.range(startFrom, count);
        }
    }


    /**
     * Возвращает все записи лога
     * @return m_messages.all()
     */
    public Iterable<LogEntry> all()
    {
        synchronized (m_messages)
        {
            return m_messages.all();
        }
    }

    /**
     * Изменение рвзмера хранилища
     * @param new_size - новый размер
     * @return результат изменения (успешно или нет)
     */
    public boolean changeSize(int new_size)
    {
        synchronized (m_messages)
        {
            boolean res = m_messages.changeCapacity(new_size);
            notifyListeners();
            return res;
        }
    }


    /**
     * Уведомляет слушателей об изменениях в хранилище логов
     */
    private void notifyListeners()
    {
        LogChangeListener[] activeListeners = m_activeListeners;

        if (activeListeners == null)
        {
            synchronized (m_listeners)
            {
                if (m_activeListeners == null)
                {
                    activeListeners = m_listeners.toArray(new LogChangeListener [0]);
                    m_activeListeners = activeListeners;
                }
            }
        }

        for (LogChangeListener listener : activeListeners)
        {
            listener.onLogChanged();
        }
    }
}
