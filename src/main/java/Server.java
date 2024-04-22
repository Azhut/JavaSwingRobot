import java.net.*;
import  java.io.*;

public class Server
{
    public static void main(String[] args)
    {
        ServerSocket serverSocket = null;
        Socket clientSocket = null;
        BufferedOutputStream outToClient = null;
        FileInputStream fileInputStream = null;

        try
        {
            serverSocket = new ServerSocket(8000);
            System.out.println("Сервер запущен!");
            while (true)
            {
                clientSocket = serverSocket.accept();

                System.out.println("Подключился клиент: " + clientSocket.getInetAddress());

                outToClient = new BufferedOutputStream(clientSocket.getOutputStream());

                File file = new File("example.jar");
                byte[] byteArray = new byte[(int) file.length()];
                fileInputStream = new FileInputStream(file);
                fileInputStream.read(byteArray, 0, byteArray.length);

                outToClient.write(byteArray, 0, byteArray.length);
                outToClient.flush();
                System.out.println("JAR-файл отправлен.");

                fileInputStream.close();
                outToClient.close();
                clientSocket.close();
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        finally {
            try
            {
                if (serverSocket != null)
                {
                    serverSocket.close();
                }
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }
}
