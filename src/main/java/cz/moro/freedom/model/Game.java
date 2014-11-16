package cz.moro.freedom.model;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;


public class Game {
    
    private static AtomicLong idGenerator = new AtomicLong();
    private static final int TEAMS_COUNT = 2;

    private final Long id = idGenerator.incrementAndGet();

    private final World world;
    private final List<Team> teams;

    public Game() {

        this.world = new World();
        this.teams = new ArrayList<>(TEAMS_COUNT);
        for(int i=0; i < TEAMS_COUNT; i++) {
            teams.add(new Team());
        }
    }


    public World getWorld() {
        return world;
    }


    public List<Team> getTeams() {
        return teams;
    }


    public Long getId() {
        return id;
    }


    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }


    @Override
    public boolean equals(Object obj) {

        if (this == obj)
            return true;

        if (obj == null)
            return false;

        if (getClass() != obj.getClass())
            return false;

        Game other = (Game)obj;

        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

}
