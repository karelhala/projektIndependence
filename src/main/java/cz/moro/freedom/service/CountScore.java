package cz.moro.freedom.service;

import java.util.ArrayList;
import java.util.List;

import cz.moro.freedom.model.Cell;
import cz.moro.freedom.model.Game;
import cz.moro.freedom.model.Player;
import cz.moro.freedom.model.Team;
import cz.moro.freedom.model.World;

public class CountScore {
	private static final int LENGTH_OF_SEQUENCE = 3;
	
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
		

	}

	public static List<Score> makeCount(Game game) {

		World world = game.getWorld();
		List<Score> totalScore = initScore(game.getTeams());

		Long scoreold;
		Long score;
		Cell cell = new Cell();
		Player player = null;

		// prejdem hracie pole z hora dole
		for (int x = 0; x < world.getWidth(); x++) {
			player = null;
			scoreold = 0l;
			score = 0l; // na zaciatku stlpca sa pocitadlo vynuluje
			for (int y = 0; y < world.getHeight(); y++) {
				cell = world.getCell(x, y);

				if (player != null) { // kontrola zaciatku
					score = getNewScore(player,cell.getPlayer(),scoreold);
					if (score < scoreold) {	// zmenil sa znak 
						if (scoreold >= LENGTH_OF_SEQUENCE){ // ak bola postupnost vacsia ako minimalna dlzka prirad body
							for (Score teamScore : totalScore) {
								if(teamScore.getId() == player.getTeam().getId()){
									teamScore.setTeamScore(scoreold);
								}
							}
						}
					} else {
						scoreold = score;
					}
				} 
				player = cell.getPlayer();				
			} // koniec y
		} // koniec x
		
		//prejde hracie pole y lava do prava
		for (int y = 0; y < world.getHeight(); y++) {
			player = null;
			scoreold = 0l;
			score = 0l; // na zaciatku riadku sa pocitadlo vynuluje
			for (int x = 0; x < world.getWidth(); x++) {
				cell = world.getCell(x, y);

				if (player != null) { // kontrola zaciatku
					score = getNewScore(player,cell.getPlayer(),scoreold);
					if (score < scoreold) {	// zmenil sa znak 
						if (scoreold >= LENGTH_OF_SEQUENCE){ // ak bola postupnost vacsia ako minimalna dlzka prirad body
							for (Score teamScore : totalScore) {
								if(teamScore.getId() == player.getTeam().getId()){
									teamScore.setTeamScore(scoreold);
								}
							}
						}
					} else {
						scoreold = score;
					}
				} 
				player = cell.getPlayer();
			} // koniec x
		} // koniec y

		return totalScore;
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
