package server.network;

import server.network.controller.NetworkController;

public class Server
{
    public static void main(String[] args)
    {
        NetworkController controller = new NetworkController();

        controller.startServer();
    }
}
