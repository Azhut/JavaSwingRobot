package log;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class LogStorageSpeedTest {

    public static void main(String[] args) {
        final int THREAD_COUNT = 100;
        final int LOG_ENTRIES = 5000;
        final int CAPACITY = 100;

        BadLogStorage badLogStorage = new BadLogStorage(CAPACITY);
        LogStorage logStorage = new LogStorage(CAPACITY);

        ExecutorService executorService = Executors.newFixedThreadPool(THREAD_COUNT);

        long startTime = System.currentTimeMillis();
        for (int i = 0; i < LOG_ENTRIES; i++) {
            final int index = i;
            executorService.submit(() -> badLogStorage.add(new LogEntry(LogLevel.Info,"Entry " + index)));
        }
        executorService.shutdown();
        try {
            executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        long endTime = System.currentTimeMillis();
        System.out.println("Time taken for adding entries to log.LogStorage: " + (endTime - startTime) + " milliseconds");

        executorService = Executors.newFixedThreadPool(THREAD_COUNT);
        startTime = System.currentTimeMillis();
        for (int i = 0; i < LOG_ENTRIES; i++) {
            final int index = i;
            executorService.submit(() -> logStorage.add(new LogEntry(LogLevel.Info,"Entry " + index)));
        }
        executorService.shutdown();
        try {
            executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        endTime = System.currentTimeMillis();
        System.out.println("Time taken for adding entries to LogStorage2: " + (endTime - startTime) + " milliseconds");
    }
}