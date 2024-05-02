package game.model;

import java.util.ArrayList;
import java.util.List;

public class Game {
    private List<Player> players;

    public Game() {
        this.players = new ArrayList<>();
    }

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
