package cz.moro.freedom.service;

import cz.moro.freedom.model.Cell;
import cz.moro.freedom.model.World;

public class CountScore {

	private Integer scoreTeam1;
	private Integer scoreTeam2;

	
	public CountScore() {
		this.scoreTeam1 = 0;
		this.scoreTeam2 = 0;
	}

	public void makeCount(World world){
		int team1 = 0;
		int team2 = 0;
		//prejdem hracie pole z lava do prava
		for (int x=0; x < world.getWidth(); x++){
			for (int y=0; y < world.getHeight(); y++){
				
			}
		}
		 
	}
	
	
	public Integer getScoreTeam1() {
		return scoreTeam1;
	}

	public Integer getScoreTeam2() {
		return scoreTeam2;
	}

}
