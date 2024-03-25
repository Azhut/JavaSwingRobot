package gui;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import gui.IRobotModel;

public class JarClassLoader {
    public static IRobotModel loadRobotModelFromJar(String jarFilePath) throws Exception {
        File file = new File(jarFilePath);
        URLClassLoader classLoader = URLClassLoader.newInstance(new URL[]{file.toURI().toURL()});
        Class<?> robotModelClass = classLoader.loadClass("gui.RobotModel"); // Указываем полное имя класса
        return (IRobotModel) robotModelClass.getDeclaredConstructor().newInstance();
    }
}
