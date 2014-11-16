package cz.moro.freedom.core.handlers;

import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import cz.moro.freedom.core.MainServer;
import cz.moro.freedom.model.Cell;
import cz.moro.freedom.model.Character;
import cz.moro.freedom.model.Game;
import cz.moro.freedom.model.Player;
import cz.moro.freedom.model.Team;
import cz.moro.freedom.service.ScoreCounter;
import cz.moro.freedom.service.ScoreCounter.Score;

public class GameHandler {

	public static final Long ROUND_TIME_MILIS = 15000l;
	private static final int MIN_PLAYER = 3;

	private final Game game;

	private Team teamInRound;
	private int teamInRoundIndex = 0;

	private Thread gameThread;

	private Set<Player> playersTurnInRound = new HashSet<>();

	public GameHandler(Game game) {
		super();
		this.game = game;
	}

	public void addPlayer(Team playerTeam, Player player) {

		if (playerTeam == null || player == null) {

			throw new IllegalArgumentException();
		}

		player.setTeam(playerTeam);
		player.setGame(game);

		for (Team team : game.getTeams()) {

			if (team.equals(playerTeam)) {
				team.addPlayer(player);
			}
		}
	}
	
	public void removePlayer(Player player) {
       for (Team team : game.getTeams()) {

            if (team.equals(player.getTeam())) {
                team.removePlayer(player);
            }
        }
	}

	public boolean isThisTeamInRound(Team team) {

		return team.equals(teamInRound);
	}

	public boolean isGameReady() {

		for (Team team : game.getTeams()) {
			if (team.getPlayers() == null || team.getPlayers().size() == 0) {
				return false;
			}
		}

		return true;
	}

	public void startGame(final MainServer mainServer) {

		if (isGameReady() && (gameThread == null)) {

			teamInRound = game.getTeams().get(0);

			gameThread = new Thread(new Runnable() {

				@Override
				public void run() {

					while (!Thread.interrupted()) {

						mainServer.sendStartRoundMessage(teamInRound);

						try {
							Thread.sleep(ROUND_TIME_MILIS);

							mainServer.sendEndRoundMessage(teamInRound);
							changeTeamInRound();
							clearPlayersTurnedInRoundList();

							try {
								List<Score> gameScore = ScoreCounter
										.getGameScore(game);

								mainServer
										.sendGameScoreMessage(game, gameScore);
							} catch (Exception e) {
								e.printStackTrace();
							}

						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}

				}
			});

			gameThread.start();
		}
	}

	private void changeTeamInRound() {

		if (game.getTeams() != null && !game.getTeams().isEmpty()) {

			teamInRoundIndex = (teamInRoundIndex + 1 < game.getTeams().size()) ? (teamInRoundIndex + 1)
					: 0;

			teamInRound = game.getTeams().get(teamInRoundIndex);
		}
	}

	public boolean isThisFirstPlayersTurnInRound(Player playerInTurn) {

		return playerInTurn != null
				&& !playersTurnInRound.contains(playerInTurn);
	}

	public void setPlayersTurnToWorld(int x, int y, Player player) {
		if (game.getWorld() != null) {
			game.getWorld().getCell(x, y).setPlayer(player);
		}
	}

	public void addPlayerToTurningPlayerList(Player player) {
		playersTurnInRound.add(player);
	}

	private void clearPlayersTurnedInRoundList() {
		playersTurnInRound.clear();
	}

	public Team getEmptiestTeam() {
		Team emptiest = null;
		int players = Integer.MAX_VALUE;

		for (Team team : game.getTeams()) {
			int used = team.getPlayers().size();
			if (used < players) {
				emptiest = team;
				players = used;
			}
		}

		return emptiest;
	}

	public void setCharacterToPlayer() {
		if (game.getPlayersCout() >= MIN_PLAYER) {
			Random random = new Random();
			Team randomTeam = game.getTeams().get(
					random.nextInt((game.getTeams().size() - 1)));
			randomTeam.getPlayers()
					.get(random.nextInt((randomTeam.getPlayers().size() - 1)))
					.setCharacter(Character.SHAMAN);
		}
	}
	
	public boolean isWorldsCellOccupied(int x, int y) {
		
		return game.getWorld().getCell(x, y).getPlayer() != null;
	}

	public boolean isShamanMove(Player player,int x, int y){
		
		if (player.getCharacter() == Character.SHAMAN){
			
			Cell cell = game.getWorld().getCell(x, y);
			
			return cell.getPlayer() != null && (!cell.getPlayer().getTeam().equals(player.getTeam()));

		}
		
		return false;
	}

	public Game getGame() {
		return game;
	}

}
