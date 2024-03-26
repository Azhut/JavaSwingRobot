package fileManagers;

import log.Logger;

import java.io.File;


/**
 * Мэнэджер для работы с файлами в каталоге пользователя
 */
public abstract class FileManager {

    /**
     * Ищет и, при необходимости, создаёт рабочую директорию в каталоге пользователя
     * @return рабочая директория
     */
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
