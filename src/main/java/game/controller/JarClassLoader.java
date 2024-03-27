package game.controller;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;

/**
 * Загрузчик классов
 */
public class JarClassLoader {
    /**
     * @param jarFilePath Путь до файла
     * @param className Полное имя класса
     * @return загруженный класс
     */
    public static Class<?> loadClassFromJar(String jarFilePath, String className) throws Exception {
        File file = new File(jarFilePath);
        URL url = file.toURI().toURL();
        URLClassLoader classLoader = new URLClassLoader(new URL[]{url});
        return classLoader.loadClass(className);
    }
}
