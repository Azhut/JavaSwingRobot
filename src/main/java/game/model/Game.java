package game.model;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Game {
    private final List<Player> players = new LinkedList<>();

    public void addPlayer(Player player) {
        players.add(player);
    }

    public void removePlayer(Player player) {
        players.remove(player);
    }

    public List<Player> getPlayers() {
        return players;
    }
}
