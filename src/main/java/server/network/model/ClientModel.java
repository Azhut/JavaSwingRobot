package server.network.model;

import java.io.*;
import java.net.Socket;

public class ClientModel
{
    private final Socket socket;
    private final BufferedReader input;

    public ClientModel(Socket socket) throws IOException
    {
        this.socket = socket;

        try
        {
            this.input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        }
        catch (IOException e)
        {
            throw new IOException("Ошибка в создании потоков клиента", e);
        }
    }

    public String getInetAddress()
    {
        return socket.getInetAddress().toString();
    }

    public OutputStream getOutputStream() throws IOException {
        try
        {
            return socket.getOutputStream();
        }
        catch (IOException e)
        {
            throw new IOException(e);
        }
    }


    public String receiveData() throws IOException {
        try
        {
            return input.readLine();
        }
        catch (IOException e)
        {
            throw new IOException("При записи данных произошла ошибка", e);
        }
    }

    public void close() throws IOException {
        try
        {
            socket.close();
            input.close();
        }
        catch (IOException e)
        {
            throw new IOException("При закрытии сокета или потока произошла ошибка", e);
        }
    }
}
