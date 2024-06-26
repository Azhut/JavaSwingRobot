package log;

import java.util.Collections;
import java.util.Date;
import java.util.concurrent.ConcurrentNavigableMap;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class LogStorage {
    private final ConcurrentSkipListMap<Date, LogEntry> storage;
    private Integer capacity;
    private final Lock lock;

    public LogStorage(Integer capacity) {
        this.storage = new ConcurrentSkipListMap<>();
        this.capacity = capacity;
        this.lock = new ReentrantLock();
    }

    public void add(LogEntry entry) {
        storage.put(new Date(), entry);
        deleteExtra();
    }

    public Iterable<LogEntry> all() {
        lock.lock();
        try
        {
            return storage.values();
        }
        finally
        {
            lock.unlock();
        }
    }

    public Iterable<LogEntry> range(Integer startFrom, Integer count) {
        lock.lock();
        try
        {
            if (startFrom < 0 || startFrom >= storage.size())
            {
                return Collections.emptyList();
            }

            int indexTo = Math.min(startFrom + count, storage.size() - 1);

            Date startKey = getKeyByIndex(startFrom);
            if (startKey == null) {
                return Collections.emptyList();
            }

            ConcurrentNavigableMap<Date, LogEntry> subMap = storage.subMap(
                    startKey, true,
                    getKeyByIndex(indexTo), false
            );

            return subMap.values();
        }
        finally
        {
            lock.unlock();
        }
    }

    public void changeCapacity(int newSize)
    {
        if (newSize > 0)
        {
            capacity = newSize;
            deleteExtra();
        }
    }

    private void deleteExtra()
    {
        while (storage.size() > capacity)
        {
            storage.pollFirstEntry();
        }
    }

    private Date getKeyByIndex(int index)
    {
        int i = 0;
        for (Date key : storage.keySet())
        {
            if (i == index)
            {
                return key;
            }
            i++;
        }
        return null;
    }
}
