package game.model;

import game.model.Player;

import java.util.ArrayList;
import java.util.List;

public class Game {
    private  final List<Player> players = new ArrayList<>();

    public  void addPlayer(Player player) {
        players.add(player);
    }

    public  void removePlayer(Player player) {
        players.remove(player);
    }

    public  List<Player> getPlayers() {
        return players;
    }
}
