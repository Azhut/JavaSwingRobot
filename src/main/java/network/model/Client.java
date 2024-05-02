package network.model;

import java.io.*;
import java.net.Socket;

public class Client {
    private final Socket socket;
    private final BufferedReader input;
    private final PrintWriter output;

    public Client(Socket socket) {
        this.socket = socket;
        try {
            this.input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.output = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to initialize client streams.");
        }
    }

    public void sendData(String data) {
        output.println(data);
    }

    public String receiveData() {
        try {
            return input.readLine();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void close() {
        try {
            socket.close();
            input.close();
            output.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
