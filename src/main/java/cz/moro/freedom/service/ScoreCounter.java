package cz.moro.freedom.service;

import java.util.ArrayList;
import java.util.List;

import cz.moro.freedom.model.Cell;
import cz.moro.freedom.model.Game;
import cz.moro.freedom.model.Player;
import cz.moro.freedom.model.Team;
import cz.moro.freedom.model.World;

public class ScoreCounter {
	private static final int LENGTH_OF_SEQUENCE = 3;
	private static List<Score> totalScore; 

	
	public static class Score {

		public Long id;
		public Long teamScore;

		/**
		 * @param firstTeamScore
		 * @param secondTeamScore
		 */
		public Score(Long id, Long teamScore) {
			this.id = id;
			this.teamScore = teamScore;
		}

		public Long getId() {
			return id;
		}

		public Long getTeamScore() {
			return teamScore;
		}

		public void setTeamScore(Long teamScore) {
			this.teamScore = teamScore;
		}

		public void setId(Long id) {
			this.id = id;
		}
		

	}

	public static List<Score> getGameScore(Game game) {

		World world = game.getWorld();
		
		totalScore = initScore(game.getTeams());
		Long scoreold;
		Cell cell = new Cell();
		Player player = null;

		// prejdem hracie pole z hora dole
		for (int x = 0; x < world.getWidth(); x++) {
			player = null;
			scoreold = 0l;
			for (int y = 0; y < world.getHeight(); y++) {
				cell = world.getCell(x, y);
				scoreold = getScoreFor(player,scoreold,cell);			
				player = cell.getPlayer();				
			} // koniec y
		} // koniec x
		
		//prejde hracie pole y lava do prava
		for (int y = 0; y < world.getHeight(); y++) {
			player = null;
			scoreold = 0l;
			for (int x = 0; x < world.getWidth(); x++) {
				cell = world.getCell(x, y);
				scoreold = getScoreFor(player,scoreold,cell);				
				player = cell.getPlayer();
			} // koniec x
		} // koniec y

		//diagony z lava do prava pod hlavnou
		for (int y = 0; y < world.getHeight()-LENGTH_OF_SEQUENCE; y++) {
			player = null;
			scoreold = 0l;
			for (int x = 0; x < world.getWidth(); x++) {
				int newy = y+x;
				if (newy >= world.getHeight() || newy >= x) {
					continue;				
				}
				cell = world.getCell(x, y+x);
				scoreold = getScoreFor(player,scoreold,cell);	
				player = cell.getPlayer();
			}
		}
		//diagony z lava do prava nad hlavnou
		for (int x = 0; x < world.getWidth()-LENGTH_OF_SEQUENCE; x++) {
			player = null;
			scoreold = 0l;
			for (int y = 0; y < world.getHeight(); y++) {
				int newx = x+y;
				if (newx >=world.getWidth() || newx >=y){
					continue;
				}
				cell = world.getCell(x+y, y);
				scoreold = getScoreFor(player,scoreold,cell);	
				player = cell.getPlayer();
			}
		}
		
		//diagony z prava do lava pod hlavnou
				for (int y = world.getHeight()-1; y > 0+LENGTH_OF_SEQUENCE; y--) {
					player = null;
					scoreold = 0l;
					for (int x = 0; x < world.getWidth() ; x++) {
						int newy = y-x;
						if (newy < 0 || x >= y ){
							continue;
						}
						cell = world.getCell(x, y-x);
						scoreold = getScoreFor(player,scoreold,cell);	
						player = cell.getPlayer();
					}
				}
				//diagony z prava do lava nad hlavnou
				for (int x = world.getWidth()-1; x > 0+LENGTH_OF_SEQUENCE; x--) {
					player = null;
					scoreold = 0l;
					for (int y =0; y < world.getHeight(); y++) {
						int newx = x-y;
						if (newx < 0 || y >= x){
							continue;
						}
						cell = world.getCell(x-y, y);
						scoreold = getScoreFor(player,scoreold,cell);	
						player = cell.getPlayer();
					}
				}
		
		return totalScore;
	}

	private static Long getScoreFor(Player player, Long oldScore, Cell cell){
		Long score = 0l;
		if (player != null) { // kontrola zaciatku
			 score = getNewScore(player,cell.getPlayer(),oldScore);
			if (score < oldScore) {	// zmenil sa znak 
				if (oldScore >= LENGTH_OF_SEQUENCE){ // ak bola postupnost vacsia ako minimalna dlzka prirad body
					for (Score teamScore : totalScore) {
						if(teamScore.getId() == player.getTeam().getId()){
							teamScore.setTeamScore(oldScore);
						}
					}								
					}
				} 
			} 
		return score;
	}
	private static Long getNewScore(Player old, Player acct, Long scoreOld){			
		return compareCell(old, acct, scoreOld);
	}
	
	private static List<Score> initScore(List<Team> teams) {
		List<Score> result = new ArrayList<>();

		for (Team team : teams) {
			Score score = new Score(team.getId(), 0l);
			result.add(score);
		}
		return result;
	}

	private static Long compareCell(Player old, Player acct, Long count) {
		Long result = count;
		if (acct == null) {  //policko nie je obsadene
			result = 0l; 
		} else if (old.getTeam().equals(acct)) { //je rovnake ako predchadzajuce
			result++;
		} else {	// novy vyskit
			result = 1l;
		}

		return result;
	}

}
