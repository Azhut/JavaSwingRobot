package fileManagers;

import log.LogEntry;
import log.Logger;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class LogDumpManager extends FileManager
{
    private final String logDumpFilePath;
    public LogDumpManager()
    {
        File directory = super.chooseWorkingDirectory();
        this.logDumpFilePath = new File(directory,"logDump.txt").getAbsolutePath();
    }

    public void makeDump(Iterable<LogEntry> toDump)
    {
        try (OutputStream output = new FileOutputStream(logDumpFilePath))
        {
            byte[] buffer;
            for (LogEntry entry : toDump)
            {
                buffer = (entry.toString() + '\n').getBytes();
                output.write(buffer);
            }

            Logger.info("Дамп сохранён!");
        }
        catch (IOException e)
        {
            Logger.error("Неизвестная ошибка");
            e.printStackTrace();
        }
    }
}
