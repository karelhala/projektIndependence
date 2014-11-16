package cz.moro.freedom.core.handlers;

import java.util.List;

import cz.moro.freedom.core.MainServer;
import cz.moro.freedom.model.Game;
import cz.moro.freedom.model.Player;
import cz.moro.freedom.model.Team;
import cz.moro.freedom.service.ScoreCounter;
import cz.moro.freedom.service.ScoreCounter.Score;


public class GameHandler {

    private static final Long ROUND_TIME_MILIS = 60000l;

    private final Game game;

    private Team teamInRound;
    private int teamInRoundIndex = 0;

    private Thread gameThread;

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

        if (isGameReady()) {

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

                            List<Score> gameScore = ScoreCounter.getGameScore(game);

                            mainServer.sendGameScoreMessage(game, gameScore);

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

            teamInRoundIndex = (teamInRoundIndex + 1 < game.getTeams().size()) ? (teamInRoundIndex + 1) : 0;

            teamInRound = game.getTeams().get(teamInRoundIndex);
        }
    }
    
    public Team getEmptiestTeam() {
        Team emptiest = null;
        int players = Integer.MAX_VALUE;
        
        for(Team team : game.getTeams()) {
            int used = team.getPlayers().size() ;
            if(used < players) {
                emptiest = team;
                players = used;
            }
        }
        
        return emptiest;
    }

    public Game getGame() {
        return game;
    }

}
