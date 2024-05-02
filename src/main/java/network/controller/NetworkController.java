package network.controller;

import game.model.Game;
import game.model.Player;
import network.model.Client;
import network.model.Server;
import authentication.PlayerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class NetworkController {
    private final Server server;
    private final Game game;
    private final PlayerFactory playerFactory;
    private final List<ClientHandler> clientHandlers;

    public NetworkController(Game game, String tokenFilePath) throws IOException {
        this.game = game;
        this.server = new Server();
        this.playerFactory = new PlayerFactory(tokenFilePath);
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
            String authToken = client.receiveData();
            Player player = playerFactory.createPlayer(authToken);
            addPlayerToGame(player);
            sendDataToClient(player);
        }

        private void addPlayerToGame(Player player) {
            game.addPlayer(player);
        }

        private void sendDataToClient(Player player) {
            // Отправляем данные о игроке клиенту
            // Например, можно отправить все данные о игроке или только необходимую часть
        }
    }
}
