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

import org.json.JSONObject;
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
        
        if(msg != null) {
            msg.setPlayer(player);
            
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
        
    }
    
    /**
     * The user closes the connection.
     * 
     * Note: you can't send messages to the client from this method
     */
    @OnClose
    public void onClose(Session session){
        sessions.remove(session.getId());
        players.remove(session.getId());
        logger.debug("Session " +session.getId()+" has ended");
    }
    
    private void startGame(StartGameMsg msg) {
        
    }
        
    
    private void chat(ChatMsg msg) {
        switch(msg.getGroup()) {
            case ALL:
                sendChatMsg(players.values(), msg);
                break;
            case GAME:
                break;
            case TEAM:
                sendChatMsg(msg.getPlayer().getTeam().getPlayers(), msg);
                break;
            default:
                break;            
        }        
    }
    
    private void turn(TurnMsg msg) {
        for(Player player : players.values()) {
            Session session = sessions.get(player.getId());
            sendJson(session, msg.toJson());
        }
    }
     
    
    private void sendChatMsg(Collection<Player> players, ChatMsg msg) {
        for(Player player : players) {
            Session session = sessions.get(player.getId());
            sendJson(session, msg.toJson());
        }
    }
    
    private void sendJson(Session session, JSONObject json) {
        try {
            session.getBasicRemote().sendText(json.toString());
        } catch (IOException e) {
            logger.warn("Error when sending msg", e);
        }
    }

    
    private void initPlayer(Session session) {
        Player player = new Player(session.getId());
        sessions.put(player.getId(),session);
        players.put(player.getId(), player);
    }
    
}
