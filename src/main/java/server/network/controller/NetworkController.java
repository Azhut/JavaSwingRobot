package server.network.controller;

import server.network.model.ClientModel;
import server.network.model.ServerModel;

import java.io.IOException;
import java.io.OutputStreamWriter;
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
        ClientModel clientModel = null;
        try
        {
            serverModel.start();

            while (true)
            {
                clientModel = serverModel.acceptClient();
                System.out.println("Подключился клиент: " + clientModel.getInetAddress());

//                String data = clientModel.se();
//
//                if (data.isEmpty())
//                {
//                    break;
//                }
//                System.out.println("Пришедние данные: " + data);
                OutputStreamWriter writer = new OutputStreamWriter(clientModel.getOutputStream());

                writer.write("Hello from server!");
                writer.flush();

                System.out.println("Сообщение отправлено!");
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

                if (!Objects.isNull(clientModel))
                {
                    clientModel.close();
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
