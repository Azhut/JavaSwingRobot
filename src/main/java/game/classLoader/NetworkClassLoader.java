package game.classLoader;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

public class NetworkClassLoader extends ClassLoader {
    @Override
    public Class<?> loadClass(String className) throws ClassNotFoundException {
        try {
            URL classUrl = new URL("http://your.server.com/classes/" + className.replace('.', '/') + ".class");
            URLConnection connection = classUrl.openConnection();
            InputStream inputStream = connection.getInputStream();
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            int data = inputStream.read();
            while (data != -1) {
                buffer.write(data);
                data = inputStream.read();
            }
            inputStream.close();
            byte[] classData = buffer.toByteArray();
            return defineClass(className, classData, 0, classData.length);
        } catch (IOException e) {
            throw new ClassNotFoundException("Failed to load class " + className, e);
        }
    }
}

