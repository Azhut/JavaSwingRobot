package fileManagers;

import log.Logger;

import java.io.File;

public abstract class FileManager {
    protected File chooseWorkingDirectory()
    {
        String userHome = System.getProperty("user.home");
        File directory = new File(userHome, "JavaSwingRobot");
        if (!directory.exists()) {
            boolean created = directory.mkdir();
            if (created)
            {
                Logger.debug("Рабочая директория успешно создана!");
            }
            else
            {
                Logger.debug("Не удалось создать директорию");
            }
        }
        return directory;
    }
}
