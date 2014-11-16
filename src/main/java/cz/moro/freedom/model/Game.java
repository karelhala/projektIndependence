package cz.moro.freedom.model;

import java.util.ArrayList;
import java.util.List;


public class Game {


    private final World world;
    private final List<Team> teams;

    public Game() {

        this.world = new World();
        this.teams = new ArrayList<>(2);
    }


    public World getWorld() {
        return world;
    }


    public List<Team> getTeams() {
        return teams;
    }

}
