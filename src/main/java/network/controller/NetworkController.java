package network.controller;

import game.model.Game;
import game.model.Player;
import network.model.Client;
import network.model.Server;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class NetworkController {
    private final Server server;
    private final Game game;
    private final List<ClientHandler> clientHandlers;

    public NetworkController(Game game) {
        this.game = game;
        this.server = new Server();
        this.clientHandlers = new ArrayList<>();
    }

    public void startServer() {
        server.start();
        while (true) {
            Client client = server.acceptClient();
            ClientHandler clientHandler = new ClientHandler(client);
            clientHandlers.add(clientHandler);
            Thread handlerThread = new Thread(clientHandler);
            handlerThread.start();
        }
    }

    private class ClientHandler implements Runnable {
        private final Client client;

        public ClientHandler(Client client) {
            this.client = client;
        }

        @Override
        public void run() {
            try {
                String authToken = client.receiveData();
                Player player = findPlayerByAuthToken(authToken);

                if (player != null) {
                    addPlayerToGame(player);
                    sendDataToClient(player);
                } else {
                    client.sendData("Ошибка: игрок не найден.");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        private Player findPlayerByAuthToken(String authToken) {
            // Реализуйте вашу логику поиска игрока по токену авторизации
            // Например, можно пройтись по списку всех игроков и найти соответствующего игрока
            return null;
        }

        private void addPlayerToGame(Player player) {
            game.addPlayer(player);
        }

        private void sendDataToClient(Player player) throws IOException {
            // Отправляем данные о игроке клиенту
            // Например, можно отправить все данные о игроке или только необходимую часть
        }
    }
}
