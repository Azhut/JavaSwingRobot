package client_server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class ClientTest
{
    public static void main(String[] args) throws IOException {
        Socket clientSocket = new Socket("127.0.0.1", 8000);

        BufferedReader reader = new BufferedReader(
                new InputStreamReader(
                        clientSocket.getInputStream()));

        String message = reader.readLine();
        System.out.println(message);

        clientSocket.close();
    }
}
