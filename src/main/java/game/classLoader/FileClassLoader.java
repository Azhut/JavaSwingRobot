package game.classLoader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class FileClassLoader extends ClassLoader {
    public Class<?> loadClassFromJar(String jarFilePath, String className) throws ClassNotFoundException {
        try (JarFile jarFile = new JarFile(new File(jarFilePath))) {
            JarEntry jarEntry = jarFile.getJarEntry(className.replace('.', '/') + ".class");
            if (jarEntry == null) {
                throw new ClassNotFoundException("Class " + className + " not found in JAR file");
            }
            byte[] classData = readJarEntry(jarFile, jarEntry);
            return defineClass(className, classData, 0, classData.length);
        } catch (IOException e) {
            throw new ClassNotFoundException("Failed to load class " + className + " from JAR file", e);
        }
    }

    private byte[] readJarEntry(JarFile jarFile, JarEntry jarEntry) throws IOException {
        try (InputStream inputStream = jarFile.getInputStream(jarEntry);
             ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
            return outputStream.toByteArray();
        }
    }
}
