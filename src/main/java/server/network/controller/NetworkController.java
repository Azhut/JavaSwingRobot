package server.network.controller;

import server.network.model.ClientModel;
import server.network.model.ServerModel;

import java.io.IOException;
import java.util.Objects;

public class NetworkController
{
    private final ServerModel serverModel;

    public NetworkController()
    {
        this.serverModel = new ServerModel();
    }

    public void startServer()
    {
        ClientModel client = null;
        try
        {
            serverModel.start();

            while (true)
            {
                client = serverModel.acceptClient();
                System.out.println("Подключился клиент: " + client.getInetAddress());

                String data = client.receiveData();

                if (data.isEmpty())
                {
                    break;
                }

                System.out.println("Пришедние данные: " + data);

//                client.close();
            }

        }
        catch (IOException e)
        {
            System.out.println("При работе сервера возникла ошибка: " + e.getMessage());
            e.printStackTrace();
        }
        finally
        {
            try
            {
                serverModel.stop();

                if (!Objects.isNull(client))
                {
                    client.close();
                }
            }
            catch (IOException e)
            {
                System.out.println("При закрытии сервера произошла ошибка: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }
}
