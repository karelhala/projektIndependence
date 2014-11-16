package cz.moro.freedom.core.handlers;

import cz.moro.freedom.core.MainServer;
import cz.moro.freedom.model.Game;
import cz.moro.freedom.model.Player;
import cz.moro.freedom.model.Team;


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
                            
                            mainServer.sendGameScoreMessage(null);


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
        
        if(game.getTeams() != null && !game.getTeams().isEmpty()) {
            
            teamInRoundIndex = (teamInRoundIndex+1 < game.getTeams().size()) ? (teamInRoundIndex + 1) : 0 ;
            
            teamInRound = game.getTeams().get(teamInRoundIndex);
        }
    }

}
