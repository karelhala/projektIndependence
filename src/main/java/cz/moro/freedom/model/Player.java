package cz.moro.freedom.model;


public class Player {

    private final String name;
    private final String team;

    public Player(String name, String team) {
		super();
		this.name = name;
		this.team = team;
	}

	public String getName() {
        return name;
    }

	public String getTeam() {
		return team;
	}
	
    
}
