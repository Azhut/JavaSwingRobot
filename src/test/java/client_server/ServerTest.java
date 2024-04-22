package client_server;

import java.net.*;
import  java.io.*;

public class ServerTest
{
    public static void main(String[] args)
    {
        ServerSocket serverSocket = null;
        Socket clientSocket = null;
        try
        {
            serverSocket = new ServerSocket(8000);
            System.out.println("Сервер запущен!");
            while (true)
            {
                clientSocket = serverSocket.accept();

                System.out.println("Подключился клиент: " + clientSocket.getInetAddress());

                OutputStreamWriter writer = new OutputStreamWriter(clientSocket.getOutputStream());
                writer.write("HTTP/1.0 200 OK\r\n" +
                        "Content-type: text/html\r\n" +
                        "\r\n" +
                        "<h1>Hello from Grigorii!</h1>\r\n");
                writer.flush();

                System.out.println("Сообщение отправлено!");

                writer.close();
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
