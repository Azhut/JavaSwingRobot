package game.model;

import game.classLoader.FileClassLoader;

public class ModelLoader {
    private FileClassLoader fileClassLoader;

    public ModelLoader() {
        fileClassLoader = new FileClassLoader();
    }

    public Class<?> loadModelClass(String jarFilePath, String className) throws ClassNotFoundException {
        return fileClassLoader.loadClassFromJar(jarFilePath, className);
    }
}
