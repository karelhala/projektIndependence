package cz.moro.freedom.core;

import java.io.IOException;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cz.moro.freedom.messages.ChatMsg;
import cz.moro.freedom.messages.Message;
import cz.moro.freedom.messages.StartGameMsg;
import cz.moro.freedom.messages.TurnMsg;
import cz.moro.freedom.model.Player;
import cz.moro.freedom.util.JsonMessageParser;

@ServerEndpoint("/server")
public class MainServer {
    
    private final Logger logger = LoggerFactory.getLogger(MainServer.class);
    
    private static Map<String, Session> sessions = new ConcurrentHashMap<>();
    private static Map<String, Player> players = new ConcurrentHashMap<>();
    
    /**
     * @OnOpen allows us to intercept the creation of a new session.
     * The session class allows us to send data to the user.
     * In the method onOpen, we'll let the user know that the handshake was 
     * successful.
     */
    @OnOpen
    public void onOpen(Session session){
        logger.debug(session.getId() + " has opened a connection");
        
        initPlayer(session);
        
        try {
            session.getBasicRemote().sendText("Connection Established");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
 
    /**
     * When a user sends a message to the server, this method will intercept the message
     * and allow us to react to it. For now the message is read as a String.
     * @throws Exception 
     */
    @OnMessage
    public void onMessage(String message, Session session) throws Exception{
        logger.debug("Message from " + session.getId() + ": " + message);

        Player player = players.get(session.getId());
        if(player == null) {
            throw new Exception("Player with ID " + session.getId() + " not found");
        }
        
        Message msg = JsonMessageParser.parse(message);
        
        switch(msg.getType()) {
            case START_GAME:
                startGame((StartGameMsg) msg);
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
    
    /**
     * The user closes the connection.
     * 
     * Note: you can't send messages to the client from this method
     */
    @OnClose
    public void onClose(Session session){
        sessions.remove(session);
        logger.debug("Session " +session.getId()+" has ended");
    }
    
    private void startGame(StartGameMsg msg) {
        
    }
    
    private void chat(ChatMsg msg) {
        switch(msg.getGroup()) {
            case ALL:
                sendMessage(players.values(), msg.getMsg());
                break;
            case GAME:
                break;
            case TEAM:
                sendMessage(msg.getPlayer().getTeam().getPlayers(), msg.getMsg());
                break;
            default:
                break;            
        }        
    }
    
    private void turn(TurnMsg msg) {
        
    }
     
    
    private void sendMessage(Collection<Player> players, String message) {
        for(Player player : players) {
            try {
                Session session = sessions.get(player.getId());
                session.getBasicRemote().sendText(message);
            } catch (IOException e) {
                logger.warn("Error when sending msg", e);
            }
        }
    }

    
    private void initPlayer(Session session) {
        Player player = new Player(session.getId());
        sessions.put(player.getId(),session);
        players.put(player.getId(), player);
    }
}
