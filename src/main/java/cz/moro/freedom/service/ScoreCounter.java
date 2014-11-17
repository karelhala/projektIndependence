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

		something(world.getWidth(), world.getHeight(), false, world);

		something(world.getHeight(), world.getWidth(), true, world);
		
		something2(world.getHeight(),  world);


		return totalScore;
	}

	private static void something2(int xAxis, 
			World world){
		Team firstTeam;
		Team secondTeam;
		Long counter = 1l;
		Long counter2 = 1l;
		
		for(int j = 0; j < xAxis; j++){
			
			firstTeam = getPlayersTeamFromWorld(world, 0, j);
			
			for(int i = 0; i <=j; i++){
				secondTeam = getPlayersTeamFromWorld(world, i, (j-i));
				
				if (firstTeam != null && firstTeam.equals(secondTeam)) {

					if ((j-i) > 0 ) {
						counter++;
					} else {
						setScoreToTeam(firstTeam, counter);
						counter = 1l;
					}

				} else if (counter >= LENGTH_OF_SEQUENCE) {

					setScoreToTeam(firstTeam, counter);
					counter = 1l;
				}

				firstTeam = secondTeam;
			}
		}
	}
	private static void something(int xAxis, int yAxis, boolean inversedAxis,
			World world) {

		Team firstTeam;
		Team secondTeam;
		Long counter = 1l;

		int x;
		int y;

		for (int i = 0; i < xAxis; i++) {

			if (inversedAxis) {
				x = 0;
				y = i;
			} else {
				x = i;
				y = 0;
			}

			firstTeam = getPlayersTeamFromWorld(world, x, y);

			for (int j = 1; j < yAxis; j++) {

				if (inversedAxis) {
					x = j;
				} else {
					y = j;
				}

				secondTeam = getPlayersTeamFromWorld(world, x, y);

				if (firstTeam != null && firstTeam.equals(secondTeam)) {

					if (j < world.getHeight()) {
						counter++;
					} else {
						setScoreToTeam(firstTeam, counter);
						counter = 1l;
					}

				} else if (counter >= LENGTH_OF_SEQUENCE) {

					setScoreToTeam(firstTeam, counter);
					counter = 1l;
				}

				firstTeam = secondTeam;
			}
		}

	}

	private static Team getPlayersTeamFromWorld(World world, int x, int y) {

		return world.getCell(x, y).getPlayer() != null ? world.getCell(x, y)
				.getPlayer().getTeam() : null;
	}

	private static void setScoreToTeam(Team team, Long counter) {

		for (Score score : totalScore) {

			if (score.getId().equals(team.getId())) {

				score.setTeamScore(score.getTeamScore()
						+ (counter - LENGTH_OF_SEQUENCE + 1));
			}
		}
	}

	private static Long getScoreFor(Player player, Player playerold,
			Long oldScore, Cell cell) {
		Long score = 0l;
		if (player != null) { // kontrola zaciatku
			score = getNewScore(playerold, player, oldScore);
			if (score < oldScore) { // zmenil sa znak
				if (oldScore >= LENGTH_OF_SEQUENCE) { // ak bola postupnost
														// vacsia ako minimalna
														// dlzka prirad body
					for (Score teamScore : totalScore) {
						if (teamScore.getId() == player.getTeam().getId()) {
							teamScore.setTeamScore(teamScore.getTeamScore()
									+ (oldScore - LENGTH_OF_SEQUENCE + 1));
						}
					}
				}
			}
		} else if (oldScore >= LENGTH_OF_SEQUENCE) {
			for (Score teamScore : totalScore) {
				if (teamScore.getId() == playerold.getTeam().getId()) {
					teamScore.setTeamScore(teamScore.getTeamScore()
							+ (oldScore - LENGTH_OF_SEQUENCE + 1));
				}
			}
		}
		return score;
	}

	private static Long getNewScore(Player old, Player acct, Long scoreOld) {
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
		if (acct == null) { // policko nie je obsadene
			result = 0l;
		} else if (old == null) {
			return 1l;
		} else if (old.getTeam().equals(acct.getTeam())) { // je rovnake ako
															// predchadzajuce
			result++;
		} else { // novy vyskit
			result = 1l;
		}

		return result;
	}

}
