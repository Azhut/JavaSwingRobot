package game.network;

import game.model.RobotModel;

import java.io.*;
import java.net.*;
import java.util.concurrent.*;

public class RobotSender
{
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    private final RobotModel model;
    private final String serverAddress;
    private final int serverPort;

    public RobotSender(RobotModel model, String serverAddress, int serverPort) {
        this.model = model;
        this.serverAddress = serverAddress;
        this.serverPort = serverPort;
    }

    public void startSending()
    {
        scheduler.scheduleAtFixedRate(() ->
        {
            try
            {
                sendRobotInfo();
            }
            catch (IOException e)
            {
                System.out.println("Не удалось подключиться к серверу");
            }
        }, 0, 200, TimeUnit.MILLISECONDS);
    }

        private void sendRobotInfo() throws IOException
    {
        try (Socket socket = new Socket(serverAddress, serverPort);
             ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream()))
        {
            outputStream.writeObject(model);
            outputStream.flush();
        }
    }
}
