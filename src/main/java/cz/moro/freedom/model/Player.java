package cz.moro.freedom.model;



public class Player {

    private final String id;
    private String name;
    private Team team;
    
    public Player(String id) {
        this.id = id;        
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public String getName() {
        return name;
    }
    
    public String getId() {
        return id;
    }
    
    public Team getTeam() {    
        return team;
    }
    
    public void setTeam(Team team) {    
        this.team = team;
    }
        
}

