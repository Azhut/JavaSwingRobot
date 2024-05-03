package client_server;
import log.Logger;

import java.io.*;
import java.net.Socket;

public class NetworkClassLoaderTest extends ClassLoader {

    public Class<?> loadClass(String className) throws ClassNotFoundException {
        Socket clientSocket = null;
        BufferedInputStream inFromServer = null;
        ByteArrayOutputStream buffer = null;

        try {
            clientSocket = new Socket("127.0.0.1", 8000);

            Logger.info("Подключение к серверу");

            inFromServer = new BufferedInputStream(clientSocket.getInputStream());
            buffer = new ByteArrayOutputStream();

            byte[] byteArray = new byte[8192];
            int bytesRead;
            while ((bytesRead = inFromServer.read(byteArray)) != -1)
            {
                buffer.write(byteArray, 0, bytesRead);
            }

            byte[] classData = buffer.toByteArray();

            Logger.info("JAR-файл получен");

            System.out.println("Пришло: " + classData);

            return defineClass(className, classData, 0, classData.length);
        } catch (IOException e) {
            throw new ClassNotFoundException("Failed to load class " + className, e);
        } finally {
            try {
                if (clientSocket != null)
                {
                    clientSocket.close();
                }
                if (inFromServer != null)
                {
                    inFromServer.close();
                }
                if (buffer != null)
                {
                    buffer.close();
                }
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args)
    {
        NetworkClassLoaderTest loader = new NetworkClassLoaderTest();
        try {
            Class incomingClass = loader.loadClass("IRoborModel.class");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


}

