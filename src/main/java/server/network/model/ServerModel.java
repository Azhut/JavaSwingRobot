package server.network.model;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server
{
    private ServerSocket serverSocket;

    public void start() throws IOException
    {
        try
        {
            serverSocket = new ServerSocket(8000);

        }
        catch (IOException e)
        {
            throw new IOException("При создании сервера произошла ошибка", e);
        }
    }

    public Client acceptClient() throws IOException
    {
        try
        {
            Socket clientSocket = serverSocket.accept();
            System.out.println("Подключился клиент: " + clientSocket.getInetAddress());
            return new Client(clientSocket);
        }
        catch (IOException e)
        {
            throw new IOException("При подключению к клиенту возникла ошибка", e);
        }
    }

    public void stop() throws IOException
    {
        try
        {
            serverSocket.close();
        }
        catch (IOException e)
        {
            throw new IOException("При прекращении работы сервера произошла ошибка", e);
        }
    }
}
