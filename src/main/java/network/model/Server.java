package network.model;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private ServerSocket serverSocket;

    public void start() {
        try {
            serverSocket = new ServerSocket(8080); // Примерный номер порта
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Client acceptClient() {
        try {
            Socket clientSocket = serverSocket.accept();
            return new Client(clientSocket);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void stop() {
        try {
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
