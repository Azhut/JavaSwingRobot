package gui;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;

public class JarClassLoader {
    public static Class<?> loadClassFromJar(String jarFilePath, String className) throws Exception {
        File file = new File(jarFilePath);
        URL url = file.toURI().toURL();
        URLClassLoader classLoader = new URLClassLoader(new URL[]{url});
        return classLoader.loadClass(className);
    }
}
