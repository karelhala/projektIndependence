package cz.moro.freedom.model;

import java.util.ArrayList;
import java.util.List;


public class Team {

    private List<Player> players;
    
    public Team() {
        players = new ArrayList<>();
    }
    
    public void addPlayer(Player player) {
        players.add(player);
    }
    
    public List<Player> getPlayers() {
        return players;
    }
}
