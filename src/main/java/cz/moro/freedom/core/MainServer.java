package cz.moro.freedom.core;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cz.moro.freedom.core.handlers.GameHandler;
import cz.moro.freedom.messages.ChatMsg;
import cz.moro.freedom.messages.ConnectToGame;
import cz.moro.freedom.messages.EndMsg;
import cz.moro.freedom.messages.GameScoreMsg;
import cz.moro.freedom.messages.InitialConnectMsg;
import cz.moro.freedom.messages.Message;
import cz.moro.freedom.messages.StartGameMsg;
import cz.moro.freedom.messages.StartMsg;
import cz.moro.freedom.messages.TurnMsg;
import cz.moro.freedom.model.Cell;
import cz.moro.freedom.model.Game;
import cz.moro.freedom.model.Player;
import cz.moro.freedom.model.Team;
import cz.moro.freedom.model.World;
import cz.moro.freedom.service.ScoreCounter.Score;
import cz.moro.freedom.util.JsonMessageParser;

@ServerEndpoint("/server")
public class MainServer {

    private final Logger logger = LoggerFactory.getLogger(MainServer.class);
    
    private static final Map<String, Session> sessions = new ConcurrentHashMap<>();
    private static final Map<String, Player> players = new ConcurrentHashMap<>();
    private static final Map<Long, GameHandler> games = new ConcurrentHashMap<>();

    /**
     * @OnOpen allows us to intercept the creation of a new session. The session class allows us to send data to the user. In the method
     *         onOpen, we'll let the user know that the handshake was successful.
     */
    @OnOpen
    public void onOpen(Session session) {
        logger.debug(session.getId() + " has opened a connection");

        initPlayer(session);

        InitialConnectMsg msg = new InitialConnectMsg();
        msg.setPlayer(players.get(session.getId()));
        sendJson(session, msg.toJson());
    }

    /**
     * When a user sends a message to the server, this method will intercept the message and allow us to react to it. For now the message is
     * read as a String.
     * 
     * @throws Exception
     */
    @OnMessage
    public void onMessage(String message, Session session) throws Exception {
        logger.debug("Message from " + session.getId() + ": " + message);

        Player player = players.get(session.getId());
        if (player == null) {
            throw new Exception("Player with ID " + session.getId() + " not found");
        }
        logger.debug("Parse: " + message);
        Message msg = JsonMessageParser.parse(message);

        if (msg != null) {
            msg.setPlayer(player);

            switch (msg.getType()) {
            case START_GAME:
                startGame((StartGameMsg)msg);
                break;
            case CHAT:
                chat((ChatMsg)msg);
                break;
            case TURN:
                turn((TurnMsg)msg);
                break;
            default:
                break;

            }
        }

    }

    /**
     * The user closes the connection.
     * 
     * Note: you can't send messages to the client from this method
     */
    @OnClose
    public void onClose(Session session) {
        sessions.remove(session.getId());
        players.remove(session.getId());
    }

    private void startGame(StartGameMsg msg) {

        GameHandler gameHandler;
        Game game = null;

        if (msg.getGame() == null) {


            game = new Game();
            gameHandler = new GameHandler(game);

            games.put(game.getId(), gameHandler);

            Session session = sessions.get(msg.getPlayer().getId());
            sendConnectToGameMsg(session, game);

        } else {
            game = msg.getGame();
            gameHandler = games.get(game.getId());
        }

        Team team = gameHandler.getEmptiestTeam();
        gameHandler.addPlayer(team, msg.getPlayer());

        ConnectToGame con = new ConnectToGame(msg.getPlayer(), game);
        sendJson(game, con.toJson());
        sendWorld(game, msg.getPlayer());

        if (gameHandler.isGameReady()) {
            gameHandler.startGame(this);
        }
    }

    public void sendStartRoundMessage(Team team) {
        StartMsg msg = new StartMsg();
        msg.setTeam(team);
        sendJson(team.getPlayers(), msg.toJson());
    }

    public void sendEndRoundMessage(Team team) {
        EndMsg msg = new EndMsg();
        msg.setTeam(team);
        sendJson(team.getPlayers(), msg.toJson());
    }

    public void sendGameScoreMessage(Game game, List<Score> gameScore) {

        GameScoreMsg gameScoreMsg = new GameScoreMsg();

        gameScoreMsg.setTeams(game.getTeams());
        gameScoreMsg.setScores(gameScore);

        sendJson(game, gameScoreMsg.toJson());

    }
    
    private void sendWorld(Game game, Player player) {
        World world = game.getWorld();
        for(int x=0; x < world.getWidth(); x++) {
            for(int y=0; y < world.getHeight(); y++) {
                Cell cell = world.getCell(x, y);
                if(cell.getPlayer() != null) {
                    TurnMsg msg = new TurnMsg();
                    msg.setX(x);
                    msg.setY(y);
                    msg.setPlayer(cell.getPlayer());
                    msg.setGame(game);
                    sendJson(sessions.get(player.getId()), msg.toJson());
                }
            }
        }
    }


    private void chat(ChatMsg msg) {
        switch (msg.getGroup()) {
        case ALL:
            sendJson(players.values(), msg.toJson());
            break;
        case GAME:
            sendJson(msg.getPlayer().getGame(), msg.toJson());
            break;
        case TEAM:
            sendJson(msg.getPlayer().getTeam().getPlayers(), msg.toJson());
            break;
        default:
            break;
        }
    }

    private void turn(TurnMsg msg) {

        Player playerInTurn = msg.getPlayer();
        msg.setGame(msg.getPlayer().getGame());
        GameHandler gameHandler = getGameHandlerById(msg.getGame().getId());

        if (gameHandler.getGame().equals(playerInTurn.getGame()) && gameHandler.isThisTeamInRound(playerInTurn.getTeam())
                && gameHandler.isThisFirstPlayersTurnInRound(playerInTurn)) {
            
            sendJson(gameHandler.getGame(), msg.toJson());
            gameHandler.addPlayerToTurningPlayerList(playerInTurn);
            gameHandler.setPlayersTurnToWorld(msg.getX(), msg.getY(), playerInTurn);
            
        }

    }

    private void sendJson(Game game, JSONObject json) {
        for (Team team : game.getTeams()) {
            sendJson(team.getPlayers(), json);
        }
    }

    private void sendJson(Collection<Player> players, JSONObject json) {
        for(Player player : players) {
            Session session = sessions.get(player.getId());
            sendJson(session, json);
        }
    }

    private void sendJson(Session session, JSONObject json) {
        sendText(session, json.toString());
    }

    private void sendText(Session session, String text) {
        try {
            if(session != null) {
                logger.debug("Sending to " + session.getId() + ": " + text);
                session.getBasicRemote().sendText(text);
            }
        } catch (IOException e) {
            logger.warn("Error when sending msg", e);
        }
    }

    private void sendConnectToGameMsg(Session session, Game game) {

        JSONObject json = new JSONObject();
        json.put("type", "GAME_CREATED");
        game.toJson(json);
        sendJson(players.values(), json);
    }

    private void initPlayer(Session session) {
        Player player = new Player(session.getId());
        sessions.put(player.getId(), session);
        players.put(player.getId(), player);
    }

    public static Player getPlayerById(String id) {
        return players.get(id);
    }

    public static GameHandler getGameHandlerById(Long id) {
        return games.get(id);
    }

    public static Collection<GameHandler> getGameHandlers() {
        return games.values();
    }

    public static Game getGameById(Long id) {
        GameHandler handler = getGameHandlerById(id);
        if (handler != null) {
            return handler.getGame();
        } else {
            return null;
        }
    }

}
